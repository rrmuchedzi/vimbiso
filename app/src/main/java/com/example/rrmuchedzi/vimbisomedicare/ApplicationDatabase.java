package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ApplicationDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "VimbisoMediCare.db";
    public static final int DATABASE_VERSION = 1;

    private static ApplicationDatabase ACCESS_INSTANCE = null;

    private ApplicationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static ApplicationDatabase getAccessInstance(Context context) {
        if( ACCESS_INSTANCE == null ) {
            ACCESS_INSTANCE = new ApplicationDatabase(context);
        }
        return ACCESS_INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLCommandMedicationTable, SQLCommandUserTable, SQLCommandAppointmentsTable, SQLCommandDoctorsTable, SQLCommandBookmarksTable, SQLCommandRecordsTable, SQLCommandScheduleTable;

        SQLCommandMedicationTable = "CREATE TABLE "+ SQL_MedicationContract.TABLE_NAME + "( " +
                SQL_MedicationContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_MedicationContract.Columns.NAME + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.QUANTITY + " REAL NOT NULL, " +
                SQL_MedicationContract.Columns.QUANTITY_UNITS + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.START_DATE + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.DURATION + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.DURATION_UNITS + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.END_DATE + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.TIMES + " BLOB NOT NULL, " +
                SQL_MedicationContract.Columns.NOTES + " TEXT NOT NULL, " +
                SQL_MedicationContract.Columns.ACTIVE+ " INTEGER NOT NULL, " +
                SQL_MedicationContract.Columns.PILL_ID + " INTEGER NOT NULL, " +
                SQL_MedicationContract.Columns.MONDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.TUESDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.WEDNESDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.THURSDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.FRIDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.SATURDAY + " INTEGER, " +
                SQL_MedicationContract.Columns.SUNDAY + " INTEGER);";

        SQLCommandAppointmentsTable = "CREATE TABLE "+ SQL_AppointmentsContract.TABLE_NAME + "( " +
                SQL_AppointmentsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_AppointmentsContract.Columns.TITLE + " TEXT NOT NULL, " +
                SQL_AppointmentsContract.Columns.DOCTOR + " TEXT, " +
                SQL_AppointmentsContract.Columns.DOCTOR_AVATAR + " INTEGER, " +
                SQL_AppointmentsContract.Columns.DATE + " TEXT NOT NULL, " +
                SQL_AppointmentsContract.Columns.TIME + " TEXT NOT NULL, " +
                SQL_AppointmentsContract.Columns.REMINDER_TIME+ " TEXT NOT NULL, " +
                SQL_AppointmentsContract.Columns.REMINDER_STATUS + " TEXT NOT NULL, " +
                SQL_AppointmentsContract.Columns.LOCATION + " TEXT, " +
                SQL_AppointmentsContract.Columns.NOTES + " TEXT);";

        SQLCommandBookmarksTable = "CREATE TABLE "+ SQL_BookmarksContract.TABLE_NAME + "( " +
                SQL_BookmarksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_BookmarksContract.Columns.TITLE + " TEXT NOT NULL UNIQUE, " +
                SQL_BookmarksContract.Columns.IMAGE_URL + " TEXT NOT NULL, " +
                SQL_BookmarksContract.Columns.PUBLISHED_DATE + " TEXT NOT NULL, " +
                SQL_BookmarksContract.Columns.STORY + " TEXT NOT NULL );";

        SQLCommandDoctorsTable= "CREATE TABLE "+ SQL_DoctorsContract.TABLE_NAME + "( " +
                SQL_DoctorsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_DoctorsContract.Columns.NAME + " INTEGER NOT NULL UNIQUE, " +
                SQL_DoctorsContract.Columns.AVATAR_ID+ " TEXT NOT NULL, " +
                SQL_DoctorsContract.Columns.SPECIALITY + " TEXT, " +
                SQL_DoctorsContract.Columns.FIRST_NUMBER + " TEXT, " +
                SQL_DoctorsContract.Columns.FIRST_NUMBER_TYPE + " TEXT, " +
                SQL_DoctorsContract.Columns.SECOND_NUMBER + " TEXT, " +
                SQL_DoctorsContract.Columns.SECOND_NUMBER_TYPE + " TEXT, " +
                SQL_DoctorsContract.Columns.EMAIL + " TEXT, " +
                SQL_DoctorsContract.Columns.ADDRESS + " TEXT, " +
                SQL_DoctorsContract.Columns.NOTES_DOCTOR + " TEXT, " +
                SQL_DoctorsContract.Columns.HOSPITAL + " TEXT);";

        SQLCommandUserTable = "CREATE TABLE "+ SQL_UserContract.TABLE_NAME + "( " +
                SQL_UserContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_UserContract.Columns.NAME + " TEXT, " +
                SQL_UserContract.Columns.SURNAME + " TEXT, " +
                SQL_UserContract.Columns.AVATAR_ID + " INTEGER NOT NULL, " +
                SQL_UserContract.Columns.EMAIL + " TEXT, " +
                SQL_UserContract.Columns.SECURITY + " INTEGER);";

        SQLCommandRecordsTable = "CREATE TABLE "+ SQL_RecordsContract.TABLE_NAME + "( " +
                SQL_RecordsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SQL_RecordsContract.Columns.MEDICATION_NAME + " TEXT NOT NULL, " +
                SQL_RecordsContract.Columns.DOSAGE_TAKEN + " TEXT NOT NULL, " +
                SQL_RecordsContract.Columns.DATE_TAKE + " TEXT NOT NULL, " +
                SQL_RecordsContract.Columns.MEDICATION_ID + " INTEGER NOT NULL, " +
                SQL_RecordsContract.Columns.AVATAR_ID + " INTEGER NOT NULL, " +
                SQL_RecordsContract.Columns.TIME_TAKE + " TEXT NOT NULL);";

        SQLCommandScheduleTable = "CREATE TABLE "+ SQL_ScheduledMedicationContract.TABLE_NAME + "( " +
                SQL_ScheduledMedicationContract.Columns._ID + " INTEGER NOT NULL, " +
                SQL_ScheduledMedicationContract.Columns.MEDICATION_ID + " INTEGER NOT NULL, " +
                SQL_ScheduledMedicationContract.Columns.DATE_SCHEDULED + " TEXT NOT NULL, " +
                SQL_ScheduledMedicationContract.Columns.TIME_SCHEDULED + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQLCommandBookmarksTable);
        sqLiteDatabase.execSQL(SQLCommandDoctorsTable);
        sqLiteDatabase.execSQL(SQLCommandMedicationTable);
        sqLiteDatabase.execSQL(SQLCommandUserTable);
        sqLiteDatabase.execSQL(SQLCommandAppointmentsTable);
        sqLiteDatabase.execSQL(SQLCommandRecordsTable);
        sqLiteDatabase.execSQL(SQLCommandScheduleTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
