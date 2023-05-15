package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;

public class SQL_UserContract {

    public static final String TABLE_NAME = "User";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri( long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    static long getUserID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String AVATAR_ID = "avatar";
        public static final String EMAIL = "email";
        public static final String SECURITY = "pin_code";
    }

}
