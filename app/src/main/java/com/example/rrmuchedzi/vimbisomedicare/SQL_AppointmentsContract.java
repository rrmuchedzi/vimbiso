package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;

public class SQL_AppointmentsContract {

    public static final String TABLE_NAME = "Appointment";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri( long appointmentID ) {
        return ContentUris.withAppendedId(CONTENT_URI, appointmentID);
    }

    static long getappointmentID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DOCTOR = "doctor_name";
        public static final String DOCTOR_AVATAR = "doctor_avatar";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String REMINDER_TIME = "scheduled_time";
        public static final String REMINDER_STATUS = "reminder";
        public static final String LOCATION = "location";
        public static final String NOTES = "notes";
    }

}

