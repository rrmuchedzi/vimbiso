package com.example.rrmuchedzi.vimbisomedicare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddAppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    final Context mContext = this;
    private DoctorObject mCurrDoctorObject;
    private static final String TAG = "AddAppointmentActivity";
    private CircleImageView mAvatar;
    private EditText mAppointmentName;
    private EditText mAppointmentLocation;
    private EditText mAppointmentNotes;

    private TextView mDatePicker;
    private TextView mTimePicker;
    private TextView mReminderPicker;

    private boolean reminderSet = false;
    private int reminderTimeSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        Intent intent = getIntent();

        if( intent != null ) {
            mCurrDoctorObject = (DoctorObject) intent.getSerializableExtra(DoctorViewerActivity.EDIT_DOCTOR_BUNDLE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        instantiateElements();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void instantiateElements() {
        mAvatar = findViewById(R.id.final_appointment_avatar);
        changeAvatarSelected((int)mCurrDoctorObject.getAvatar());

        mAppointmentName = findViewById(R.id.final_appointment_title);
        mAppointmentLocation = findViewById(R.id.final_appointment_location);
        mAppointmentNotes = findViewById(R.id.final_appointment_notes);

        mTimePicker = findViewById(R.id.final_appointment_time);
        mDatePicker = findViewById(R.id.final_appointment_date);
        mReminderPicker = findViewById(R.id.appoiintment_addNotification);

        setupDateAndTime ();

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        mReminderPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.dialog_scheduletime, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                alertDialogBuilder.setTitle("Select Doctor Avatar");
                alertDialogBuilder.setView(dialogView);


                final AlertDialog alertDialog = alertDialogBuilder.create();

                Button avatar_1 = dialogView.findViewById(R.id.schedule_option_1);
                Button avatar_2 = dialogView.findViewById(R.id.schedule_option_2);
                Button avatar_3 = dialogView.findViewById(R.id.schedule_option_3);

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.schedule_option_1: {
                                reminderSet = false;
                                reminderTimeSchedule = 0;
                                mReminderPicker.setText("No Reminder Set");
                                break;
                            }
                            case R.id.schedule_option_2: {
                                reminderSet = true;
                                reminderTimeSchedule = 10;
                                mReminderPicker.setText("Will remind you 10 minutes before");
                                break;
                            }
                            case R.id.schedule_option_3: {
                                reminderSet = true;
                                reminderTimeSchedule = 30;
                                mReminderPicker.setText("Will remind you 30 minutes before");
                                break;
                            }
                        }
                        alertDialog.dismiss();
                    }
                };

                avatar_1.setOnClickListener(clickListener);
                avatar_2.setOnClickListener(clickListener);
                avatar_3.setOnClickListener(clickListener);

                alertDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, dayOfMonth);
        mDatePicker.setText(DateFormat.getDateInstance().format(c.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
        Log.d(TAG, "onTimeSet: Time selected is " + hour + ":" + minutes);
        mTimePicker.setText(String.format("%02d:%02d", hour, minutes));
    }

    private void changeAvatarSelected(Integer avatarIdentification) {

        switch ( avatarIdentification ) {
            case 1 : {
                mAvatar.setImageResource(R.drawable.avatar_1);
                break;
            }
            case 2 : {
                mAvatar.setImageResource(R.drawable.avatar_2);
                break;
            }
            case 3 : {
                mAvatar.setImageResource(R.drawable.avatar_3);
                break;
            }
            case 4 : {
                mAvatar.setImageResource(R.drawable.avatar_4);
                break;
            }
            case 5 : {
                mAvatar.setImageResource(R.drawable.avatar_5);
                break;
            }
            case 6 : {
                mAvatar.setImageResource(R.drawable.avatar_6);
                break;
            }
            case 7 : {
                mAvatar.setImageResource(R.drawable.avatar_7);
                break;
            }
            case 8 : {
                mAvatar.setImageResource(R.drawable.avatar_8);
                break;
            }
            default:
                mAvatar.setImageResource(R.drawable.profile_avatar_placeholder);
        }
    }

    private void setupDateAndTime () {

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        mDatePicker.setText(DateFormat.getDateInstance().format(c.getTime()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String result = sdf.format(c.getInstance().getTime());

        mTimePicker.setText(result);
        mReminderPicker.setText("No Reminder Set");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_doctor) {
          if( saveNewAppointment() ){
              Toast.makeText(mContext, "Appointment Added", Toast.LENGTH_SHORT).show();
              HomeActivity.triggerChangesUpdates();
              onBackPressed();
              return true;
          } else {
              Toast.makeText(mContext, "Details missing", Toast.LENGTH_SHORT).show();
          }
        }
        return false;
    }

    private boolean saveNewAppointment() {

        ContentResolver contentResolver;
        ContentValues contentValues;
        String appointmentName = mAppointmentName.getText().toString();
        boolean result = false;

        if (appointmentName.length() > 0) {
            try {
                contentResolver = getContentResolver();
                contentValues = new ContentValues();

                contentValues.put(SQL_AppointmentsContract.Columns.TITLE, appointmentName);
                contentValues.put(SQL_AppointmentsContract.Columns.DOCTOR, mCurrDoctorObject.getName());
                contentValues.put(SQL_AppointmentsContract.Columns.DOCTOR_AVATAR, mCurrDoctorObject.getAvatar());
                contentValues.put(SQL_AppointmentsContract.Columns.DATE, mDatePicker.getText().toString());
                contentValues.put(SQL_AppointmentsContract.Columns.TIME, mTimePicker.getText().toString());
                contentValues.put(SQL_AppointmentsContract.Columns.REMINDER_TIME, reminderTimeSchedule);
                contentValues.put(SQL_AppointmentsContract.Columns.REMINDER_STATUS, reminderSet);
                contentValues.put(SQL_AppointmentsContract.Columns.LOCATION, mAppointmentLocation.getText().toString());
                contentValues.put(SQL_AppointmentsContract.Columns.NOTES, mAppointmentNotes.getText().toString());

                Uri resultant = contentResolver.insert(SQL_AppointmentsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "appointment: returned uri : " + resultant);
                result = !result;

            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
        }

        return result;
    }
}

/*
*        public static final String _ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DOCTOR = "doctor_name";
        public static final String DOCTOR_AVATAR = "doctor_avatar";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String REMINDER_TIME = "scheduled_time";
        public static final String REMINDER_STATUS = "reminder";
        public static final String LOCATION = "location";
        public static final String NOTES = "notes";
        public static final String PUBLISHED_DATE = "date";*/
