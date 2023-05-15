package com.example.rrmuchedzi.vimbisomedicare;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedReaderActivity extends AppCompatActivity {
    private static final String TAG = "FeedReaderActivity";
    private HealthNews mHealthNewsFeed;
    private BookmarkArticle mBookmarkArticle;

    private ImageView mFeedBanner;
    private TextView mFeedTitle;
    private ImageView mFeedFavourite;
    private ImageView mFeedDelete;
    private TextView mFeedStory;
    private TextView mFeedPostDate;

    private static Handler mHandler;
    private boolean mReaderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_reader);

        Bundle passedIntent = getIntent().getExtras();
        mHandler = new Handler();
        setupReaderElements();

        if (passedIntent != null) {
            mReaderStatus = (boolean) passedIntent.getBoolean("READER_STATUS");
            mHealthNewsFeed = (HealthNews) passedIntent.getSerializable("ELEMENT_ID" );
            if( mReaderStatus ) {
                mBookmarkArticle = (BookmarkArticle) passedIntent.getSerializable("BOOKMARK_ID");
                readerDataFromDatabase();
            } else {
                mHealthNewsFeed = (HealthNews) passedIntent.getSerializable("ELEMENT_ID" );
                if (mHealthNewsFeed != null) {
                    instantiateReaderElements();
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void readerDataFromDatabase() {
        Log.d(TAG, "readerDataFromDatabase: entered");
        mFeedDelete = findViewById(R.id.feed_reader_remove);
        mFeedDelete.setVisibility(View.VISIBLE);
        mFeedDelete.setOnClickListener(mFavouriteClickListener);
        Picasso.with(this).load( mBookmarkArticle.getImageLink()).error(R.drawable.img_feed_placeholder).placeholder(R.drawable.img_feed_placeholder).into(mFeedBanner);
        mFeedTitle.setText( mBookmarkArticle.getTitle() );
        mFeedPostDate.setText( mBookmarkArticle.getPostDate() );
        mFeedStory.setText( mBookmarkArticle.getStory() );
        mFeedFavourite.setImageResource(R.drawable.unmark_as_favourite);
    }


    private void instantiateReaderElements() {
        downloadHTMLPage(mHealthNewsFeed.getLink());
        Picasso.with(this).load(mHealthNewsFeed.getPostBanner()).error(R.drawable.img_feed_placeholder).placeholder(R.drawable.img_feed_placeholder).into(mFeedBanner);
        mFeedTitle.setText(mHealthNewsFeed.getTitle());
        mFeedPostDate.setText( mHealthNewsFeed.getPostDate());
    }

    private void setupReaderElements() {
        try {
            mFeedBanner = findViewById(R.id.feed_reader_banner_img);
            mFeedFavourite = findViewById(R.id.feed_reader_favourite);
            mFeedTitle = findViewById(R.id.feed_reader_title);
            mFeedPostDate = findViewById(R.id.feed_reader_postdate);
            mFeedStory = findViewById(R.id.feed_reader_story);

            mFeedFavourite.setOnClickListener(mFavouriteClickListener);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }catch (NullPointerException e) {
            Log.e(TAG, "setupReaderElements: Could not find resource for Reader Elements"+ e.getMessage() );
        }
    }

    View.OnClickListener mFavouriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Implement Favoutes logic after database instantiation
            if( !mReaderStatus ) {
                saveArticleToDatabase(); //Todo, implement delete from database
                Snackbar.make(view, "Added to Favourites", Snackbar.LENGTH_SHORT).show();
                mFeedFavourite.setImageResource(R.drawable.unmark_as_favourite);
            }

            if( view.getId() == R.id.feed_reader_remove ) {
                deleteFromDatabase();
            }
        }
    };

    private void downloadHTMLPage( final String HTMLPageLink ) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = new ParseHTMLFeedArticle(HTMLPageLink).downloadAndParseHTMLPage();
                mHandler.post( new Runnable() {
                    @Override
                    public void run() {
                        String articleResult;
                        final Matcher matcher = Pattern.compile(" -- ").matcher(result);
                        if(matcher.find()){
                            articleResult = result.substring(matcher.end()).trim();
                        } else {
                            articleResult = result;
                        }
                        mFeedStory.setText(articleResult);
                    }
                });
            }
        }).start();
    }

    private void saveArticleToDatabase() {

        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SQL_BookmarksContract.Columns.TITLE, mHealthNewsFeed.getTitle());
        contentValues.put(SQL_BookmarksContract.Columns.IMAGE_URL, mHealthNewsFeed.getPostBanner());
        contentValues.put(SQL_BookmarksContract.Columns.STORY, mFeedStory.getText().toString());
        contentValues.put(SQL_BookmarksContract.Columns.PUBLISHED_DATE, mHealthNewsFeed.getPostDate());

        Uri uri = contentResolver.insert(SQL_BookmarksContract.CONTENT_URI, contentValues);
        Log.d(TAG, "onCreate: uri = " + uri);
    }

    private void deleteFromDatabase() {
        ContentResolver contentResolver = getContentResolver();
        int row = contentResolver.delete(SQL_BookmarksContract.buildTaskUri(mBookmarkArticle.get_ID()), null, null);
        Log.d(TAG, "delete Bookmark: " + row);
        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        onSupportNavigateUp();
    }
}
