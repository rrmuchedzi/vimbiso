package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class LoadAppointments {

    private static final String TAG = "LoadAppointments";
    ArrayList <AppointmentObject> mAppointmentObjects;

    public ArrayList<AppointmentObject> loadAppointments( ContentResolver contentResolver) {

        Log.d(TAG, "loadDoctorEntries: ENTER");
        mAppointmentObjects = new ArrayList();
        AppointmentObject newAppointment;

        int _ID = 0;
        String name = null;
        String doctorName = null;
        int doctorAvatar = 0;
        String date = null;
        String time = null;
        String reminderTime = null;
        boolean reminderStatus = false;
        String location = null;
        String notes = null;

        String[] projection = {
                SQL_AppointmentsContract.Columns._ID,
                SQL_AppointmentsContract.Columns.TITLE,
                SQL_AppointmentsContract.Columns.DOCTOR,
                SQL_AppointmentsContract.Columns.DOCTOR_AVATAR,
                SQL_AppointmentsContract.Columns.DATE,
                SQL_AppointmentsContract.Columns.TIME,
                SQL_AppointmentsContract.Columns.REMINDER_TIME,
                SQL_AppointmentsContract.Columns.REMINDER_STATUS,
                SQL_AppointmentsContract.Columns.LOCATION,
                SQL_AppointmentsContract.Columns.NOTES
        };

        Cursor cursor = contentResolver.query(SQL_AppointmentsContract.CONTENT_URI, projection, null, null, SQL_DoctorsContract.Columns._ID);

        if (cursor != null) {
            Log.d(TAG, "appointments: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    switch ( cursor.getColumnName(i) ) {
                        case SQL_AppointmentsContract.Columns._ID: {
                            _ID = Integer.parseInt(cursor.getString(i));
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.TITLE: {
                            name = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.DOCTOR: {
                            doctorName = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.DOCTOR_AVATAR: {
                            doctorAvatar = Byte.parseByte(cursor.getString(i));
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.DATE: {
                            date = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.TIME: {
                            time = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.REMINDER_TIME: {
                            reminderTime = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.REMINDER_STATUS: {
                            reminderStatus = Boolean.parseBoolean(cursor.getString(i).toLowerCase().trim());
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.LOCATION: {
                            location = cursor.getString(i);
                            break;
                        }
                        case SQL_AppointmentsContract.Columns.NOTES: {
                            notes = cursor.getString(i);
                            break;
                        }
                    }
                }

                if(name != null ) {
                    newAppointment = new AppointmentObject(_ID, name, doctorName, doctorAvatar, date, time, reminderTime, reminderStatus, location, notes );
                    mAppointmentObjects.add(newAppointment);
                    newAppointment.toString();
                }
                name = null;
            }
            cursor.close();
        } else {
            Log.d(TAG, "loadDoctorEntries: CURSO NULL");
        }

        return mAppointmentObjects;
    }

}
