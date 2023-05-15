package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;

public class SQL_BookmarksContract {

    public static final String TABLE_NAME = "Bookmarks";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri( long bookmarkID ) {
        return ContentUris.withAppendedId(CONTENT_URI, bookmarkID);
    }

    static long getArticleID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String IMAGE_URL = "image";
        public static final String STORY = "story";
        public static final String PUBLISHED_DATE = "date";
    }

}
