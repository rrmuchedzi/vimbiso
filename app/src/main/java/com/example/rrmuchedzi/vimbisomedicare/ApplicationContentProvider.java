package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Rufaro R.Muchedzi on 12/7/2017.
 */

public class ApplicationContentProvider extends ContentProvider {

    private ApplicationDatabase mOpenHelper;
    private static final String TAG = "ApplicationContentProvi";

    static final String CONTENT_AUTHORITY = "com.example.rrmuchedzi.vimbisomedicare.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://"+ CONTENT_AUTHORITY );

    public static final UriMatcher sURI_MATCHER = buildUriMatcher();
    //UriMatcher Values
    private static final int BOOKMARKS = 100;
    private static final int BOOKMARKS_ID = 101;

    private static final int DOCTORS = 200;
    private static final int DOCTORS_ID = 201;

    private static final int MEDICATIONS = 300;
    private static final int MEDICATIONS_ID = 301;

    private static final int USER = 400;
    private static final int USER_ID = 401;

    private static final int APPOINTMENT = 500;
    private static final int APPOINTMENT_ID = 501;

    private static final int RECORD = 600;
    private static final int RECORD_ID = 601;

    private static final int SCHEDULE = 700;
    private static final int SCHEDULE_ID = 701;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_BookmarksContract.TABLE_NAME, BOOKMARKS);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_BookmarksContract.TABLE_NAME + "/#", BOOKMARKS_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_DoctorsContract.TABLE_NAME, DOCTORS);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_DoctorsContract.TABLE_NAME + "/#", DOCTORS_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_MedicationContract.TABLE_NAME, MEDICATIONS);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_MedicationContract.TABLE_NAME + "/#", MEDICATIONS_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_UserContract.TABLE_NAME, USER);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_UserContract.TABLE_NAME + "/#", USER_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_AppointmentsContract.TABLE_NAME, APPOINTMENT);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_AppointmentsContract.TABLE_NAME + "/#", APPOINTMENT_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_RecordsContract.TABLE_NAME, RECORD);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_RecordsContract.TABLE_NAME + "/#", RECORD_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_ScheduledMedicationContract.TABLE_NAME, SCHEDULE);
        uriMatcher.addURI(CONTENT_AUTHORITY, SQL_ScheduledMedicationContract.TABLE_NAME + "/#", SCHEDULE_ID);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = ApplicationDatabase.getAccessInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match = sURI_MATCHER.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case BOOKMARKS: {
                queryBuilder.setTables(SQL_BookmarksContract.TABLE_NAME);
                break;
            }
            case BOOKMARKS_ID: {
                queryBuilder.setTables(SQL_BookmarksContract.TABLE_NAME);
                long articleID = SQL_BookmarksContract.getArticleID(uri);
                queryBuilder.appendWhere(SQL_DoctorsContract.Columns._ID + " = " + articleID);
                break;
            }
            case DOCTORS: {
                queryBuilder.setTables(SQL_DoctorsContract.TABLE_NAME);
                break;
            }
            case DOCTORS_ID: {
                queryBuilder.setTables(SQL_DoctorsContract.TABLE_NAME);
                long doctorID = SQL_DoctorsContract.getDoctorsID(uri);
                queryBuilder.appendWhere(SQL_DoctorsContract.Columns._ID + " = " + doctorID);
                break;
            }
            case MEDICATIONS: {
                queryBuilder.setTables(SQL_MedicationContract.TABLE_NAME);
                break;
            }
            case MEDICATIONS_ID: {
                queryBuilder.setTables(SQL_MedicationContract.TABLE_NAME);
                long medicationID = SQL_MedicationContract.getMedicationID(uri);
                queryBuilder.appendWhere(SQL_MedicationContract.Columns._ID + " = " + medicationID);
                break;
            }
            case USER: {
                queryBuilder.setTables(SQL_UserContract.TABLE_NAME);
                break;
            }
            case USER_ID: {
                queryBuilder.setTables(SQL_UserContract.TABLE_NAME);
                long userID = SQL_UserContract.getUserID(uri);
                queryBuilder.appendWhere(SQL_UserContract.Columns._ID + " = " +userID);
                break;
            }
            case APPOINTMENT: {
                queryBuilder.setTables(SQL_AppointmentsContract.TABLE_NAME);
                break;
            }
            case APPOINTMENT_ID: {
                queryBuilder.setTables(SQL_AppointmentsContract.TABLE_NAME);
                long appointmentID = SQL_AppointmentsContract.getappointmentID(uri);
                queryBuilder.appendWhere(SQL_AppointmentsContract.Columns._ID + " = " +appointmentID);
                break;
            }
            case RECORD: {
                queryBuilder.setTables(SQL_RecordsContract.TABLE_NAME);
                break;
            }
            case RECORD_ID: {
                queryBuilder.setTables(SQL_RecordsContract.TABLE_NAME);
                long recordMedsID = SQL_RecordsContract.getRecordID(uri);
                queryBuilder.appendWhere(SQL_RecordsContract.Columns._ID + " = " + recordMedsID);
                break;
            }
            case SCHEDULE: {
                queryBuilder.setTables(SQL_ScheduledMedicationContract.TABLE_NAME);
                break;
            }
            case SCHEDULE_ID: {
                queryBuilder.setTables(SQL_ScheduledMedicationContract.TABLE_NAME);
                long scheduledMedsID = SQL_ScheduledMedicationContract.getMedicationScheduledID(uri);
                queryBuilder.appendWhere(SQL_ScheduledMedicationContract.Columns._ID + " = " + scheduledMedsID);
                break;
            }
            default:
                return null;
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Log.d(TAG, "query: rows in returned cursor = " + cursor.getCount() );
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "updated: notify change with " + uri);
        getContext().getContentResolver().notifyChange(uri, null);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert: entering");
        final int match = sURI_MATCHER.match(uri);
        Log.d(TAG, "insert: URI_MATCHER REYRNED : " + match );

        final SQLiteDatabase db;
        Uri returnURI;
        long recordID;

        switch (match) {
            case BOOKMARKS: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_BookmarksContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_BookmarksContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case DOCTORS: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_DoctorsContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_DoctorsContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case MEDICATIONS: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_MedicationContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_MedicationContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case USER: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_UserContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_UserContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case APPOINTMENT: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_AppointmentsContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_UserContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case RECORD: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_RecordsContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_RecordsContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            case SCHEDULE: {
                db = mOpenHelper.getWritableDatabase();
                recordID = db.insert(SQL_ScheduledMedicationContract.TABLE_NAME, null, contentValues);
                if( recordID >= 0 ) {
                    returnURI = SQL_ScheduledMedicationContract.buildTaskUri(recordID);
                }else {
                    throw new android.database.SQLException("Failed to insert into : " + uri.toString());
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Uri Table in Insert: " + uri);
        }
        Log.d(TAG, "insert: exiting");
        Log.d(TAG, "updated: notify change with " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return returnURI;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "Entering Delete: ");
        final int match = sURI_MATCHER.match(uri);
        Log.d(TAG, "Matcher URI delete: " + match);
        final SQLiteDatabase db;
        int recordID;
        String selectionCriteria;

        switch (match) {
            case BOOKMARKS:
                db = mOpenHelper.getWritableDatabase();
                recordID = db.delete(SQL_BookmarksContract.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOKMARKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long articleID = SQL_BookmarksContract.getArticleID(uri);
                selectionCriteria = SQL_BookmarksContract.Columns._ID + " = " + articleID;
                recordID = db.delete(SQL_BookmarksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            case DOCTORS:
                db = mOpenHelper.getWritableDatabase();
                recordID = db.delete(SQL_DoctorsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case DOCTORS_ID:
                db = mOpenHelper.getWritableDatabase();
                long doctorsID = SQL_DoctorsContract.getDoctorsID(uri);
                selectionCriteria = SQL_DoctorsContract.Columns._ID + " = " + doctorsID;

                recordID = db.delete(SQL_DoctorsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            case APPOINTMENT_ID:
                db = mOpenHelper.getWritableDatabase();
                long appointmentID = SQL_AppointmentsContract.getappointmentID(uri);
                selectionCriteria = SQL_AppointmentsContract.Columns._ID + " = " + appointmentID;

                recordID = db.delete(SQL_AppointmentsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            case RECORD_ID:
                db = mOpenHelper.getWritableDatabase();
                long recordMedID = SQL_RecordsContract.getRecordID(uri);
                selectionCriteria = SQL_RecordsContract.Columns._ID + " = " + recordMedID;
                recordID = db.delete(SQL_RecordsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            case RECORD:
                db = mOpenHelper.getWritableDatabase();
                recordID = db.delete(SQL_RecordsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case SCHEDULE_ID:
                db = mOpenHelper.getWritableDatabase();
                long recordScheduledID = SQL_ScheduledMedicationContract.getMedicationScheduledID(uri);
                selectionCriteria = SQL_ScheduledMedicationContract.Columns._ID + " = " + recordScheduledID;
                recordID = db.delete(SQL_ScheduledMedicationContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("");
        }

        if( recordID >= 0 ) {
            Log.d(TAG, "delete: notify change with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nothign deleted");
        }

        Log.d(TAG, "Exiting Delete, deleted row: " + recordID);
        Log.d(TAG, "updated: notify change with " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return recordID;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "Entering Update: ");
        final int match = sURI_MATCHER.match(uri);
        Log.d(TAG, "Match is : " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case DOCTORS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(SQL_DoctorsContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case DOCTORS_ID:
                db = mOpenHelper.getWritableDatabase();
                long doctorID = SQL_DoctorsContract.getDoctorsID(uri);
                selectionCriteria = SQL_DoctorsContract.Columns._ID + " = " + doctorID;

                Log.d(TAG, "SELECTION LOOKS LIKE THIS: " + selectionCriteria);
                Log.d(TAG, "SELECTION CRITERIA LOOKS LIKE THIS: " + selectionCriteria);

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }

                count = db.update(SQL_DoctorsContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            case USER:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(SQL_UserContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case USER_ID:
                db = mOpenHelper.getWritableDatabase();
                long userID = SQL_UserContract.getUserID(uri);
                selectionCriteria = SQL_UserContract.Columns._ID + " = " + userID;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }

                count = db.update(SQL_UserContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            case APPOINTMENT:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(SQL_AppointmentsContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case APPOINTMENT_ID:
                db = mOpenHelper.getWritableDatabase();
                long appointmentID = SQL_AppointmentsContract.getappointmentID(uri);
                selectionCriteria = SQL_AppointmentsContract.Columns._ID + " = " + appointmentID;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }

                count = db.update(SQL_AppointmentsContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            case RECORD:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(SQL_RecordsContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case RECORD_ID:
                db = mOpenHelper.getWritableDatabase();
                long recordID = SQL_RecordsContract.getRecordID(uri);
                selectionCriteria = SQL_RecordsContract.Columns._ID + " = " + recordID ;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(SQL_RecordsContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            case SCHEDULE:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(SQL_ScheduledMedicationContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case SCHEDULE_ID:
                db = mOpenHelper.getWritableDatabase();
                long recordScheduledID = SQL_ScheduledMedicationContract.getMedicationScheduledID(uri);
                selectionCriteria = SQL_ScheduledMedicationContract.Columns._ID + " = " + recordScheduledID;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(SQL_ScheduledMedicationContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri: " + uri);
        }

        Log.d(TAG, "updated: notify change with " + uri);
        getContext().getContentResolver().notifyChange(uri, null);

        Log.d(TAG, "Exiting Update, retuning number of affected rows: " + count);
        return count;
    }
}
