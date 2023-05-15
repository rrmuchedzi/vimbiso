package com.example.rrmuchedzi.vimbisomedicare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HealthAndMedicalActivity extends AppCompatActivity implements ManageAppRequirements.onDataAvailable, FeedRecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "HealthAndMedicalActivit";
    private ManageAppRequirements mManageAppRequirements;
    private AdapterHealthAndNewsEntities mAdapterHealthAndNewsEntities;
    private RecyclerView mRecyclerView;

    //Health And News Feed Url Should be stored in Database
    private static final String MedLinePlusURL = "https://medlineplus.gov/feeds/news_en.xml";
    private final static String ELEMENT_ID = "ELEMENT_ID";
    private final static String READER_STATUS = "READER_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_and_medical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.health_and_medical_toolbar);
        setSupportActionBar(toolbar);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

            // return;
            //}
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.healthAndNews_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnItemTouchListener(new FeedRecyclerItemClickListener(this, mRecyclerView, this)); //Add the Click Listener
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        mAdapterHealthAndNewsEntities = new AdapterHealthAndNewsEntities( this, new ArrayList<HealthNews>() );
        mRecyclerView.setAdapter(mAdapterHealthAndNewsEntities);

        mManageAppRequirements = new ManageAppRequirements(this);
        downloadHealthNewsFeed();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void downloadHealthNewsFeed() {
        mManageAppRequirements.execute(MedLinePlusURL);
    }

    @Override
    public void onDataAvailable(List<HealthNews> newFeed, DownloadStatus downloadStatus) {
//        Log.d(TAG, "onDataAvailable: ENTRY NOTIFICATION: AVAILABLE FEED ENTRIES : " + newFeed.size() );
        //Check here is Internet connection and download was successful
        mAdapterHealthAndNewsEntities.newDataNotificationPoint(newFeed);
        Log.d(TAG, "onDataAvailable: EXIT");
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, FeedReaderActivity.class);
        intent.putExtra(ELEMENT_ID, mAdapterHealthAndNewsEntities.getHealthNewsFeed(position));
        intent.putExtra(READER_STATUS, false);
        startActivity(intent);
    }
}
