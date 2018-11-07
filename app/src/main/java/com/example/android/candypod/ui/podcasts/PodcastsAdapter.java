/*
 * Copyright 2018 Soojeong Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.candypod.ui.podcasts;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.candypod.AppExecutors;
import com.example.android.candypod.R;
import com.example.android.candypod.data.CandyPodDatabase;
import com.example.android.candypod.data.PodcastEntry;
import com.example.android.candypod.databinding.PodcastsListItemBinding;

import java.util.List;

import static com.example.android.candypod.utilities.Constants.DELETE;
import static com.example.android.candypod.utilities.Constants.GROUP_ID_DELETE;
import static com.example.android.candypod.utilities.Constants.ORDER_DELETE;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastsAdapter.PodcastsViewHolder> {

    private List<PodcastEntry> mPodcastEntries;

    private Context mContext;

    private final PodcastsAdapterOnClickHandler mOnClickHandler;

    public interface PodcastsAdapterOnClickHandler {
        void onPodcastClick(PodcastEntry podcastEntry);
    }

    public PodcastsAdapter(Context context, PodcastsAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public PodcastsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        PodcastsListItemBinding podcastsListItemBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.podcasts_list_item, viewGroup, false);
        return new PodcastsViewHolder(podcastsListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastsViewHolder podcastsViewHolder, int position) {
        PodcastEntry podcastEntry = mPodcastEntries.get(position);
        podcastsViewHolder.bind(podcastEntry);
    }

    @Override
    public int getItemCount() {
        if (null == mPodcastEntries) return 0;
        return mPodcastEntries.size();
    }

    public void setPodcastEntries(List<PodcastEntry> podcastEntries) {
        mPodcastEntries = podcastEntries;
        notifyDataSetChanged();
    }

    public class PodcastsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private PodcastsListItemBinding mPodcastsListItemBinding;

        public PodcastsViewHolder(PodcastsListItemBinding podcastsListItemBinding) {
            super(podcastsListItemBinding.getRoot());
            mPodcastsListItemBinding = podcastsListItemBinding;

            itemView.setOnClickListener(this);
            // Set OnCreateContextMenuListener on the view
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(PodcastEntry podcastEntry) {
            String artworkImageUrl = podcastEntry.getArtworkImageUrl();
            Glide.with(mContext)
                    .load(artworkImageUrl)
                    .into(mPodcastsListItemBinding.ivArtwork);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            PodcastEntry podcastEntry = mPodcastEntries.get(adapterPosition);
            mOnClickHandler.onPodcastClick(podcastEntry);
        }

        /**
         * When the user performs a long-click on a podcast, a floating menu appears.
         *
         *  Reference @see "https://stackoverflow.com/questions/36958800/recyclerview-getmenuinfo-always-null"
         *  "https://stackoverflow.com/questions/37601346/create-options-menu-for-recyclerview-item"
         */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int adapterPosition = getAdapterPosition();
            // Set the itemId to adapterPosition to retrieve podcastEntry later
            MenuItem item = menu.add(GROUP_ID_DELETE, adapterPosition, ORDER_DELETE,
                    v.getContext().getString(R.string.action_delete));
            // Set OnMenuItemClickListener on the menu item
            item.setOnMenuItemClickListener(this);
        }

        /**
         * This gets called when a menu item is clicked.
         */
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getTitle().toString()) {
                case DELETE:
                    int adapterPosition = item.getItemId();
                    PodcastEntry podcastEntry = mPodcastEntries.get(adapterPosition);
                    // Delete the podcast from the database
                    delete(podcastEntry);
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Delete the podcast when the user clicks "Delete" menu option.
         * @param podcastEntry The PodcastEntry the user want to delete
         */
        private void delete(final PodcastEntry podcastEntry) {
            // Get the database instance
            final CandyPodDatabase db = CandyPodDatabase.getInstance(mContext);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // Delete the podcast from the database by using the podcastDao
                    db.podcastDao().deletePodcast(podcastEntry);
                }
            });
        }
    }
}