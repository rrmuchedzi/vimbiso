package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentViewerActivity extends AppCompatActivity {

    private CircleImageView mAvatar;
    private TextView mTitle;
    private TextView mDate;
    private TextView mTime;
    private TextView mLocation;
    private TextView mNotes;

    private AppointmentObject appointmentObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAvatar = findViewById(R.id.final_appointment_avatar);
        mTitle = findViewById(R.id.final_appointment_title);
        mDate = findViewById(R.id.final_appointment_date);
        mTime = findViewById(R.id.final_appointment_time);
        mLocation = findViewById(R.id.final_appointment_location);
        mNotes = findViewById(R.id.final_appointment_notes);

        Intent intent = getIntent();
        appointmentObject = (AppointmentObject) intent.getSerializableExtra(AppointmentsActivity.APPOINTMENT_VIEW);
        if( appointmentObject != null ) {
            setDetails ();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setDetails () {
        switch ( appointmentObject.getDoctorAvatar() ) {
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

        mTitle.setText(appointmentObject.getName());

        Date date;
        String stringDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = df.parse(appointmentObject.getDate());
            stringDate = DateFormat.getDateInstance().format(date);
        } catch ( ParseException e) {
            e.printStackTrace();
        }

        mDate.setText((stringDate != null ? stringDate : appointmentObject.getDate()));
        mTime.setText(appointmentObject.getTime());

        String line = appointmentObject.getLocation();

        if(line != null) {
            mLocation.setText(line);
        }

        line = appointmentObject.getNotes();

        if(line != null) {
            mNotes.setText(line);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
