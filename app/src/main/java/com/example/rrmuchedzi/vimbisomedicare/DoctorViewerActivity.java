package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewerActivity extends AppCompatActivity {

    private TextView name;
    private TextView speciality;
    private TextView firstnumber;
    private TextView firstnumbertype;
    private TextView secondnumber;
    private TextView secondnumbertype;
    private TextView email;
    private TextView notes;

    private ImageView firstNumberIcon;
    private ImageView secondNumberIcon;
    private ImageView emailIcon;
    private ImageView noteIcon;
    private CircleImageView mAvatar;

    private DoctorObject doctorObject;

    public static final String EDIT_DOCTOR_BUNDLE = "edit";

    private static final String TAG = "DoctorViewerActivity";

    private int selectedAvatar;
    private boolean isAppointment = false;
    public static final String DOC_APPOINTMENT = "doctor_appointment";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_viewer);
        Intent intent = getIntent();
        doctorObject = (DoctorObject) intent.getSerializableExtra(DoctorsActivity.DOCTOR_OBJECT);

        mAvatar = findViewById(R.id.form_doctor_entry_change_avatar);
        name = findViewById(R.id.view_doctor_name);
        speciality = findViewById(R.id.view_doctor_speciality);
        firstnumber = findViewById(R.id.viewer_doctor_phone_one);
        secondnumber = findViewById(R.id.viewer_doctor_phone_two);
        email = findViewById(R.id.viewer_doctor_email);
        notes = findViewById(R.id.viewer_doctor_notes);
        firstnumbertype = findViewById(R.id.viewer_doctor_phone_one_description);
        secondnumbertype = findViewById(R.id.viewer_doctor_phone_two_description);

        firstNumberIcon = findViewById(R.id.view_doctor_firstphoneicon);
        secondNumberIcon = findViewById(R.id.view_doctor_secondphoneicon);
        emailIcon = findViewById(R.id.view_doctor_email_icon);
        noteIcon = findViewById(R.id.view_doctor_noteicon);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addViewDetails();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp: ");
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_edit_doctor : {
                startNewActivity(AddDoctorActivity.class);
                break;
            }
            case R.id.menu_delete_doctor : {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteDoctorEntry();
                                break;
                        }
                        dialog.dismiss();
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you want to delete, Dr. " + doctorObject.getName() + "?" ).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                break;
            }
            case R.id.menu_email_doctor: {
                sendEmail();
                break;
            }
            case R.id.menu_appaointment_doctor: {
                isAppointment = true;
                startNewActivity(AddAppointmentActivity.class);
                break;
            }
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    private void addViewDetails() {

        switch ( doctorObject.getAvatar() ) {
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

        name.setText("Dr. "+ doctorObject.getName());
        String line;

        if( ( line = doctorObject.getSpeciality()) != null ) {
            speciality.setText(line);
        }

        line = doctorObject.getFirstNumber();

        if( line != null && line.trim().length() > 0 ) {
            firstnumber.setText(line);
            firstnumbertype.setText(doctorObject.getFirstNumber_type());
            firstNumberIcon.setVisibility(View.VISIBLE);
            firstnumber.setVisibility(View.VISIBLE);
            firstnumbertype.setVisibility(View.VISIBLE);
        }
        line = doctorObject.getSecondNumber();

        if( line != null && line.trim().length() > 0 ) {
            secondnumber.setText(line);
            secondnumbertype.setText(doctorObject.getSecondNumber_type());
            secondNumberIcon.setVisibility(View.VISIBLE);
            secondnumber.setVisibility(View.VISIBLE);
            secondnumbertype.setVisibility(View.VISIBLE);
        }
        line = doctorObject.getEmail();

        if( line != null && line.trim().length() > 0 ) {
            email.setText(line);
            emailIcon.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
        } else {
            emailIcon.setVisibility(View.GONE);
        }
        line = doctorObject.getNotes();

        if( line != null && line.trim().length() > 0 ) {
            notes.setText(line);
            noteIcon.setVisibility(View.VISIBLE);
            notes.setVisibility(View.VISIBLE);
        }

    }

    private void startNewActivity( Class targetClass) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtra(EDIT_DOCTOR_BUNDLE, doctorObject);
        startActivity(intent);
    }

    private void deleteDoctorEntry() {
        ContentResolver contentResolver = getContentResolver();
        int row = contentResolver.delete(SQL_DoctorsContract.buildTaskUri(doctorObject.get_ID()), null, null);
        Log.d(TAG, "deleteDoctorEntry: " + row);
        Toast.makeText(this, "Delete Successfull", Toast.LENGTH_SHORT).show();
        onSupportNavigateUp();
    }

    private void sendEmail() {
        final String email = doctorObject.getEmail();

        if( email == null || email.trim().length() > 0 ) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", doctorObject.getEmail(), null));
                            //intent.putExtra(Intent.EX)
                            startActivity( Intent.createChooser(intent, "Send Email to Dr. "+ doctorObject.getName()));
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            Snackbar.make(name, "Email not sent", Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                    dialog.dismiss();
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Email not set, would you like to enter manually later?" ).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}
