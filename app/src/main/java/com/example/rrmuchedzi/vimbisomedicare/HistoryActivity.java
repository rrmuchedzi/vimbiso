package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private ListView mHistoryListView;
    private TextView mMessageTextView;
    private HistoryListViewAdapter historyListViewAdapter;

    ArrayList<HistoryObject> mMedicalHistoryContainer;
    private static final String TAG = "HistoryActivity";

    DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryListView = findViewById(R.id.history_list_view);
        mMessageTextView = findViewById(R.id.history_no_entry_message);

        mHistoryListView.setEmptyView(mMessageTextView);
        dataTransfer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void dataTransfer() {
        loadHistoryEntries();
        historyListViewAdapter = new HistoryListViewAdapter(this, R.layout.browse_medication_history, mMedicalHistoryContainer);
        mHistoryListView.setAdapter(historyListViewAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadHistoryEntries() {

        mMedicalHistoryContainer = new ArrayList();
        ContentResolver contentResolver = getContentResolver();
        DoctorObject doctorObject;

        Integer _ID = null;
        int medication_ID = 0;
        int medication_image_id = 1;
        String medication_name = null;
        Date medication_date = null;
        Date medication_time = null;
        String medication_dosage = null;
        String medication_dosage_quantity = null;

        String[] projection = {
                SQL_RecordsContract.Columns._ID,
                SQL_RecordsContract.Columns.MEDICATION_NAME,
                SQL_RecordsContract.Columns.MEDICATION_ID,
                SQL_RecordsContract.Columns.AVATAR_ID,
                SQL_RecordsContract.Columns.DOSAGE_TAKEN,
                SQL_RecordsContract.Columns.DATE_TAKE,
                SQL_RecordsContract.Columns.TIME_TAKE,
        };

        Cursor cursor = contentResolver.query(SQL_RecordsContract.CONTENT_URI, projection, null, null, null);

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch ( cursor.getColumnName(i) ) {
                            case SQL_RecordsContract.Columns._ID: {
                                _ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_RecordsContract.Columns.MEDICATION_ID: {
                                medication_ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_RecordsContract.Columns.AVATAR_ID: {
                                medication_image_id = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_RecordsContract.Columns.MEDICATION_NAME: {
                                medication_name = cursor.getString(i);
                                break;
                            }
                            case SQL_RecordsContract.Columns.DATE_TAKE: {
                                medication_date = mDateFormat.parse( cursor.getString(i) );
                                break;
                            }
                            case SQL_RecordsContract.Columns.TIME_TAKE: {
                                medication_time = mTimeFormat.parse(cursor.getString(i));
                                break;
                            }
                            case SQL_RecordsContract.Columns.DOSAGE_TAKEN: {
                                medication_dosage = cursor.getString(i);
                                break;
                            }
                        }
                    } catch ( ParseException e) {
                        Log.e(TAG, "loadHistoryEntries: " + e.getMessage() );
                    }
                }

                if( _ID != null ) {
                    HistoryObject historyObject = new HistoryObject(medication_ID, medication_image_id, medication_name, medication_date, medication_time, medication_dosage, medication_dosage_quantity);
                    historyObject.set_ID( _ID );
                    mMedicalHistoryContainer.add(historyObject);
                }
                _ID = null;
            }
            cursor.close();
        } else {
            Log.d(TAG, "loadDoctorEntries: CURSOR NULL");
        }
    }
}
