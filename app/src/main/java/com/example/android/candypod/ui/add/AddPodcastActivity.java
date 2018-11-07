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

package com.example.android.candypod.ui.add;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.candypod.R;
import com.example.android.candypod.databinding.ActivityAddPodcastBinding;
import com.example.android.candypod.model.Feed;
import com.example.android.candypod.model.ITunesResponse;
import com.example.android.candypod.model.Result;
import com.example.android.candypod.ui.GridAutofitLayoutManager;
import com.example.android.candypod.ui.subscribe.SubscribeActivity;
import com.example.android.candypod.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.candypod.utilities.Constants.EXTRA_RESULT_ID;
import static com.example.android.candypod.utilities.Constants.EXTRA_RESULT_NAME;
import static com.example.android.candypod.utilities.Constants.GRID_AUTO_FIT_COLUMN_WIDTH;

public class AddPodcastActivity extends AppCompatActivity
        implements AddPodcastAdapter.AddPodcastAdapterOnClickHandler {

    /** This field is used for data binding **/
    private ActivityAddPodcastBinding mAddPodBinding;

    /** Member variable for the list of results which includes the information of Podcasts */
    private List<Result> mResults;

    /** Member variable for AddPodcastAdapter */
    private AddPodcastAdapter mAddPodAdapter;

    /** ViewModel for AddPodcastActivity */
    private AddPodViewModel mAddPodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddPodBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_podcast);

        // Create a GridLayoutManager and AddPodcastAdapter, and set them to the RecyclerView
        initAdapter();

        String country = "us";
        // Get the ViewModel from the factory
        setupViewModel(country);
        // Observe changes in the ITunesResponse
        observeITunesResponse();

        // Show the up button in the action bar
        showUpButton();
    }

    /**
     * Create a GridAutofitLayoutManager and AddPodcastAdapter, and set them to the RecyclerView.
     */
    private void initAdapter() {
        // A GridAutofitLayoutManager is responsible for calculating the amount of GridView columns
        // based on screen size and positioning item views within a RecyclerView into a grid layout.
        // Reference: @see "https://codentrick.com/part-4-android-recyclerview-grid/"
        GridAutofitLayoutManager layoutManager = new GridAutofitLayoutManager(
                this, GRID_AUTO_FIT_COLUMN_WIDTH);
        // Set the layout manager to the RecyclerView
        mAddPodBinding.rvAddPod.setLayoutManager(layoutManager);
        // Use this setting to improve performance if you know that changes in content do not
        // change the child layout size in the RecyclerView
        mAddPodBinding.rvAddPod.setHasFixedSize(true);

        // Create an empty ArrayList
        mResults = new ArrayList<>();
        // AddPodcastAdapter is responsible for displaying each result in the list.
        mAddPodAdapter = new AddPodcastAdapter(mResults, this);
        // Set adapter to the RecyclerView
        mAddPodBinding.rvAddPod.setAdapter(mAddPodAdapter);
    }

    /**
     * Get the ViewModel from the factory.
     */
    private void setupViewModel(String country) {
        AddPodViewModelFactory factory = InjectorUtils.provideAddPodViewModelFactory(
                AddPodcastActivity.this, country);
        mAddPodViewModel = ViewModelProviders.of(this, factory).get(AddPodViewModel.class);
    }

    /**
     * Every time the ITunesResponse data is updated, the onChanged callback will be invoked and
     * update the UI.
     */
    private void observeITunesResponse() {
        mAddPodViewModel.getITunesResponse().observe(this, new Observer<ITunesResponse>() {
            @Override
            public void onChanged(@Nullable ITunesResponse iTunesResponse) {
                if (iTunesResponse != null) {
                    Feed feed = iTunesResponse.getFeed();
                    List<Result> results = feed.getResults();
                    mAddPodAdapter.addAll(results);
                }
            }
        });
    }

    /**
     * Show an up button on the action bar.
     */
    private void showUpButton() {
        ActionBar actionBar = getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                // Navigate back to the MainActivity when the home button is pressed
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This is where we receive our callback from
     * {@link AddPodcastAdapter.AddPodcastAdapterOnClickHandler}
     *
     * This callback is invoked when you click on an item in the list.
     *
     * @param result Result object
     */
    @Override
    public void onItemClick(Result result) {
        // Create the Intent that will start the SubscribeActivity
        Intent intent = new Intent(this, SubscribeActivity.class);
        // Pass the podcast ID
        intent.putExtra(EXTRA_RESULT_ID, result.getId());
        // Pass the podcast title
        intent.putExtra(EXTRA_RESULT_NAME, result.getName());
        // Once the Intent has been created, start the SubscribeActivity
        startActivity(intent);
    }
}