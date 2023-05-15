package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {

    private static final String TAG = "AddDoctorActivity";

    enum CURRENTMODE {SAVEMODE, EDITMODE}

    ; //Todo, implement computation for edit mode and save mode

    final Context mContext = this;
    private ImageView mDoctorAvatar;
    private TextView mDoctorName;
    private TextView mDoctorSpeciality;
    private TextView mDoctorFirstPhoneNumber;
    private TextView mDoctorSecondPhoneNumber;
    private TextView mAddNewNumber;
    private TextView mDoctorEmail;
    private TextView mDoctorAddress;
    private TextView mDoctorHospitalName;
    private TextView mDoctorNotes;
    private Spinner mMobileDescriptionOne;
    private Spinner mMobileDescriptionTwo;

    private List<String> mSpinnerMobileTypes;

    private int mSelectedAvatar = 1;
    private boolean mEditOrAddMode = false;
    private DoctorObject mCurrDoctorObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_doctor_toolbar);
        setSupportActionBar(toolbar);
        instantiateFormInputControls();

        Intent intent = getIntent();

        if( intent != null ) {
            Log.d(TAG, "onCreate: Intent Body Check");
            mCurrDoctorObject = (DoctorObject) intent.getSerializableExtra(DoctorViewerActivity.EDIT_DOCTOR_BUNDLE);
            if( mCurrDoctorObject != null ) {
                mEditOrAddMode = true;
                prepareDoctorEntryEditing();
            }
            Log.d(TAG, "EDITING MODE : " + mEditOrAddMode );
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void instantiateFormInputControls() {

        mDoctorAvatar = findViewById(R.id.form_doctor_entry_change_avatar);
        mDoctorName = findViewById(R.id.form_doctor_entry_name);
        mDoctorSpeciality = findViewById(R.id.form_doctor_entry_speciality);
        mDoctorFirstPhoneNumber = findViewById(R.id.form_doctor_entry_phone_number_one);
        mDoctorSecondPhoneNumber = findViewById(R.id.form_doctor_entry_phone_number_two);
        mAddNewNumber = findViewById(R.id.form_doctor_entry_add_extra_number);
        mDoctorEmail = findViewById(R.id.form_doctor_entry_email);
        mDoctorAddress = findViewById(R.id.form_doctor_entry_address);
        mDoctorHospitalName = findViewById(R.id.form_doctor_entry_hospital_name);
        mDoctorNotes = findViewById(R.id.form_doctor_entry_notes);

        mMobileDescriptionOne = findViewById(R.id.form_doctor_entry_spiner_number_one);
        mMobileDescriptionTwo = findViewById(R.id.form_doctor_entry_spinner_number_two);

        mSpinnerMobileTypes = Arrays.asList(getResources().getStringArray(R.array.form_doctor_entry_phone_types));
        ArrayAdapter arrayAdapterSpinners = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerMobileTypes);
        mMobileDescriptionOne.setAdapter(arrayAdapterSpinners);
        mMobileDescriptionTwo.setAdapter(arrayAdapterSpinners);

        mAddNewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDoctorSecondPhoneNumber.setVisibility(View.VISIBLE);
                mMobileDescriptionTwo.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            }
        });

        mDoctorAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.dialog_doctor_avatar, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                alertDialogBuilder.setTitle("Select Doctor Avatar");
                alertDialogBuilder.setView(dialogView);


                final AlertDialog alertDialog = alertDialogBuilder.create();

                ImageButton avatar_1 = dialogView.findViewById(R.id.avatar_1);
                ImageButton avatar_2 = dialogView.findViewById(R.id.avatar_2);
                ImageButton avatar_3 = dialogView.findViewById(R.id.avatar_3);
                ImageButton avatar_4 = dialogView.findViewById(R.id.avatar_4);
                ImageButton avatar_5 = dialogView.findViewById(R.id.avatar_5);
                ImageButton avatar_6 = dialogView.findViewById(R.id.avatar_6);
                ImageButton avatar_7 = dialogView.findViewById(R.id.avatar_7);
                ImageButton avatar_8 = dialogView.findViewById(R.id.avatar_8);

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int clickValue = mSelectedAvatar;

                        switch (view.getId()) {
                            case R.id.avatar_1: {
                                clickValue = 1;
                                break;
                            }
                            case R.id.avatar_2: {
                                clickValue = 2;
                                break;
                            }
                            case R.id.avatar_3: {
                                clickValue = 3;
                                break;
                            }
                            case R.id.avatar_4: {
                                clickValue = 4;
                                break;
                            }
                            case R.id.avatar_5: {
                                clickValue = 5;
                                break;
                            }
                            case R.id.avatar_6: {
                                clickValue = 6;
                                break;
                            }
                            case R.id.avatar_7: {
                                clickValue = 7;
                                break;
                            }
                            case R.id.avatar_8: {
                                clickValue = 8;
                                break;
                            }
                        }
                        changeAvatarSelected(clickValue);
                        alertDialog.dismiss();
                    }
                };

                avatar_1.setOnClickListener(clickListener);
                avatar_2.setOnClickListener(clickListener);
                avatar_3.setOnClickListener(clickListener);
                avatar_4.setOnClickListener(clickListener);
                avatar_5.setOnClickListener(clickListener);
                avatar_6.setOnClickListener(clickListener);
                avatar_7.setOnClickListener(clickListener);
                avatar_8.setOnClickListener(clickListener);
                // show it
                alertDialog.show();
            }
        });
    }

    private void prepareDoctorEntryEditing() {
        Log.d(TAG, "prepareDoctorEntryEditing: Entered EDITING MODE Preparation");
        mSelectedAvatar = mCurrDoctorObject.getAvatar();
        changeAvatarSelected((int)mSelectedAvatar);
        mDoctorName.setText(mCurrDoctorObject.getName());
        mDoctorSpeciality.setText(mCurrDoctorObject.getSpeciality());

        String line = mCurrDoctorObject.getFirstNumber();
        if( null != line ) {
            mDoctorFirstPhoneNumber.setText(line);
            line = mCurrDoctorObject.getFirstNumber_type();
            if( line.equalsIgnoreCase("Mobile")) {
                mMobileDescriptionOne.setSelection(0);
            } else {
                mMobileDescriptionOne.setSelection(1);
            }
        }

        line = mCurrDoctorObject.getSecondNumber();
        if( null != line ) {
            mDoctorSecondPhoneNumber.setText(line);
            line = mCurrDoctorObject.getSecondNumber_type();
            if( line.equalsIgnoreCase("Mobile")) {
                mMobileDescriptionTwo.setSelection(0);
            }else {
                mMobileDescriptionTwo.setSelection(1);
            }
        }

        mDoctorSecondPhoneNumber.setVisibility(View.VISIBLE);
        mMobileDescriptionTwo.setVisibility(View.VISIBLE);

        mAddNewNumber.setVisibility(View.GONE);
        mDoctorEmail.setText( mCurrDoctorObject.getEmail() );
        mDoctorAddress.setText( mCurrDoctorObject.getAddress() );
        mDoctorHospitalName.setText( mCurrDoctorObject.getHospitalName() );
        mDoctorNotes.setText( mCurrDoctorObject.getNotes() );
        Log.d(TAG, "prepareDoctorEntryEditing: ======================================");
    }

    private void changeAvatarSelected(Integer avatarIdentification) {
        switch (avatarIdentification) {
            case 1: {
                mSelectedAvatar = 1;
                mDoctorAvatar.setImageResource(R.drawable.avatar_1);
                break;
            }
            case 2: {
                mSelectedAvatar = 2;
                mDoctorAvatar.setImageResource(R.drawable.avatar_2);
                break;
            }
            case 3: {
                mSelectedAvatar = 3;
                mDoctorAvatar.setImageResource(R.drawable.avatar_3);
                break;
            }
            case 4: {
                mSelectedAvatar = 4;
                mDoctorAvatar.setImageResource(R.drawable.avatar_4);
                break;
            }
            case 5: {
                mSelectedAvatar = 5;
                mDoctorAvatar.setImageResource(R.drawable.avatar_5);
                break;
            }
            case 6: {
                mSelectedAvatar = 6;
                mDoctorAvatar.setImageResource(R.drawable.avatar_6);
                break;
            }
            case 7: {
                mSelectedAvatar = 7;
                mDoctorAvatar.setImageResource(R.drawable.avatar_7);
                break;
            }
            case 8: {
                mSelectedAvatar = 8;
                mDoctorAvatar.setImageResource(R.drawable.avatar_8);
                break;
            }
            default:
                Log.e(TAG, "changeAvatarSelected: unknown avatar value : " + avatarIdentification);
        }
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

            boolean result;
            String messagePassed = null;

            if (mEditOrAddMode) { //True for updating, False for adding
                result = updateDoctor();
                messagePassed = (result) ? "Doctor Updated Successfully": "Please provide Doctor's name";
            } else {
                result = saveNewDoctor();
                messagePassed = (result) ? "Doctor Saved Successfully": "Please provide Doctor's name";
            }

            if (result) {
                Toast.makeText(this, messagePassed, Toast.LENGTH_LONG).show();
                return onSupportNavigateUp();
            } else {
                Snackbar.make(mDoctorAvatar, messagePassed, Snackbar.LENGTH_LONG).show();
            }
            return result;

        } else {
            return onSupportNavigateUp();
        }
    }

    private boolean saveNewDoctor() {

        ContentResolver contentResolver;
        ContentValues contentValues;
        String doctorName = mDoctorName.getText().toString();
        boolean result = false;

        if (doctorName.length() > 0) {
            try {
                contentResolver = getContentResolver();
                contentValues = new ContentValues();

                contentValues.put(SQL_DoctorsContract.Columns.NAME, doctorName);
                contentValues.put(SQL_DoctorsContract.Columns.AVATAR_ID, mSelectedAvatar);
                contentValues.put(SQL_DoctorsContract.Columns.SPECIALITY, mDoctorSpeciality.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.FIRST_NUMBER, mDoctorFirstPhoneNumber.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.FIRST_NUMBER_TYPE, mMobileDescriptionOne.getSelectedItem().toString());
                contentValues.put(SQL_DoctorsContract.Columns.SECOND_NUMBER, mDoctorSecondPhoneNumber.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.SECOND_NUMBER_TYPE, mMobileDescriptionTwo.getSelectedItem().toString());
                contentValues.put(SQL_DoctorsContract.Columns.EMAIL, mDoctorEmail.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.ADDRESS, mDoctorAddress.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.HOSPITAL, mDoctorHospitalName.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.NOTES_DOCTOR, mDoctorNotes.getText().toString());

                Uri resultant = contentResolver.insert(SQL_DoctorsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "saveNewDoctor: returned uri : " + resultant);
                result = !result;

            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
        }

        return result;
    }

    private boolean updateDoctor() {

        ContentResolver contentResolver;
        ContentValues contentValues;
        String doctorName = mDoctorName.getText().toString();
        boolean result = false;

        if (doctorName.length() > 0) {
            try {
                contentResolver = getContentResolver();
                contentValues = new ContentValues();

                contentValues.put(SQL_DoctorsContract.Columns.NAME, doctorName);
                contentValues.put(SQL_DoctorsContract.Columns.AVATAR_ID, mSelectedAvatar);
                contentValues.put(SQL_DoctorsContract.Columns.SPECIALITY, mDoctorSpeciality.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.FIRST_NUMBER, mDoctorFirstPhoneNumber.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.FIRST_NUMBER_TYPE, mMobileDescriptionOne.getSelectedItem().toString());
                contentValues.put(SQL_DoctorsContract.Columns.SECOND_NUMBER, mDoctorSecondPhoneNumber.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.SECOND_NUMBER_TYPE, mMobileDescriptionTwo.getSelectedItem().toString());
                contentValues.put(SQL_DoctorsContract.Columns.EMAIL, mDoctorEmail.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.ADDRESS, mDoctorAddress.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.HOSPITAL, mDoctorHospitalName.getText().toString());
                contentValues.put(SQL_DoctorsContract.Columns.NOTES_DOCTOR, mDoctorNotes.getText().toString());

                int resultant = contentResolver.update( SQL_DoctorsContract.buildTaskUri(mCurrDoctorObject.get_ID()), contentValues, null, null);
                Log.d(TAG, "saveNewDoctor: updated row : " + resultant);
                result = !result;

            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
        }

        return result;
    }

}
