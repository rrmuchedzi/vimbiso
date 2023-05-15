package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private List<AppointmentObject> mAppointmentObjects;
    private ListView mAppointmentsListView;
    private TextView mMessageTextView;
    AppointmentsListViewAdapter doctorsListViewAdapter;
    public static final String APPOINTMENT_VIEW = "appointmentview";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mAppointmentObjects = (ArrayList<AppointmentObject>) bundle.getSerializable(HomeActivity.APPOINTMENTS_OBJECT);
        }

        mAppointmentsListView = findViewById(R.id.appointments_list_view);
        mMessageTextView = findViewById(R.id.appointments_no_entry_message);

        doctorsListViewAdapter = new AppointmentsListViewAdapter(this, R.layout.browse_appointment, mAppointmentObjects);
        mAppointmentsListView.setAdapter(doctorsListViewAdapter);

        mAppointmentsListView.setEmptyView(mMessageTextView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAppointmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewAppointment(i);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void viewAppointment( int position ) {
        Intent intent = new Intent(this , AppointmentViewerActivity.class);
        intent.putExtra(APPOINTMENT_VIEW, mAppointmentObjects.get(position));
        startActivity(intent);
    }
}
