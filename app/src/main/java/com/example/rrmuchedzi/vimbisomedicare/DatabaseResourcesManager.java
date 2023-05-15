package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DatabaseResourcesManager {

    ContentResolver contentResolver;
    private static final String TAG = "DatabaseResourcesManage";
    private UserObject userDetails;

    private List<MedicationScheduled> mMedicationScheduledsUnfiltered;
    private List<DuplicatedScheduled> mMorning;
    private List<DuplicatedScheduled> mAfternoon;
    private List<DuplicatedScheduled> mEvening;
    private List<DuplicatedScheduled> mNight;

    private List<RecordViewer> mRecordsContainer;

    DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    private int [] scheduledEventsTotal = new int[4];

    public DatabaseResourcesManager(Context context) {
        contentResolver = context.getContentResolver();
    }

    private void setUpUserDetails() { //Get user current details object

        String[] projection = {
                SQL_UserContract.Columns._ID,
                SQL_UserContract.Columns.NAME,
                SQL_UserContract.Columns.SURNAME,
                SQL_UserContract.Columns.AVATAR_ID,
                SQL_UserContract.Columns.EMAIL,
                SQL_UserContract.Columns.SECURITY
        };

        Cursor cursor = contentResolver.query(SQL_UserContract.CONTENT_URI, projection, null, null, null);

        int _ID = 0;
        String NAME = null;
        String SURNAME = null;
        int AVATAR = 1;
        String EMAIL = null;
        int PASSCODE = 1;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch (cursor.getColumnName(i)) {
                            case SQL_UserContract.Columns._ID: {
                                _ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_UserContract.Columns.NAME: {
                                NAME = cursor.getString(i);
                                break;
                            }
                            case SQL_UserContract.Columns.SURNAME: {
                                SURNAME = cursor.getString(i);
                                break;
                            }
                            case SQL_UserContract.Columns.AVATAR_ID: {
                                AVATAR = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_UserContract.Columns.EMAIL: {
                                EMAIL = cursor.getString(i);
                                break;
                            }
                            case SQL_UserContract.Columns.SECURITY: {
                                PASSCODE = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            default:
                                Log.e(TAG, "getScheduledMedication: unrecognissed column :::: " + cursor.getColumnName(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getScheduledMedication: " + e.getMessage());
                    }
                }

                userDetails = new UserObject(_ID, NAME, SURNAME, AVATAR, EMAIL, PASSCODE);
                userDetails.toString();
                break;
            }

        }
    }

    public UserObject getUserDetails() {
        if (userDetails != null) {
            return userDetails;
        }
        setUpUserDetails();
        return userDetails;
    }

    public void setUpAndRetriveAlarmsScehduled() {

    }

    public int[] getScheduledEvents() {

        mMorning = new ArrayList<>();
        mAfternoon = new ArrayList<>();
        mEvening = new ArrayList<>();
        mNight = new ArrayList<>();

        mMedicationScheduledsUnfiltered = new ArrayList<>();

        String[] projection = {
                SQL_MedicationContract.Columns._ID,
                SQL_MedicationContract.Columns.NAME,
                SQL_MedicationContract.Columns.QUANTITY,
                SQL_MedicationContract.Columns.QUANTITY_UNITS,
                SQL_MedicationContract.Columns.START_DATE,
                SQL_MedicationContract.Columns.DURATION,
                SQL_MedicationContract.Columns.DURATION_UNITS,
                SQL_MedicationContract.Columns.END_DATE,
                SQL_MedicationContract.Columns.NOTES,
                SQL_MedicationContract.Columns.PILL_ID,
                SQL_MedicationContract.Columns.TIMES,
                SQL_MedicationContract.Columns.ACTIVE
        };

        Calendar calendar = Calendar.getInstance();
        int dayID = calendar.get(Calendar.DAY_OF_WEEK);
        String currentDay = SQL_MedicationContract.Columns.SUNDAY;

        switch (dayID) {
            case Calendar.SUNDAY:
                break;
            case Calendar.MONDAY:
                currentDay = SQL_MedicationContract.Columns.MONDAY;
                break;
            case Calendar.TUESDAY:
                currentDay = SQL_MedicationContract.Columns.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                currentDay = SQL_MedicationContract.Columns.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                currentDay = SQL_MedicationContract.Columns.THURSDAY;
                break;
            case Calendar.FRIDAY:
                currentDay = SQL_MedicationContract.Columns.FRIDAY;
                break;
            case Calendar.SATURDAY:
                currentDay = SQL_MedicationContract.Columns.SATURDAY;
                break;
        }

        String condition = "( " + SQL_MedicationContract.Columns.ACTIVE + " = 1 AND" + currentDay + " = 1 )";
        Cursor cursor = contentResolver.query(SQL_MedicationContract.CONTENT_URI, projection, null, null, SQL_MedicationContract.Columns._ID);

        int _ID = 0;
        String NAME = null;
        int QUANTITY = 0;
        String QUANTITY_UNITS = null;
        Date START_DATE = null;
        int DURATION = 0;
        String DURATION_UNITS = null;
        Date END_DATE = null;
        String NOTES = null;
        int PILL_ID = 0;
        MedicationTimes TIMES = null;
        boolean ACTIVE = false;

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch (cursor.getColumnName(i)) {
                            case SQL_MedicationContract.Columns._ID: {
                                _ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.NAME: {
                                NAME = cursor.getString(i);
                                break;
                            }
                            case SQL_MedicationContract.Columns.QUANTITY: {
                                QUANTITY = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.QUANTITY_UNITS: {
                                QUANTITY_UNITS = cursor.getString(i);
                                break;
                            }
                            case SQL_MedicationContract.Columns.START_DATE: {
                                START_DATE = mDateFormat.parse(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.DURATION: {
                                DURATION = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.DURATION_UNITS: {
                                DURATION_UNITS = cursor.getString(i);
                                break;
                            }
                            case SQL_MedicationContract.Columns.END_DATE: {
                                END_DATE = mDateFormat.parse(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.NOTES: {
                                NOTES = cursor.getString(i);
                                break;
                            }
                            case SQL_MedicationContract.Columns.PILL_ID: {
                                PILL_ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_MedicationContract.Columns.TIMES: {
                                byte[] blob = cursor.getBlob(i);
                                String json = new String(blob);
                                Gson gson = new Gson();
                                TIMES = gson.fromJson(json, new TypeToken<MedicationTimes>() {
                                }.getType());
                                Log.d(TAG, "getScheduledMedication: TIMES CONVERTED TO GET : " + TIMES.toString());
                                break;
                            }
                            case SQL_MedicationContract.Columns.ACTIVE: {
                                ACTIVE = Boolean.parseBoolean(cursor.getString(i));
                                break;
                            }
                            default:
                                Log.e(TAG, "getScheduledMedication: unrecognissed column :::: " + cursor.getColumnName(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getScheduledMedication: " + e.getMessage());
                    }
                }

                mMedicationScheduledsUnfiltered.add(new MedicationScheduled(_ID, NAME, QUANTITY, QUANTITY_UNITS, START_DATE, END_DATE, DURATION, DURATION_UNITS, PILL_ID, TIMES, NOTES, ACTIVE));
                Log.d(TAG, "onCreate: =======================================");

            }
            cursor.close();
            filterScheduledEvents();

            for (DuplicatedScheduled ele: mNight ) {
                Log.d(TAG, "TIME AFTER FILTER: " + ele.getTime() );
            }
            sortElementTimes();

            mMedicationScheduledsUnfiltered = null;
        }

        return scheduledEventsTotal;
    }

    private void filterScheduledEvents() {

        boolean dayStatus = false;

        for (MedicationScheduled element : mMedicationScheduledsUnfiltered) {

            HashMap<String, String> times = element.getMedicationTimes().getTimes();

            for (String timeFound : times.keySet()) {

                if (timeFound != null && timeFound.trim().length() > 0) {
                    //Log.d(TAG, "checkTimes: TIME TESTED IS : " + timeFound);
                    Date comparison;
                    try {
                        comparison = mTimeFormat.parse(timeFound);
                        if ((comparison.equals(mTimeFormat.parse("00:00")) || comparison.after(mTimeFormat.parse("00:00"))) && comparison.before(mTimeFormat.parse("05:00"))) {
                            mNight.add( new DuplicatedScheduled(element.get_ID(), comparison, element.getMedicName(), element.getMedicQuantity(), element.getMedicUnits(), element.getStartDate(), element.getEndDate(), element.getDuration(), element.getDurationUnits(), element.getMedicImageID(), element.getNotes(), element.isMedicStatus() ));
                            scheduledEventsTotal[3] += 1;
                        } else if ((comparison.equals(mTimeFormat.parse("05:00")) || comparison.after(mTimeFormat.parse("05:00"))) && comparison.before(mTimeFormat.parse("12:00"))) {
                            mMorning.add( new DuplicatedScheduled(element.get_ID(), comparison, element.getMedicName(), element.getMedicQuantity(), element.getMedicUnits(), element.getStartDate(), element.getEndDate(), element.getDuration(), element.getDurationUnits(), element.getMedicImageID(), element.getNotes(), element.isMedicStatus() ));
                            scheduledEventsTotal[0] += 1;
                        } else if ((comparison.equals(mTimeFormat.parse("12:00")) || comparison.after(mTimeFormat.parse("12:00"))) && comparison.before(mTimeFormat.parse("17:00"))) {
                            mAfternoon.add( new DuplicatedScheduled(element.get_ID(), comparison, element.getMedicName(), element.getMedicQuantity(), element.getMedicUnits(), element.getStartDate(), element.getEndDate(), element.getDuration(), element.getDurationUnits(), element.getMedicImageID(), element.getNotes(), element.isMedicStatus() ));
                            scheduledEventsTotal[1] += 1;
                        } else if ((comparison.equals(mTimeFormat.parse("17:00")) || comparison.after(mTimeFormat.parse("17:00"))) && comparison.before(mTimeFormat.parse("00:00"))) {
                            mEvening.add( new DuplicatedScheduled(element.get_ID(), comparison, element.getMedicName(), element.getMedicQuantity(), element.getMedicUnits(), element.getStartDate(), element.getEndDate(), element.getDuration(), element.getDurationUnits(), element.getMedicImageID(), element.getNotes(), element.isMedicStatus() ));
                            scheduledEventsTotal[2] += 1;
                        }
                    } catch (ParseException e) {
                        Log.e(TAG, "checkTimes: Failed to Pasrse time" + e.getMessage());
                    }
                }
            }
        }
    }

    private void sortElementTimes() {
        Collections.sort(mMorning, new Comparator<DuplicatedScheduled>() {
            @Override
            public int compare(DuplicatedScheduled o1, DuplicatedScheduled o2) {
                if( o1.getTime().before(o2.getTime()) )
                    return -1;
                if( o1.getTime().after(o2.getTime()) )
                    return 1;

                return 0;
            }
        });

        Collections.sort(mAfternoon, new Comparator<DuplicatedScheduled>() {
            @Override
            public int compare(DuplicatedScheduled o1, DuplicatedScheduled o2) {
                if( o1.getTime().before(o2.getTime()) )
                    return -1;
                if( o1.getTime().after(o2.getTime()) )
                    return 1;

                return 0;
            }
        });
        Collections.sort(mEvening, new Comparator<DuplicatedScheduled>() {
            @Override
            public int compare(DuplicatedScheduled o1, DuplicatedScheduled o2) {
                if( o1.getTime().before(o2.getTime()) )
                    return -1;
                if( o1.getTime().after(o2.getTime()) )
                    return 1;

                return 0;
            }
        });
        Collections.sort(mNight, new Comparator<DuplicatedScheduled>() {
            @Override
            public int compare(DuplicatedScheduled o1, DuplicatedScheduled o2) {
                if( o1.getTime().before(o2.getTime()) )
                    return -1;
                if( o1.getTime().after(o2.getTime()) )
                    return 1;

                return 0;
            }
        });

    }

    private void loadDayRecords() {
        mRecordsContainer = new ArrayList<>();

        String[] projection = {

        };


    }

    public ScheduledEvent getCurrentScheduledMedication() {
        //Implemented for final version of the app
        Log.d(TAG, "getCurrentScheduledMedication: ENTERED");
        String[] projection = {
                SQL_ScheduledMedicationContract.Columns.MEDICATION_ID,
                SQL_ScheduledMedicationContract.Columns.TIME_SCHEDULED,
                SQL_ScheduledMedicationContract.Columns.DATE_SCHEDULED,
        };

        Integer medication_id = null;
        Date time_scheduled = null;
        Date date_scheduled = null;
        boolean cursorHad = false;
        String condition = SQL_ScheduledMedicationContract.Columns._ID + " = 1";
        Cursor cursor = contentResolver.query(SQL_ScheduledMedicationContract.CONTENT_URI, projection, condition, null, null);

        if ( cursor != null ) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                cursorHad = true;
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch (cursor.getColumnName(i)) {
                            case SQL_ScheduledMedicationContract.Columns.MEDICATION_ID: {
                                medication_id = Integer.parseInt( cursor.getString( i ) );
                                break;
                            }
                            case SQL_ScheduledMedicationContract.Columns.TIME_SCHEDULED: {
                                time_scheduled = mTimeFormat.parse( cursor.getString(i) );
                                Log.d(TAG, "getCurrentScheduledMedication: TIME IS : " + time_scheduled);
                                break;
                            }
                            case SQL_ScheduledMedicationContract.Columns.DATE_SCHEDULED: {
                                date_scheduled = mDateFormat.parse( cursor.getString(i) );
                                break;
                            }
                            default:
                                Log.e(TAG, "getScheduledMedication: unrecognissed column :::: " + cursor.getColumnName(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getScheduledMedication: " + e.getMessage());
                    }
                }
            }
            cursor.close();
            Log.d(TAG, "getCurrentScheduledMedication: ENDED SUCCESSFULLY");
            if (cursorHad)
                return new ScheduledEvent(1, medication_id, date_scheduled, time_scheduled );
        }
        Log.d(TAG, "getCurrentScheduledMedication: ENDED WITH NULL");
        return null;
    }

    public void saveNewScheduleUpdate( ScheduledEvent eventScheduled ) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(SQL_ScheduledMedicationContract.Columns._ID, 1);
        contentValues.put(SQL_ScheduledMedicationContract.Columns.MEDICATION_ID, eventScheduled.getMedication_ID());
        contentValues.put(SQL_ScheduledMedicationContract.Columns.TIME_SCHEDULED, eventScheduled.getScheduled_time().toString());
        contentValues.put(SQL_ScheduledMedicationContract.Columns.DATE_SCHEDULED, eventScheduled.getSchedule_date().toString());

        String where = SQL_ScheduledMedicationContract.Columns._ID +" = 1";

        int count = contentResolver.update(SQL_ScheduledMedicationContract.CONTENT_URI, contentValues, where, null);

        if( count <= 0 ) {
            Uri uri = contentResolver.insert(SQL_ScheduledMedicationContract.CONTENT_URI, contentValues);
            Log.d(TAG, "saveNewScheduleUpdate: uri : "+ uri);
        } else {
            Log.d(TAG, "saveNewScheduleUpdate: Inserted at : " + count);
        }
    }

    public boolean saveMedicationHistory( HistoryObject historyObject ) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(SQL_RecordsContract.Columns.MEDICATION_NAME, historyObject.getMedication_name());
        contentValues.put(SQL_RecordsContract.Columns.DATE_TAKE, mDateFormat.format(historyObject.getMedication_date()) );
        contentValues.put(SQL_RecordsContract.Columns.TIME_TAKE, mTimeFormat.format(historyObject.getMedication_time()));
        contentValues.put(SQL_RecordsContract.Columns.MEDICATION_ID, historyObject.getMedication_ID());
        contentValues.put(SQL_RecordsContract.Columns.AVATAR_ID, historyObject.getMedication_image_id());
        contentValues.put(SQL_RecordsContract.Columns.DOSAGE_TAKEN, historyObject.getMedication_dosage() + " " + historyObject.getMedication_dosage_quantity());

        Uri uri = contentResolver.insert(SQL_RecordsContract.CONTENT_URI, contentValues);
        Log.d(TAG, "saveMedicationHistory: uri : "+ uri);

        return true;
    }

    public boolean deleteMedicationHistory( HistoryObject historyObject ) {

        String projection [] = {
                SQL_RecordsContract.Columns._ID,
                SQL_RecordsContract.Columns.MEDICATION_ID,
                SQL_RecordsContract.Columns.MEDICATION_NAME,
                SQL_RecordsContract.Columns.TIME_TAKE,
        };

        Cursor cursor = contentResolver.query(SQL_RecordsContract.CONTENT_URI, projection, null, null, null);
        boolean cursorHad = false;

        Integer _ID = null;
        Integer Medication_ID = null;
        String Medication_Name = null;
        String Time_Taken = null;

        if ( cursor != null ) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                cursorHad = true;
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch (cursor.getColumnName(i)) {
                            case SQL_RecordsContract.Columns.MEDICATION_ID: {
                                Medication_ID = Integer.parseInt( cursor.getString( i ) );
                                break;
                            }
                            case SQL_RecordsContract.Columns.TIME_TAKE: {
                                Time_Taken = cursor.getString(i);
                                break;
                            }
                            case SQL_RecordsContract.Columns.MEDICATION_NAME: {
                                Medication_Name = cursor.getString(i);
                                break;
                            }
                            case SQL_RecordsContract.Columns._ID: {
                                _ID = Integer.parseInt( cursor.getString( i ) );
                                break;
                            }
                            default:
                                Log.e(TAG, "getScheduledMedication: unrecognissed column :::: " + cursor.getColumnName(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getScheduledMedication: " + e.getMessage());
                    }
                }

                if( cursorHad && Medication_Name != null && Time_Taken != null ) {
                    Log.d(TAG, "deleteMedicationHistory: FIRST STAGE DELETE");
                    if( Medication_ID == historyObject.getMedication_ID() && Time_Taken.equalsIgnoreCase(mTimeFormat.format(historyObject.getMedication_time())) ) {
                        Log.d(TAG, "deleteMedicationHistory: Found ID " + _ID);
                        break;
                    }
                }
            }
            cursor.close();
        }

        String conditionDelete = SQL_RecordsContract.Columns._ID +" = "+ _ID;
        int i = contentResolver.delete(SQL_RecordsContract.CONTENT_URI, conditionDelete, null);
        Log.d(TAG, "deleted rows: uri : "+ i);

        return true;
    }

    public void setupDayRecords() {
        loadDayRecords();
    }

    public List<DuplicatedScheduled> getMorning() {
        return mMorning;
    }

    public List<DuplicatedScheduled> getAfternoon() {
        return mAfternoon;
    }

    public List<DuplicatedScheduled> getEvening() {
        return mEvening;
    }

    public List<DuplicatedScheduled> getNight() {
        return mNight;
    }
}
