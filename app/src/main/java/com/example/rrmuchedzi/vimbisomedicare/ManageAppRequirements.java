package com.example.rrmuchedzi.vimbisomedicare;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;



public class ManageAppRequirements extends AsyncTask<String, Void, List <HealthNews>> {

    private static final String TAG = "ManageAppRequirements";

    interface onDataAvailable {
        void onDataAvailable(List <HealthNews> newFeed, DownloadStatus downloadStatus);
    }

    private DownloadFeed mDownloadFeed;
    private ParseXMLHealthNewsFeed mParseXMLHealthNewsFeed;
    private DownloadStatus mDownloadStatus;
    private final onDataAvailable mCallBackRef;

    //Class constructor assigns onDownloadComplete to object to call back
    public ManageAppRequirements( onDataAvailable callback ) {
        mDownloadStatus = DownloadStatus.IDLE;
        mCallBackRef = callback;
    }

    @Override
    protected void onPostExecute( List <HealthNews> feedEntries ) {
        //We call back our main activity when the data is available for viewing to the user
        mParseXMLHealthNewsFeed = null;
        if(mCallBackRef != null ) {
            mCallBackRef.onDataAvailable( feedEntries, DownloadStatus.OK );
        }
    }

    @Override
    protected List <HealthNews> doInBackground(String... strings) {
        String urlPath = strings[0];
        mDownloadFeed = new DownloadFeed();
        String downloadResult = mDownloadFeed.downloadFeedFromURL(urlPath);

        if( downloadResult != null ) {
            mParseXMLHealthNewsFeed = new ParseXMLHealthNewsFeed();
            if( mParseXMLHealthNewsFeed.parseHealthNewsXML(downloadResult) ) {
                mDownloadFeed = null;
                return mParseXMLHealthNewsFeed.getAvailableNewsFeed();
            } else {
                Log.e(TAG, "doInBackground: ERROR PASSING DATA");
            }

        } else {
            Log.e(TAG, "doInBackground: ERROR DOWNLOADING UPDATE");
        }
        
        return null;
    }
}
