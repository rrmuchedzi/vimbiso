package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private CircleImageView mAvatar;
    final Context mContext = this;
    private TextView mUsernameDisplay;
    private EditText mUserName;
    private EditText mUserSurname;
    private EditText mUserEmail;

    private Button mChangeOrAddPin;
    private Button mRateUs;
    private Button mFeedback;
    private UserObject mCurrentUser;
    private int mSelectedAvatar;
    private Integer currentPin;
    private boolean editorinsetMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeControls();
        Bundle passedIntent = getIntent().getExtras();

        if ( passedIntent != null ) {
            mCurrentUser = (UserObject) passedIntent.getSerializable("current_ref");
            if (mCurrentUser != null) {
                uploadCurrentValues();
                editorinsetMode = true;
            }
        } else {
            editorinsetMode = false;
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeControls() {
        mAvatar = findViewById(R.id.settings_avatar);
        mUsernameDisplay = findViewById(R.id.settings_main_username_display);
        mUserName = findViewById(R.id.settings_name_feild);
        mUserSurname = findViewById(R.id.settings_surname_feild);
        mUserEmail = findViewById(R.id.settings_email_feild);

        mChangeOrAddPin = findViewById(R.id.setting_change_pin);
        mFeedback = findViewById(R.id.settings_feedback_feild);

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.dialog_user_avatar, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                alertDialogBuilder.setTitle("Select Your Avatar");
                alertDialogBuilder.setView(dialogView);

                final AlertDialog alertDialog = alertDialogBuilder.create();

                ImageButton avatar_1 = dialogView.findViewById(R.id.user_avatar_1);
                ImageButton avatar_2 = dialogView.findViewById(R.id.user_avatar_2);
                ImageButton avatar_3 = dialogView.findViewById(R.id.user_avatar_3);
                ImageButton avatar_4 = dialogView.findViewById(R.id.user_avatar_4);

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int clickValue = mSelectedAvatar;

                        switch (view.getId()) {
                            case R.id.user_avatar_1: {
                                clickValue = 1;
                                break;
                            }
                            case R.id.user_avatar_2: {
                                clickValue = 2;
                                break;
                            }
                            case R.id.user_avatar_3: {
                                clickValue = 3;
                                break;
                            }
                            case R.id.user_avatar_4: {
                                clickValue = 4;
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

                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    private void changeAvatarSelected(Integer avatarIdentification) {
        switch (avatarIdentification) {
            case 1: {
                mSelectedAvatar = 1;
                mAvatar.setImageResource(R.drawable.user_avatar_1);
                break;
            }
            case 2: {
                mSelectedAvatar = 2;
                mAvatar.setImageResource(R.drawable.user_avatar_2);
                break;
            }
            case 3: {
                mSelectedAvatar = 3;
                mAvatar.setImageResource(R.drawable.user_avatar_3);
                break;
            }
            case 4: {
                mSelectedAvatar = 4;
                mAvatar.setImageResource(R.drawable.user_avatar_4);
                break;
            }
            default:
                Log.e(TAG, "changeAvatarSelected: unknown avatar value : " + avatarIdentification);
        }

        Log.d(TAG, "changeAvatarSelected: selected avatar : " + mSelectedAvatar);
    }

    private void uploadCurrentValues() {
        changeAvatarSelected(mCurrentUser.getAVATAR_ID());
        mUsernameDisplay.setText(mCurrentUser.getNAME());
        mUserName.setText(mCurrentUser.getNAME());
        mUserSurname.setText(mCurrentUser.getSURNAME());
        mUserEmail.setText(mCurrentUser.getEMAIL());
        currentPin = mCurrentUser.getSECURITY();
    }

    private boolean insetOrUpdateUser() {

        ContentResolver contentResolver;
        ContentValues contentValues;

        String username = mUserName.getText().toString();
        boolean result = false;

        if( editorinsetMode ) { //True - We Updating
            contentResolver = getContentResolver();
            contentValues = new ContentValues();

            contentValues.put(SQL_UserContract.Columns.NAME, username.trim());
            contentValues.put(SQL_UserContract.Columns.AVATAR_ID, mSelectedAvatar);
            contentValues.put(SQL_UserContract.Columns.SURNAME, mUserSurname.getText().toString().trim());
            contentValues.put(SQL_UserContract.Columns.EMAIL, mUserEmail.getText().toString().trim());
            contentValues.put(SQL_UserContract.Columns.SECURITY, currentPin);

            String whereToUpdate = SQL_UserContract.Columns._ID + " IS " + mCurrentUser.get_ID();

            int rowUpdated = contentResolver.update(SQL_UserContract.CONTENT_URI, contentValues, whereToUpdate, null);
            Log.d(TAG, "saveNewUser: returned uri : " + rowUpdated);
            result = !result;

        } else { //We are inserting new record

            if (username.length() > 0) {
                try {
                    contentResolver = getContentResolver();
                    contentValues = new ContentValues();

                    contentValues.put(SQL_UserContract.Columns.NAME, username.trim());
                    contentValues.put(SQL_UserContract.Columns.AVATAR_ID, mSelectedAvatar);
                    contentValues.put(SQL_UserContract.Columns.SURNAME, mUserSurname.getText().toString().trim());
                    contentValues.put(SQL_UserContract.Columns.EMAIL, mUserEmail.getText().toString().trim());
                    contentValues.put(SQL_UserContract.Columns.SECURITY, currentPin);

                    Uri resultant = contentResolver.insert(SQL_UserContract.CONTENT_URI, contentValues);
                    Log.d(TAG, "saveNewUser: returned uri : " + resultant);
                    result = !result;

                } catch (Exception e) {
                    result = false;
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_doctor) {
            String name = mUserName.getText().toString().trim();
            Log.d(TAG, "onOptionsItemSelected: name :" + name);
            if( null != name && name.length() > 0  ) {
                if( insetOrUpdateUser()) {
                    Toast.makeText(mContext, "New Settings Saved", Toast.LENGTH_SHORT).show();
                    onNavigateUp();
                }
            } else {
                createSnackBack("Please enter your name to save settings");
            }
        } else {
            return onNavigateUp();
        }

        return false;

    }

    private void createSnackBack(String message) {
        Snackbar.make(mUserName, message, Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
    }
}
