package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class DayViewPillsAndMedsActivity extends AppCompatActivity {
    private static final String TAG = "DayViewPillsAndMedsActi";
    private DayViewerFragmentsAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private static ImageView mToolbarHeaderImage;

    private String operationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view_pills_and_meds);

        Toolbar toolbar = findViewById(R.id.dayviewertoolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: THIS IS RUN");

        if( savedInstanceState != null ) {
            Log.d(TAG, "onCreate: EXPECTED THIS IS RUN");
        } else {
            Log.d(TAG, "onCreate: Adding a new SupportFragment");
            mSectionsPagerAdapter = new DayViewerFragmentsAdapter( getSupportFragmentManager());
        }

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        operationDate = DateFormat.getDateInstance().format(c.getTime());

//        Intent intent = getIntent();

        mToolbarHeaderImage = findViewById(R.id.dayviewer_header_image_background);
        setupViewPager();
        mTabLayout = findViewById(R.id.day_tabs_menu);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabIcons();
        changeToolHeaderImage(mViewPager.getCurrentItem());

        //Initialize toolbar back Button. If you on v7, override onSupportNavigationUp()
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeActivity.triggerChangesUpdates();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called whenever the user chooses to navigate Up within your application's activity hierarchy from the action bar.
        onBackPressed();
        return true;
    }

    private void setupViewPager() {

        mViewPager = findViewById(R.id.pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeToolHeaderImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     *
     * @param sectionSelected changes the toolbar header section image.
     *                        Has for options for Morning, Afternoon, Evening and Night
     *                        @param sectionSelected must be between 0 and 3.
     *                        Parama passed to @changeToolHeaderImage()
     */
    private static void changeToolHeaderImage( int sectionSelected ) {
        switch (sectionSelected) {
            case 0: {
                mToolbarHeaderImage.setImageResource(R.drawable.img_dayview_morning);
                break;
            }
            case 1: {
                mToolbarHeaderImage.setImageResource(R.drawable.img_dayview_afternoon);
                break;
            }
            case 2: {
                mToolbarHeaderImage.setImageResource(R.drawable.img_dayview_evening);
                break;
            }
            case 3: {
                mToolbarHeaderImage.setImageResource(R.drawable.img_dayview_night);
                break;
            }
        }
    }

    /**
     * Setups the icons for the TabLayout Menus. Can throw
     * NullPointerException is icon is missing
     */
    private void setTabIcons() {

        try {
            mTabLayout.getTabAt(0).setIcon(R.drawable.img_morning_icon);
            mTabLayout.getTabAt(1).setIcon(R.drawable.img_afternoon_icon);
            mTabLayout.getTabAt(2).setIcon(R.drawable.img_sunset_icon);
            mTabLayout.getTabAt(3).setIcon(R.drawable.img_night_icon);
        } catch (NullPointerException e) {
            Log.e(TAG, "setTabIcons: Icon Resource not found for TabMenus" + e.getMessage() );
        }

    }
}
