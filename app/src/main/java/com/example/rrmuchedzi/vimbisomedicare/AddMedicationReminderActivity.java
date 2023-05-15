package com.example.rrmuchedzi.vimbisomedicare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import static com.example.rrmuchedzi.vimbisomedicare.R.id.form_dates_selector_container;

public class AddMedicationReminderActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddMedicationReminderAc";

    //First Page Controls. Total of 6
    private EditText mMedicationName;
    private EditText mMedicationQuantity;
    private Button mMedicationStartDate;
    private Spinner mMedicationTypeUnits;
    private RadioButton mMedicationRadioEveryDay;
    private RadioButton mMedicationRadioCertainDays;

    //Second Page Controls
    private CheckBox mCheckBoxMonday;
    private CheckBox mCheckBoxTuesday;
    private CheckBox mCheckBoxWednesday;
    private CheckBox mCheckBoxThursday;
    private CheckBox mCheckBoxFriday;
    private CheckBox mCheckBoxSaturday;
    private CheckBox mCheckBoxSunday;

    private EditText mMedicationMaximumDuration;
    private Spinner mMedicationDuration;
    private RadioGroup mRadioGroupFrequencySection;

    //Third Page Controls
    private Spinner mMedicationTimes;
    private LinearLayout mTimeSecondSectionContainer;
    private LinearLayout mDatesSecondSectionContainer;

    private Button mTime_1_selected;
    private Button mTime_2_selected;
    private Button mTime_3_selected;
    private Button mTime_4_selected;
    private Button mTime_5_selected;
    private Button mTime_6_selected;
    private Button mTime_7_selected;
    private Button mTime_8_selected;

    private EditText mMedicationNotes;

    private List<String> mSpinnerEntries;
    private List<String> mSpinnerDurationFrequency;
    private List<String> mSpinnerMedicationTimes;

    private Button[] timeButtons;

    private int timePickerClickedIndex = 0;
    private boolean scheduleForEveryday = true;

    private AlarmEvent mNewAlarmEvent; //Todo, Implement and set Alarm

    DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_medication_toolbar);
        setSupportActionBar(toolbar);
        setupPageUIElements();
        intialELementsSetup();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_doctor) {
            boolean result = addMedicationToDatabase();
            Snackbar snackbar;

            //Check if event was successful
            if (result) {
                Toast.makeText(this, "Medication saved successfully", Toast.LENGTH_LONG).show();
                return onSupportNavigateUp();
            }

            return result;

        } else {
            return onSupportNavigateUp();
        }
    }

    private void setupPageUIElements() {
        Log.d(TAG, "setupPageUIElements: starts");
        mMedicationName = findViewById(R.id.form_medication_name);
        mMedicationQuantity = findViewById(R.id.form_medication_quantity);
        mMedicationStartDate = findViewById(R.id.form_medication_start_date);
        mMedicationTypeUnits = findViewById(R.id.form_medication_spinner);
        mMedicationRadioEveryDay = findViewById(R.id.form_medication_schedule_everyday);
        mMedicationRadioCertainDays = findViewById(R.id.form_medication_schedule_certain_days);

        //SecondPageControls Instatiating
        mCheckBoxMonday = findViewById(R.id.form_day_monday);
        mCheckBoxTuesday = findViewById(R.id.form_day_tuesday);
        mCheckBoxWednesday = findViewById(R.id.form_day_wednesday);
        mCheckBoxThursday = findViewById(R.id.form_day_thursday);
        mCheckBoxFriday = findViewById(R.id.form_day_friday);
        mCheckBoxSaturday = findViewById(R.id.form_day_saturday);
        mCheckBoxSunday = findViewById(R.id.form_day_sunday);

        mMedicationMaximumDuration = findViewById(R.id.form_medication_duration_quantity);
        mMedicationDuration = findViewById(R.id.form_medication_spinner_duration_specification);

        mTime_1_selected = findViewById(R.id.form_time_1_schedule_selector);
        mTime_2_selected = findViewById(R.id.form_time_2_schedule_selector);
        mTime_3_selected = findViewById(R.id.form_time_3_schedule_selector);
        mTime_4_selected = findViewById(R.id.form_time_4_schedule_selector);
        mTime_5_selected = findViewById(R.id.form_time_5_schedule_selector);
        mTime_6_selected = findViewById(R.id.form_time_6_schedule_selector);
        mTime_7_selected = findViewById(R.id.form_time_7_schedule_selector);
        mTime_8_selected = findViewById(R.id.form_time_8_schedule_selector);

        mDatesSecondSectionContainer = findViewById(form_dates_selector_container);
        mTimeSecondSectionContainer = findViewById(R.id.form_times_container_below);

        mMedicationNotes = findViewById(R.id.form_medication_notes);
        mRadioGroupFrequencySection = findViewById(R.id.form_frequency_radio_group);
        mMedicationTimes = findViewById(R.id.form_medication_time_frequency_selection);

        timeButtons = new Button[]{
                mTime_1_selected,
                mTime_2_selected,
                mTime_3_selected,
                mTime_4_selected,
                mTime_5_selected,
                mTime_6_selected,
                mTime_7_selected,
                mTime_8_selected
        };

        for (Button element : timeButtons) { // Add time text to each button.
            element.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
        }

        setupFormSpinnerOptions();
        Log.d(TAG, "setupPageUIElements: ends");
    }

    private void setupFormSpinnerOptions() {
        mSpinnerDurationFrequency = Arrays.asList(getResources().getStringArray(R.array.form_medication_duration));
        mSpinnerMedicationTimes = Arrays.asList(getResources().getStringArray(R.array.form_medication_time_frequency_entries));
        mSpinnerEntries = Arrays.asList(getResources().getStringArray(R.array.form_medication_spinner_entries));

        ArrayAdapter spinerQuantitiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerEntries);
        ArrayAdapter spinerDurationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerDurationFrequency);
        ArrayAdapter spinerMedicationTimes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerMedicationTimes);

        mMedicationTypeUnits.setAdapter(spinerQuantitiesAdapter);
        mMedicationTimes.setAdapter(spinerMedicationTimes);
        mMedicationDuration.setAdapter(spinerDurationAdapter);

        mMedicationTimes.setOnItemSelectedListener(this);
        //TODO, Implement the units selected onItemSelectedListener, might have to implement onItemSelectedListener
    }

    private void intialELementsSetup() {
        //Set Startind Date
        Calendar cal = Calendar.getInstance();
        String date = mDateFormat.format(cal.getTime());
        mMedicationStartDate.setText(date);

        mMedicationRadioEveryDay.setChecked(true);
        mDatesSecondSectionContainer.setVisibility(View.GONE);
        mTimeSecondSectionContainer.setVisibility(View.GONE);

        for (int index = 0; index < 8; index++) {

            timeButtons[index].setOnClickListener(mViewsClickListener);

            if (index != 0) {
                timeButtons[index].setVisibility(View.INVISIBLE);
            }

        }

        mMedicationStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        mMedicationRadioEveryDay.setOnClickListener(mViewsClickListener);
        mMedicationRadioCertainDays.setOnClickListener(mViewsClickListener);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, dayOfMonth);
        mMedicationStartDate.setText( mDateFormat.format( c.getTime()) );
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
        Log.d(TAG, "onTimeSet: Time selected is " + hour + ":" + minutes);
        timeButtons[timePickerClickedIndex].setText(String.format("%02d:%02d", hour, minutes));
    }

    View.OnClickListener mViewsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean timePickerSelected = true;

            switch (view.getId()) {

                case R.id.form_time_1_schedule_selector: {
                    timePickerClickedIndex = 0;
                    break;
                }
                case R.id.form_time_2_schedule_selector: {
                    timePickerClickedIndex = 1;
                    break;
                }
                case R.id.form_time_3_schedule_selector: {
                    timePickerClickedIndex = 2;
                    break;
                }
                case R.id.form_time_4_schedule_selector: {
                    timePickerClickedIndex = 3;
                    break;
                }
                case R.id.form_time_5_schedule_selector: {
                    timePickerClickedIndex = 4;
                    break;
                }
                case R.id.form_time_6_schedule_selector: {
                    timePickerClickedIndex = 5;
                    break;
                }
                case R.id.form_time_7_schedule_selector: {
                    timePickerClickedIndex = 6;
                    break;
                }
                case R.id.form_time_8_schedule_selector: {
                    timePickerClickedIndex = 7;
                    break;
                }
                case R.id.form_medication_schedule_everyday: {
                    mDatesSecondSectionContainer.setVisibility(View.GONE);
                    scheduleForEveryday = true;
                    timePickerSelected = !timePickerSelected;
                    break;
                }
                case R.id.form_medication_schedule_certain_days: {
                    mDatesSecondSectionContainer.setVisibility(View.VISIBLE);
                    scheduleForEveryday = false;
                    timePickerSelected = !timePickerSelected;
                    break;
                }
                default:
                    timePickerSelected = !timePickerSelected;
            }

            if (timePickerSelected) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String item = adapterView.getItemAtPosition(position).toString();
        int index = 0;
        for (index = 0; index < 8; index++) {
            timeButtons[index].setVisibility(View.VISIBLE);

            if (index > position) {
                timeButtons[index].setVisibility(View.INVISIBLE);
            }
        }

        if (position + 1 > 4) {
            mTimeSecondSectionContainer.setVisibility(View.VISIBLE);
        } else {
            mTimeSecondSectionContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean addMedicationToDatabase() {
        boolean operationStatus = true;

        String line = mMedicationName.getText().toString();

        if ((line != null) && (line.trim().length() > 0)) {

            if (mMedicationRadioCertainDays.isChecked()) {

                if (mCheckBoxMonday.isChecked() || mCheckBoxTuesday.isChecked() || mCheckBoxWednesday.isChecked() || mCheckBoxThursday.isChecked() || mCheckBoxFriday.isChecked() || mCheckBoxSaturday.isChecked() || mCheckBoxSunday.isChecked()) {

                } else {
                    operationStatus = false;
                    showSnackBarUpdates("Please select atleast 1 day for medication");
                }

            }

        } else {
            operationStatus = false;
            showSnackBarUpdates("Please enter medication name");
        }

        if (operationStatus) {
            //perform database operation if all entries are valid
            Log.d(TAG, "addMedicationToDatabase: QUERRY STARTED ========================================");
            operationStatus = queryDatabase(line);
        } else {
            Log.d(TAG, "addMedicationToDatabase: query not started missing infor");
        }

        return operationStatus;
    }

    private boolean queryDatabase(String title) {

        boolean operationStatus = true;
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        String message = "Saved Successfully";
        //MedicationDays medicationDays;
        int [] medicationDays = new int[7];

        String startDate = mMedicationStartDate.getText().toString();
        int duration = Integer.parseInt(mMedicationMaximumDuration.getText().toString());
        String durationUnits = mMedicationDuration.getSelectedItem().toString();
        String quantityUnits = mMedicationTypeUnits.getSelectedItem().toString();
        HashMap<String, String> times = new HashMap();
        MedicationTimes setTimesRef;
        Gson gson = new Gson();
        int totalTimes = mMedicationTimes.getSelectedItemPosition() + 1;

        //We check for Frequency
        if (mMedicationRadioCertainDays.isChecked()) {
            int mon, tue, wed, thu, fri, sat, sun;
            mon = (mCheckBoxMonday.isChecked()) ? 1 : 0;
            tue = (mCheckBoxTuesday.isChecked()) ? 1 : 0;
            wed = (mCheckBoxWednesday.isChecked()) ? 1 : 0;
            thu = (mCheckBoxThursday.isChecked()) ? 1 : 0;
            fri = (mCheckBoxFriday.isChecked()) ? 1 : 0;
            sat = (mCheckBoxSaturday.isChecked()) ? 1 : 0;
            sun = (mCheckBoxSunday.isChecked()) ? 1 : 0;
            medicationDays[0] = mon;
            medicationDays[1] = tue;
            medicationDays[2] = wed;
            medicationDays[3] = thu;
            medicationDays[4] = fri;
            medicationDays[5] = sat;
            medicationDays[6] = sun;
        } else {
            medicationDays[0] = 1;
            medicationDays[1] = 1;
            medicationDays[2] = 1;
            medicationDays[3] = 1;
            medicationDays[4] = 1;
            medicationDays[5] = 1;
            medicationDays[6] = 1;
        }

        Log.d(TAG, "Total Times Selected : " + totalTimes);
        String t;
        for (int z = 0; z < totalTimes; z++) {
            t = timeButtons[z].getText().toString();
            times.put( t , checkTimes(t) );
            Log.d(TAG, "Time Entry : " + timeButtons[z].getText().toString());
        }

        setTimesRef = new MedicationTimes(times, totalTimes);
        String endDate = calculateEndDate(startDate, duration, ((durationUnits.toUpperCase().trim().equals("DAYS")) ? true : false));

        contentValues.put(SQL_MedicationContract.Columns.NAME, title);
        contentValues.put(SQL_MedicationContract.Columns.QUANTITY, Integer.parseInt(mMedicationQuantity.getText().toString()));
        contentValues.put(SQL_MedicationContract.Columns.QUANTITY_UNITS, quantityUnits);
        contentValues.put(SQL_MedicationContract.Columns.START_DATE, startDate);
        contentValues.put(SQL_MedicationContract.Columns.DURATION, duration);
        contentValues.put(SQL_MedicationContract.Columns.DURATION_UNITS, durationUnits);
        contentValues.put(SQL_MedicationContract.Columns.END_DATE, endDate);
        contentValues.put(SQL_MedicationContract.Columns.NOTES, mMedicationNotes.getText().toString());
        contentValues.put(SQL_MedicationContract.Columns.MONDAY, medicationDays[0]);
        contentValues.put(SQL_MedicationContract.Columns.TUESDAY, medicationDays[1]);
        contentValues.put(SQL_MedicationContract.Columns.WEDNESDAY, medicationDays[2]);
        contentValues.put(SQL_MedicationContract.Columns.THURSDAY, medicationDays[3]);
        contentValues.put(SQL_MedicationContract.Columns.FRIDAY, medicationDays[4]);
        contentValues.put(SQL_MedicationContract.Columns.SATURDAY, medicationDays[5]);
        contentValues.put(SQL_MedicationContract.Columns.SUNDAY, medicationDays[6]);

        if (quantityUnits.toUpperCase().trim().equals("PILLS")) {
            contentValues.put(SQL_MedicationContract.Columns.PILL_ID, 1);
        } else if (quantityUnits.toUpperCase().trim().equals("ML")) {
            contentValues.put(SQL_MedicationContract.Columns.PILL_ID, 2);
        } else {
            contentValues.put(SQL_MedicationContract.Columns.PILL_ID, 3);
        }

        //contentValues.put(SQL_MedicationContract.Columns.DAYS, gson.toJson(medicationDays).getBytes());
        contentValues.put(SQL_MedicationContract.Columns.TIMES, gson.toJson(setTimesRef).getBytes());
        contentValues.put(SQL_MedicationContract.Columns.ACTIVE, 1);

        Uri uri = contentResolver.insert(SQL_MedicationContract.CONTENT_URI, contentValues);
        Log.d(TAG, "Medication Table Insert: uri : " + uri);
        return operationStatus;
    }

    private String calculateEndDate(String startDate, int duration, boolean durationType) {
        try {

//            DateFormat mDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Log.d(TAG, "Set Date: starting Date : " + startDate);
            Date date = mDateFormat.parse(startDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            Log.d(TAG, "MIDDLE DATE : " + DateFormat.getDateInstance().format(c.getTime()));
            if (durationType) { //For Days
                c.add(Calendar.DATE, duration); // Adding 5 days
            } else { //For Months
                c.add(Calendar.MONTH, duration);
            }

            String outputDate = mDateFormat.format(c.getTime());
            Log.d(TAG, "calculated End Date output " + outputDate);

            return outputDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String checkTimes(String entry) {

        String dayStatus = "Morning";
        SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm");
        Date comparison;

        if (entry != null && entry.trim().length() > 0) {
            Log.d(TAG, "Adding Event: TIME TESTED IS : " + entry);
            try {
                comparison = inputParser.parse(entry);

                if ((comparison.equals(inputParser.parse("00:00")) || comparison.after(inputParser.parse("00:00"))) && comparison.before(inputParser.parse("05:00"))) {
                   dayStatus = TimeMapping.NIGHT_TIME;
                } else if ((comparison.equals(inputParser.parse("05:00")) || comparison.after(inputParser.parse("05:00"))) && comparison.before(inputParser.parse("12:00"))) {
                    dayStatus = TimeMapping.MORNING_TIME;
                } else if ((comparison.equals(inputParser.parse("12:00")) || comparison.after(inputParser.parse("12:00"))) && comparison.before(inputParser.parse("17:00"))) {
                    dayStatus = TimeMapping.AFTERNOON_TIME;
                } else if ((comparison.equals(inputParser.parse("17:00")) || comparison.after(inputParser.parse("17:00"))) && comparison.before(inputParser.parse("23:59"))) {
                    dayStatus = TimeMapping.EVENING_TIME;
                }
            } catch (ParseException e) {
                Log.e(TAG, "checkTimes: Failed to Pasrse time" + e.getMessage());
            }
        }
        return dayStatus;
    }

    private void showSnackBarUpdates(String message) {
        Snackbar snackbar = Snackbar.make(mMedicationName, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
