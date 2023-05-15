package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DoctorsActivity extends AppCompatActivity {

    private static final String TAG = "DoctorsActivity";

    private ListView mDoctorsListView;
    private TextView mMessageTextView;
    private DoctorsListViewAdapter doctorsListViewAdapter;

    public static final String DOCTOR_OBJECT = "DOCTOR_OBJECT";

    ArrayList<DoctorObject> mDoctorsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.doctor_toolbar);
        setSupportActionBar(toolbar);
        mDoctorsListView = findViewById(R.id.doctors_list_view);
        mMessageTextView = findViewById(R.id.doctors_no_entry_message);

        dataTransfer();
        mDoctorsListView.setEmptyView(mMessageTextView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.doctors_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddDoctorActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDoctorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewDoctorDetails(i);
            }
        });
    }

    private void dataTransfer() {
        loadDoctorEntries();
        doctorsListViewAdapter = new DoctorsListViewAdapter(this, R.layout.browse_doctor_entry, mDoctorsList);
        mDoctorsListView.setAdapter(doctorsListViewAdapter);
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume: started");
        dataTransfer();
        super.onResume();
        Log.d(TAG, "onResume: ended");
    }

    private void viewDoctorDetails( int position ) {
        Intent intent = new Intent(this , DoctorViewerActivity.class);
        intent.putExtra(DOCTOR_OBJECT, mDoctorsList.get(position));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadDoctorEntries() {
        Log.d(TAG, "loadDoctorEntries: ENTER");
        mDoctorsList = new ArrayList();
        ContentResolver contentResolver = getContentResolver();
        DoctorObject doctorObject;

        int doctorID = 0;
        String doctorName = null;
        byte doctorAvatar = 1;
        String doctorSpeciality = null;
        String doctorFirstPhone = null;
        String doctorFirstPhone_Type = null;
        String doctorSecondPhone = null;
        String doctorSecondPhone_Type = null;
        String doctorEmail = null;
        String doctorAddress = null;
        String doctorHospital = null;
        String doctorNotes = null;

        String[] projection = {
                SQL_DoctorsContract.Columns._ID,
                SQL_DoctorsContract.Columns.NAME,
                SQL_DoctorsContract.Columns.AVATAR_ID,
                SQL_DoctorsContract.Columns.SPECIALITY,
                SQL_DoctorsContract.Columns.FIRST_NUMBER,
                SQL_DoctorsContract.Columns.FIRST_NUMBER_TYPE,
                SQL_DoctorsContract.Columns.SECOND_NUMBER,
                SQL_DoctorsContract.Columns.SECOND_NUMBER_TYPE,
                SQL_DoctorsContract.Columns.EMAIL,
                SQL_DoctorsContract.Columns.ADDRESS,
                SQL_DoctorsContract.Columns.HOSPITAL,
                SQL_DoctorsContract.Columns.NOTES_DOCTOR,
        };

        Cursor cursor = contentResolver.query(SQL_DoctorsContract.CONTENT_URI, projection, null, null, SQL_DoctorsContract.Columns._ID);

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    switch ( cursor.getColumnName(i) ) {
                        case SQL_DoctorsContract.Columns._ID: {
                            doctorID = Integer.parseInt(cursor.getString(i));
                            break;
                        }
                        case SQL_DoctorsContract.Columns.NAME: {
                            doctorName = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.AVATAR_ID: {
                            doctorAvatar = Byte.parseByte(cursor.getString(i));
                            break;
                        }
                        case SQL_DoctorsContract.Columns.SPECIALITY: {
                            doctorSpeciality = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.FIRST_NUMBER: {
                            doctorFirstPhone = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.FIRST_NUMBER_TYPE: {
                            doctorFirstPhone_Type = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.SECOND_NUMBER: {
                            doctorSecondPhone = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.SECOND_NUMBER_TYPE: {
                            doctorSecondPhone_Type = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.EMAIL: {
                            doctorEmail = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.ADDRESS: {
                            doctorAddress = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.HOSPITAL: {
                            doctorHospital = cursor.getString(i);
                            break;
                        }
                        case SQL_DoctorsContract.Columns.NOTES_DOCTOR: {
                            doctorNotes = cursor.getString(i);
                            break;
                        }
                    }
                }

                if( doctorName != null ) {
                    doctorObject = new DoctorObject(doctorID, doctorName, doctorAvatar, doctorSpeciality, doctorFirstPhone, doctorFirstPhone_Type, doctorSecondPhone, doctorSecondPhone_Type, doctorEmail, doctorAddress, doctorHospital, doctorNotes );
                    mDoctorsList.add(doctorObject);
                    doctorObject.toString();
                }
                doctorName = null;
            }
            cursor.close();
        } else {
            Log.d(TAG, "loadDoctorEntries: CURSO NULL");
        }
    }

    private void startAddDoctorActivity() {
        Intent intent = new Intent(this, AddDoctorActivity.class);
        startActivity(intent);
    }

}
