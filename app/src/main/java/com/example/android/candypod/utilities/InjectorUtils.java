package com.example.android.candypod.utilities;

import android.content.Context;

import com.example.android.candypod.AppExecutors;
import com.example.android.candypod.data.CandyPodDatabase;
import com.example.android.candypod.data.CandyPodRepository;
import com.example.android.candypod.ui.add.AddPodViewModelFactory;
import com.example.android.candypod.ui.subscribe.PodcastEntryViewModelFactory;
import com.example.android.candypod.ui.podcasts.PodcastsViewModelFactory;
import com.example.android.candypod.ui.subscribe.RssFeedViewModelFactory;
import com.example.android.candypod.ui.subscribe.SubscribeViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for CandyPod.
 */
public class InjectorUtils {

    public static CandyPodRepository provideRepository(Context context) {
        CandyPodDatabase database = CandyPodDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        ITunesSearchApi iTunesSearchApi = RetrofitClient.getClient().create(ITunesSearchApi.class);
        return CandyPodRepository.getInstance(database.podcastDao(), iTunesSearchApi, executors);
    }

    public static AddPodViewModelFactory provideAddPodViewModelFactory(Context context, String country) {
        CandyPodRepository repository = provideRepository(context.getApplicationContext());
        return new AddPodViewModelFactory(repository, country);
    }

    public static SubscribeViewModelFactory provideSubscribeViewModelFactory(
            Context context, String lookupUrl, String id) {
        CandyPodRepository repository = provideRepository(context.getApplicationContext());
        return new SubscribeViewModelFactory(repository, lookupUrl, id);
    }

    public static RssFeedViewModelFactory provideRssViewModelFactory(Context context, String feedUrl) {
        CandyPodRepository repository = provideRepository(context.getApplicationContext());
        return new RssFeedViewModelFactory(repository, feedUrl);
    }

    public static PodcastEntryViewModelFactory providePodcastEntryViewModelFactory(
            Context context, String podcastId) {
        CandyPodRepository repository = provideRepository(context.getApplicationContext());
        return new PodcastEntryViewModelFactory(repository, podcastId);
    }

    public static PodcastsViewModelFactory providePodcastsViewModelFactory(Context context) {
        CandyPodRepository repository = provideRepository(context.getApplicationContext());
        return new PodcastsViewModelFactory(repository);
    }
}
