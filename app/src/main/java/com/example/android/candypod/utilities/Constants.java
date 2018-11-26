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

package com.example.android.candypod.utilities;

/**
 * Constants class is to define constants used in the app.
 */
public class Constants {

    private Constants() {
        // Restrict instantiation
    }

    /** Base URL for iTunes Search API */
    public static final String I_TUNES_BASE_URL = "https://rss.itunes.apple.com/api/v1/";

    /** URL for a lookup request */
    public static final String I_TUNES_LOOKUP = "https://itunes.apple.com/lookup";

    /** URL for search request */
    public static final String I_TUNES_SEARCH = "https://itunes.apple.com/search";

    /** The parameter value for a search field. The  "podcast" is the media type to search. */
    public static final String SEARCH_MEDIA_PODCAST = "podcast";

    /** Index for representing a default fragment */
    public static final int INDEX_ZERO = 0;

    /** A key for the Extra to pass data via Intent */
    public static final String EXTRA_RESULT_ID = "extra_result_id";
    public static final String EXTRA_RESULT_NAME = "extra_result_name";
    public static final String EXTRA_ITEM = "extra_item";
    public static final String EXTRA_PODCAST_IMAGE = "extra_podcast_image";
    public static final String EXTRA_DOWNLOAD_ENTRY = "extra_download_entry";

    public static final String ACTION_RELEASE_OLD_PLAYER = "action_release_old_player";

    /** Database name */
    public static final String DATABASE_NAME = "podcast";

    /** Used to replace the HTML img tag with empty string */
    public static final String IMG_HTML_TAG = "<img.+?>";
    public static final String REPLACEMENT_EMPTY = "";

    /** The value of column width in the GridAutofitLayoutManager */
    public static final int GRID_AUTO_FIT_COLUMN_WIDTH = 380;
    /** Default value for column width in the GridAutofitLayoutManager */
    public static final int GRID_COLUMN_WIDTH_DEFAULT = 48;
    /** Initial span count for the GridAutofitLayoutManager */
    public static final int GRID_SPAN_COUNT = 1;

    /** Context menu option in the PodcastsAdapter */
    public static final String DELETE = "Delete";
    public static final int GROUP_ID_DELETE = 0;
    public static final int ORDER_DELETE = 0;

    /** Notification channel id */
    public static final String PLAYBACK_CHANNEL_ID = "candy_pod_playback_channel";
    public static final int PLAYBACK_NOTIFICATION_ID = 1;
    public static final String DOWNLOAD_CHANNEL_ID = "candy_pod_download_channel";
    public static final int DOWNLOAD_NOTIFICATION_ID = 2;

    /** The pending intent id is used to uniquely reference the pending intent */
    public static final int NOTIFICATION_PENDING_INTENT_ID = 0;

    /** The fast forward increment and rewind increment (milliseconds) */
    public static final int FAST_FORWARD_INCREMENT = 30000; // 30 sec
    public static final int REWIND_INCREMENT = 10000; // 10 sec

    /** Scheduler Job id in the PodcastDownloadService*/
    public static final int JOB_ID = 1;

    /** The pubDate pattern */
    public static final String PUB_DATE_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";
    /** The formatted date pattern */
    public static final String FORMATTED_PATTERN = "MMM d, yyyy";

    /** Type of the share intent data */
    public static final String SHARE_INTENT_TYPE_TEXT = "text/plain";

}
