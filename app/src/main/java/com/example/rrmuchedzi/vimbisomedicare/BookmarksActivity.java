package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity implements FeedRecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "BookmarksActivity";
    private BookmarkEntitiesAdapter mBookmarkEntitiesAdapter;
    private RecyclerView mRecyclerView;

    private final static String BOOKMARK_ID = "BOOKMARK_ID";
    private final static String READER_STATUS = "READER_STATUS";

    List<BookmarkArticle> mBookmarkArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bookmarks_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.healthAndNews_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnItemTouchListener(new FeedRecyclerItemClickListener(this, mRecyclerView, this)); //Add the Click Listener
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        mBookmarkEntitiesAdapter = new BookmarkEntitiesAdapter(this, new ArrayList<BookmarkArticle>());
        mRecyclerView.setAdapter(mBookmarkEntitiesAdapter);
        getReaderArticles();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume: started");
        getReaderArticles();
        super.onResume();
        Log.d(TAG, "onResume: ended");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, FeedReaderActivity.class);
        intent.putExtra(BOOKMARK_ID, mBookmarkEntitiesAdapter.getHealthNewsFeed(position));
        intent.putExtra(READER_STATUS, true);
        startActivity(intent);
    }

    private void getReaderArticles() {
        mBookmarkArticles = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        String[] projection = {
                SQL_BookmarksContract.Columns._ID,
                SQL_BookmarksContract.Columns.TITLE,
                SQL_BookmarksContract.Columns.STORY,
                SQL_BookmarksContract.Columns.IMAGE_URL,
                SQL_BookmarksContract.Columns.PUBLISHED_DATE
        };

        Cursor cursor = contentResolver.query(SQL_BookmarksContract.CONTENT_URI, projection, null, null, SQL_MedicationContract.Columns._ID);

        int _ID = 0;
        String TITLE = null;
        String STORY = null;
        String IMAGE_URL = null;
        String PUBLISHED_DATE = null;

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        switch (cursor.getColumnName(i)) {
                            case SQL_BookmarksContract.Columns._ID: {
                                _ID = Integer.parseInt(cursor.getString(i));
                                break;
                            }
                            case SQL_BookmarksContract.Columns.TITLE: {
                                TITLE = cursor.getString(i);
                                break;
                            }
                            case SQL_BookmarksContract.Columns.STORY: {
                                STORY = cursor.getString(i);
                                break;
                            }
                            case SQL_BookmarksContract.Columns.IMAGE_URL: {
                                IMAGE_URL = cursor.getString(i);
                                break;
                            }
                            case SQL_BookmarksContract.Columns.PUBLISHED_DATE: {
                                PUBLISHED_DATE = cursor.getString(i);
                                break;
                            }
                            default:
                                Log.e(TAG, "getScheduledMedication: unrecognissed column :::: " + cursor.getColumnName(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getScheduledMedication: " + e.getMessage());
                    }
                }

                mBookmarkArticles.add(new BookmarkArticle(_ID, TITLE, STORY, IMAGE_URL, PUBLISHED_DATE));
            }

        }

        mBookmarkEntitiesAdapter.newDataNotificationPoint(mBookmarkArticles);
    }
}