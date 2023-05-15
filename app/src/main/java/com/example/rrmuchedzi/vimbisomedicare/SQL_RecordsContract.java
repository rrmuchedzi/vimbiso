package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;

public class SQL_RecordsContract {

    public static final String TABLE_NAME = "MedicationRecords";

    public final static Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long medicationRecordID ) {
        return ContentUris.withAppendedId(CONTENT_URI, medicationRecordID);
    }

    static long getRecordID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String MEDICATION_NAME = "name";
        public static final String AVATAR_ID = "avatar_id";
        public static final String MEDICATION_ID = "medication_id";
        public static final String DATE_TAKE = "date";
        public static final String TIME_TAKE = "time";
        public static final String DOSAGE_TAKEN = "dosage_take";
    }
}
