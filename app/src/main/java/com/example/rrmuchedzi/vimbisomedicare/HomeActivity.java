package com.example.rrmuchedzi.vimbisomedicare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity"; //Remove on complete

    private Button pillReminderMenu;
    private Button myDoctorsMenu;
    private Button healthAndMedicalNewsMenu;
    private Button medicationHistoryMenu;
    private Button navigationDrawerMenu;

    private TextView mCurrentDay;
    private TextView mCurrentDate;
    private TextView mHomeUsername;

    private boolean isDrawerOpened = false;
    private DrawerLayout mDrawerLayout;
    private final List<Button> homeButtons = new ArrayList<>();
    public static UserObject mUserRef;
    private NavigationView mNavigationView;

    private CircleImageView mNavAvatar;
    private TextView mNavUsername;
    private TextView mNavEmail;

    private ArrayList<AppointmentObject> mAppointmentObjects;
    private final LoadAppointments mLoadAppointments = new LoadAppointments();
    public static final String APPOINTMENTS_OBJECT = "appointments_collection";

    private final static String CURRENT_USER_REF = "current_ref";
    public static DatabaseResourcesManager mDatabaseResourcesManager;

    private AlarmsHandler mAlarmsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instantiateHomeButtons();
        getUserDetails();

        mAlarmsHandler = new AlarmsHandler(this);
        mAlarmsHandler.setUpAlarmSchedule(mDatabaseResourcesManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
    }

    private void instantiateHomeButtons() {
        pillReminderMenu = findViewById(R.id.btn_menu_pill_and_meds_reminder);
        myDoctorsMenu = findViewById(R.id.btn_menu_mydoctors);
        healthAndMedicalNewsMenu = findViewById(R.id.btn_menu_health_and_medical_news);
        medicationHistoryMenu = findViewById(R.id.btn_menu_medicalHistory);
        navigationDrawerMenu = findViewById(R.id.btn_menu_navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        homeButtons.add(pillReminderMenu);
        homeButtons.add(myDoctorsMenu);
        homeButtons.add(healthAndMedicalNewsMenu);
        homeButtons.add(medicationHistoryMenu);
        homeButtons.add(navigationDrawerMenu);

        mCurrentDate = findViewById(R.id.txtview_menu_currentDate);
        mCurrentDay = findViewById(R.id.txtview_menu_homeCurrentDay);

        mNavigationView = findViewById(R.id.nav_view);
        mHomeUsername = findViewById(R.id.txtview_homeUserName);
        mDatabaseResourcesManager = new DatabaseResourcesManager(this);
        mDatabaseResourcesManager.getScheduledEvents();
        for (Button elementBTN : homeButtons) {
            elementBTN.setOnClickListener(mHomeButtonsClickListener);
        }

        mDrawerLayout.setDrawerListener(
                new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.btn_home_side_menu,
                0,
                0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpened = false;//is Closed
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpened = true;//is Opened
            }
        });

        setupDate();
        mAppointmentObjects = mLoadAppointments.loadAppointments(getContentResolver());

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Check to see which item was being clicked and perform appropriate action
                switch (item.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                        break;
                    }
                    case R.id.nav_appointments: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                        startAppointmentsActivity();
                        break;
                    }
                    case R.id.nav_bookmarks: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                        startNewActivity(BookmarksActivity.class);
                        break;
                    }
                    case R.id.nav_settings: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                        startNewActivity(SettingsActivity.class);
                        break;
                    }
                    case R.id.nav_about: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                        startNewActivity(AboutActivity.class);
                        break;
                    }
                    default: {
                        mDrawerLayout.closeDrawers();
                        isDrawerOpened = false;
                    }
                }

                return true;
            }
        });
    }

    View.OnClickListener mHomeButtonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()) {
                case R.id.btn_menu_pill_and_meds_reminder: {
                    startNewActivity(PillAndMedsReminderActivity.class);
                    break;
                }
                case R.id.btn_menu_health_and_medical_news: {
                    startNewActivity(HealthAndMedicalActivity.class);
                    break;
                }
                case R.id.btn_menu_mydoctors: {
                    startNewActivity(DoctorsActivity.class);
                    break;
                }
                case R.id.btn_menu_medicalHistory: {
                    startNewActivity(HistoryActivity.class);
                    break;
                }
                case R.id.btn_menu_navigation_drawer: {
                    clickEventSlide();
                    break;
                }
                default:
                    return;
            }



        }
    };

    private void startNewActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(CURRENT_USER_REF, mUserRef);
        startActivity(intent);
    }

    private void startAppointmentsActivity(){
        Intent intent = new Intent(this, AppointmentsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(APPOINTMENTS_OBJECT, mAppointmentObjects);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void clickEventSlide() {
        if (isDrawerOpened) {
            mDrawerLayout.closeDrawer(Gravity.END);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    private void setupDate() {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        mCurrentDay.setText(weekDay);
        dayFormat = new SimpleDateFormat("dd-MM-yyyy");
        weekDay = dayFormat.format(calendar.getTime());
        mCurrentDate.setText(weekDay);
    }

    private void getUserDetails() {
        mUserRef = new DatabaseResourcesManager(this).getUserDetails();
        if( mUserRef != null ) {
            addUserDetails();
        }

    }

    private void changeAvatarSelected(Integer avatarIdentification) {
        switch (avatarIdentification) {
            case 1: {
                mNavAvatar.setImageResource(R.drawable.user_avatar_1);
                break;
            }
            case 2: {
                mNavAvatar.setImageResource(R.drawable.user_avatar_2);
                break;
            }
            case 3: {
                mNavAvatar.setImageResource(R.drawable.user_avatar_3);
                break;
            }
            case 4: {
                mNavAvatar.setImageResource(R.drawable.user_avatar_4);
                break;
            }
            default:
                Log.e(TAG, "changeAvatarSelected: unknown avatar value : " + avatarIdentification);
        }

        Log.d(TAG, "changeAvatarSelected: selected avatar : " + avatarIdentification);
    }

    public void addUserDetails() {
        Log.d(TAG, "addUserDetails: STARTED HERE");
        View headerView = mNavigationView.getHeaderView(0);
        mNavAvatar = headerView.findViewById(R.id.nav_useravatar);
        mNavUsername = headerView.findViewById(R.id.nav_username_txt);
        mNavEmail = headerView.findViewById(R.id.nav_emailaddress_txt);

        mNavEmail.setText(mUserRef.getEMAIL());
        mNavUsername.setText(mUserRef.getNAME() +" " + mUserRef.getSURNAME());
        changeAvatarSelected(mUserRef.getAVATAR_ID());
        Log.d(TAG, "addUserDetails: ENDED HERE");

        mHomeUsername.setText(mUserRef.getNAME());
    }

    public void showNotification( String message ) {
        Log.d(TAG, "showNotification: Tasvika Pano And UserRef : " + mUserRef);
    }

    public static void triggerChangesUpdates() {
        mDatabaseResourcesManager.getScheduledEvents();
    }
}
