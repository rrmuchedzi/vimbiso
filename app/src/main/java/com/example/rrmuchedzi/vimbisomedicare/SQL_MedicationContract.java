package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;

public class SQL_MedicationContract {

    public static final String TABLE_NAME = "Medication";

    public final static Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long medicationID ) {
        return ContentUris.withAppendedId(CONTENT_URI, medicationID);
    }

    static long getMedicationID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String QUANTITY = "quantity";
        public static final String QUANTITY_UNITS = "quantity_units";
        public static final String START_DATE = "start";
        public static final String DURATION = "duration";
        public static final String DURATION_UNITS = "duration_units";
        public static final String END_DATE = "end";
        public static final String NOTES = "note";
        public static final String PILL_ID = "pill";
        public static final String TIMES = "times";;
        public static final String MONDAY = "monday";
        public static final String TUESDAY = "tuesday";
        public static final String WEDNESDAY = "wednesday";
        public static final String THURSDAY = "thursday";
        public static final String FRIDAY = "friday";
        public static final String SATURDAY = "saturday";
        public static final String SUNDAY= "sunday";
        public static final String ACTIVE = "active";
    }
}
