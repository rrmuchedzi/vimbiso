package com.example.rrmuchedzi.vimbisomedicare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PillAndMedsReminderActivity extends AppCompatActivity {

    private static final String TAG = "PillAndMedsReminderActi";
    public static final String MEDICATION_OBJECTS = "MEDICATION_LIST";
    public static String TODAYSDATE;

    private final static Date mCurrentDate = Calendar.getInstance().getTime();

    private TextView mTodayDate;

    private TextView mMorningStatus;
    private TextView mAfternoonStatus;
    private TextView mEveningStatus;
    private TextView mNightStatus;

    private Button addMedication;
    private ConstraintLayout mMorningConstraint;
    private ConstraintLayout mAfternoonConstraint;
    private ConstraintLayout mEveningConstraint;
    private ConstraintLayout mNightConstraint;
    private ImageView mMorningArrow;
    private ImageView mAfternoonArrow;
    private ImageView mEveningArrow;
    private ImageView mNightArrow;

    private List<MedicationScheduled> mDayMedicationList;
    private List<MedicationScheduled> mCurrentDayMedication;

    private int morningCounter;
    private int afternoonCounter;
    private int eveningCounter;
    private int nightCounter;

    //Alarm Events Schedule
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    public static List<DuplicatedScheduled> mMorningEntries;
    public static List<DuplicatedScheduled> mAfternoonEntries;
    public static List<DuplicatedScheduled> mEveningEntries;
    public static List<DuplicatedScheduled> mNightEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_and_meds_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCurrentDayMedication = new ArrayList();
        getScheduledMedication();

        setupActivityElements();
        initializeActivityElements();

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupActivityElements() {
        mTodayDate = (TextView) findViewById(R.id.txt_pillPlannerViewDate);
        addMedication = (Button) findViewById(R.id.btn_addNewMedication);
        mMorningConstraint = (ConstraintLayout) findViewById(R.id.morning_constraintLayout);
        mAfternoonConstraint = (ConstraintLayout) findViewById(R.id.afternoon_constraintLayout);
        mEveningConstraint = (ConstraintLayout) findViewById(R.id.evening_constraintLayout);
        mNightConstraint = (ConstraintLayout) findViewById(R.id.night_constraintLayout);
        mMorningArrow = (ImageView) findViewById(R.id.img_morning_moreIndicator);
        mAfternoonArrow = (ImageView) findViewById(R.id.img_afternoon_moreIndicator);
        mEveningArrow = (ImageView) findViewById(R.id.img_evening_moreIndicator);
        mNightArrow = (ImageView) findViewById(R.id.img_night_moreIndicator);

        mMorningStatus = findViewById(R.id.txt_morning_medicationStatus);
        mAfternoonStatus = findViewById(R.id.txt_afternoon_medicationStatus);
        mEveningStatus = findViewById(R.id.txt_evening_medicationStatus);
        mNightStatus = findViewById(R.id.txt_night_medicationStatus);

        addMedication.setOnClickListener(mMedsViewsOnClickListener);
        mMorningConstraint.setOnClickListener(mMedsViewsOnClickListener);
        mAfternoonConstraint.setOnClickListener(mMedsViewsOnClickListener);
        mEveningConstraint.setOnClickListener(mMedsViewsOnClickListener);
        mNightConstraint.setOnClickListener(mMedsViewsOnClickListener);
        mMorningArrow.setOnClickListener(mMedsViewsOnClickListener);
        mAfternoonArrow.setOnClickListener(mMedsViewsOnClickListener);
        mEveningArrow.setOnClickListener(mMedsViewsOnClickListener);
        mNightArrow.setOnClickListener(mMedsViewsOnClickListener);
    }

    private void initializeActivityElements() {
        mTodayDate.setText("Today, " + (DateFormat.getDateInstance().format(Calendar.getInstance().getTime())));

        if( morningCounter > 0 ) {
            mMorningStatus.setText( morningCounter + " this morning" );
        }

        if( afternoonCounter > 0 ) {
            mAfternoonStatus.setText( afternoonCounter + " this afternoon" );
        }

        if( eveningCounter > 0 ) {
            mEveningStatus.setText( eveningCounter + " this evening" );
        }

        if( nightCounter > 0 ) {
            mNightStatus.setText( nightCounter + " tonight" );
        }
    }

    private void openPillsExternalActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
//        mDayEntriesObjects.putSerializable(MEDICATION_OBJECTS,(Serializable)mCurrentDayMedication);
//        intent.putExtra("BUNDLE",mDayEntriesObjects);
        startActivity(intent);
    }

    View.OnClickListener mMedsViewsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String message = "Nothing Selected";
            switch (view.getId()) {
                case R.id.btn_addNewMedication: {
                    openPillsExternalActivity(AddMedicationReminderActivity.class);
                    break;
                }
                case R.id.morning_constraintLayout: {
                    openPillsExternalActivity(DayViewPillsAndMedsActivity.class);
                    break;
                }
                case R.id.afternoon_constraintLayout: {
                    openPillsExternalActivity(DayViewPillsAndMedsActivity.class);
                    break;
                }
                case R.id.evening_constraintLayout: {
                    openPillsExternalActivity(DayViewPillsAndMedsActivity.class);
                    break;
                }
                case R.id.night_constraintLayout: {
                    openPillsExternalActivity(DayViewPillsAndMedsActivity.class);
                    break;
                }
                case R.id.img_morning_moreIndicator: {
                    message = "Morning Entries";
                    break;
                }
                case R.id.img_afternoon_moreIndicator: {
                    message = "Afternoon Entries";
                    break;
                }
                case R.id.img_evening_moreIndicator: {
                    message = "Evening Entries";
                    break;
                }
                case R.id.img_night_moreIndicator: {
                    message = "Night Entries";
                    break;
                }
                default:
                    return;
            }

            Toast.makeText(PillAndMedsReminderActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void getScheduledMedication() {
        DatabaseResourcesManager resourcesEntryManager =  new DatabaseResourcesManager(this);
        int [] occurrences = resourcesEntryManager.getScheduledEvents();
        morningCounter = occurrences[0];
        afternoonCounter = occurrences[1];
        eveningCounter = occurrences[2];
        nightCounter = occurrences[3];

        mMorningEntries = resourcesEntryManager.getMorning();
        mAfternoonEntries = resourcesEntryManager.getAfternoon();
        mEveningEntries = resourcesEntryManager.getEvening();
        mNightEntries = resourcesEntryManager.getNight();

        for (DuplicatedScheduled ele: mNightEntries){
            Log.d(TAG, "Pills Object :   "+ ele.getTime());
        }
    }

}
