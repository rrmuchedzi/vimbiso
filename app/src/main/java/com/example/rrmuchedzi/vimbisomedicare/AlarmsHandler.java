package com.example.rrmuchedzi.vimbisomedicare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rufaro R.Muchedzi on 12/18/2017.
 */

public class AlarmsHandler {

    private static final String TAG = "AlarmsHandler";
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private Intent mAlarmIntent;
    private Calendar mCalendar;
    Context mContext;
    DatabaseResourcesManager mDatabaseResourcesManager;
    private ScheduledEvent mScheduledEvent;

    private boolean checkResult = true;

    DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat mTimeFormat = new SimpleDateFormat("HH:mm");
    List<DuplicatedScheduled> timeComparizones;
    private int timeSection;

    private Date newTempAlarmTime;

    public AlarmsHandler(Context context) {
        mContext = context;
    }

    public void setUpAlarmSchedule(DatabaseResourcesManager upAlarmSchedule) {
        Log.d(TAG, "setUpAlarmSchedule: STARTS");
        mDatabaseResourcesManager = upAlarmSchedule;
        mScheduledEvent = mDatabaseResourcesManager.getCurrentScheduledMedication();

        if (mScheduledEvent != null) {
            Log.d(TAG, "setUpAlarmSchedule: ENTRY SCHEDULED FOUND");
            if (checkDateComparizon(mScheduledEvent.getSchedule_date())) {

                if (checkTimeComparison(mScheduledEvent.getScheduled_time())) { //Checking time

                    Log.d(TAG, "setUpAlarmSchedule: " + mScheduledEvent.getScheduled_time().toString());
                    timeSection = checkTimeSectionComparizon(mScheduledEvent.getScheduled_time().toString());
                    checkResult = true;
                    getScheduledTimes(timeSection);

                    if( checkResult && timeComparizones != null ) {
                        Date newAlarmTime = null;
                        newTempAlarmTime = mScheduledEvent.getScheduled_time();
                        for ( DuplicatedScheduled element: timeComparizones ) {
                            if (element.getTime().before(newTempAlarmTime) && element.getTime().after( new Date() )) {
                                newTempAlarmTime = element.getTime();
                            }
                        }

                        if( !newTempAlarmTime.equals(mScheduledEvent.getScheduled_time() ) ) {
                            //Setting new Alarm
                            Log.d(TAG, "setUpAlarmSchedule: Setting new alarm with new time");
                        }
                    }
                    Log.d(TAG, "setUpAlarmSchedule: ALARM ALREADY SCHEDULED");
                    return;
                } else {
                    Log.d(TAG, "setUpAlarmSchedule: SOMETHING TO DO WITH TIME");
                }
            } else {
                Log.d(TAG, "setUpAlarmSchedule: OLD DATE OR FUTURE DATE");
            }
        } else {
            Log.d(TAG, "setUpAlarmSchedule: NO CURRENT SCHEDULED EVENT");
        }

        scheduleNewAlarm();

        //Log.d(TAG, "setUpAlarmSchedule: ENDS, TIME SET - " + mCalendar.getTime());
    }

    private void scheduleNewAlarm() {
        Log.d(TAG, "scheduleNewAlarm: STARTED");
        Calendar c = Calendar.getInstance();
        timeSection = checkTimeSectionComparizon(mTimeFormat.format(c.getTime()));
        getScheduledTimes(timeSection);

        if( timeComparizones != null ) {
            Log.d(TAG, "scheduleNewAlarm: Setting a new alarm " + timeComparizones.size());
//            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//            mCalendar = Calendar.getInstance();
//            mCalendar.set( Calendar.MINUTE, 29 );
//
//            mAlarmIntent = new Intent(mContext, AlarmReciever.class);
//            mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mAlarmIntent, 0);
//            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), mPendingIntent);
            Calendar cg = Calendar.getInstance();
            Date date = new Date();
            String dateToday = mDateFormat.format(date);
            String timeToday = mTimeFormat.format(cg.getTime());
            Log.d(TAG, "scheduleNewAlarm: DATE AS : "+ dateToday);
            Log.d(TAG, "scheduleNewAlarm: TIME AS : "+ timeToday);
            try {mDatabaseResourcesManager.saveNewScheduleUpdate( new ScheduledEvent(1, 1, mTimeFormat.parse(timeToday), mDateFormat.parse(dateToday) ) );} catch (ParseException e) {
                Log.e(TAG, "scheduleNewAlarm: PARSE FAILED" + e.getMessage() );
            }
        }

        return;
    }

    public void stopAlarmScheduled() {

    }

    public boolean checkDateComparizon(Date dateFound) {
        Log.d(TAG, "checkDateComparizon: COMPARING + " + dateFound + "  -- With : "+ new Date());
        if (new Date().equals(dateFound)) {
            return true;
        }
        return false;
    }

    private boolean checkTimeComparison(Date timeFound) {
        Date currentTime = Calendar.getInstance().getTime();
        if (currentTime.before(timeFound)) {
            return true;
        }
        return false;
    }

    public int checkTimeSectionComparizon(String timeFound) {
        SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.US);
        Date comparison;
        try {
            comparison = inputParser.parse(timeFound);
            if ((comparison.equals(inputParser.parse("00:00")) || comparison.after(inputParser.parse("00:00"))) && comparison.before(inputParser.parse("05:00"))) {
                return 3;
            } else if ((comparison.equals(inputParser.parse("05:00")) || comparison.after(inputParser.parse("05:00"))) && comparison.before(inputParser.parse("12:00"))) {
                return 0;
            } else if ((comparison.equals(inputParser.parse("12:00")) || comparison.after(inputParser.parse("12:00"))) && comparison.before(inputParser.parse("17:00"))) {
                return 1;
            } else if ((comparison.equals(inputParser.parse("17:00")) || comparison.after(inputParser.parse("17:00"))) && comparison.before(inputParser.parse("23:59"))) {
                return 2;
            }
        } catch (ParseException e) {
            Log.e(TAG, "checkTimes: Failed to Pasrse time" + e.getMessage());
        }
        return -1;
    }

    private void getScheduledTimes(int timeSection) {
        Log.d(TAG, "getScheduledTimes: ENTERED + "+ timeSection);
        switch (timeSection) {
            case 0:
                timeComparizones = mDatabaseResourcesManager.getMorning();
                break;
            case 1:
                timeComparizones = mDatabaseResourcesManager.getAfternoon();
                break;
            case 2:
                timeComparizones = mDatabaseResourcesManager.getEvening();
                break;
            case 3:
                timeComparizones = mDatabaseResourcesManager.getNight();
                break;
            default:
                checkResult = false;
        }
        timeComparizones = mDatabaseResourcesManager.getMorning();
        Log.d(TAG, "getScheduledTimes: OUTPUT : " + checkResult);
    }


}
