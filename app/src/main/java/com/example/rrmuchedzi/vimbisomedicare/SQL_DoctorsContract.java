package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY_URI;
import static com.example.rrmuchedzi.vimbisomedicare.ApplicationContentProvider.CONTENT_AUTHORITY;

public class SQL_DoctorsContract {

    public static final String TABLE_NAME = "Doctors";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri( long doctorID ) {
        return ContentUris.withAppendedId(CONTENT_URI, doctorID);
    }

    static long getDoctorsID( Uri uri ) {
        return ContentUris.parseId(uri);
    }

    public class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String AVATAR_ID = "avatar";
        public static final String SPECIALITY = "speciality";
        public static final String FIRST_NUMBER = "primary_phone";
        public static final String FIRST_NUMBER_TYPE = "primary_phone_type";
        public static final String SECOND_NUMBER = "secondary_phone";
        public static final String SECOND_NUMBER_TYPE = "secondary_phone_type";
        public static final String EMAIL = "email";
        public static final String ADDRESS= "address";
        public static final String HOSPITAL = "hospital";
        public static final String NOTES_DOCTOR = "doctor_notes";
    }

}
