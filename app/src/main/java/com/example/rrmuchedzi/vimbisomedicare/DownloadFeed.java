package com.example.rrmuchedzi.vimbisomedicare;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

class DownloadFeed {

    private static final String TAG = "DownloadFeed";
    private DownloadStatus mDownloadStatus;

    public String downloadFeedFromURL( String URLPath ) {

        StringBuilder stringBuilder = new StringBuilder();
        Integer responseCode = null;
        URL downlaodURL = null;
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String line = null;

        try {

            downlaodURL = new URL(URLPath);
            urlConnection = (HttpURLConnection) downlaodURL.openConnection();
            responseCode = urlConnection.getResponseCode();
            bufferedReader = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );

            while( null != ( line = bufferedReader.readLine() ) ) {
                stringBuilder.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            Log.d(TAG, "downloadFeedFromURL: DOWNLOAD COMPLETE");
            return stringBuilder.toString();

        } catch(MalformedURLException e) {
            Log.e(TAG, "downloadXMLFeedFromURL: MalformedURLException "+ e.getMessage());
        }  catch(IOException e) {
            Log.d(TAG, "downloadXMLFeedFromURL: IOException"+ e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "downloadFeedFromURL: SecurityException "+ e.getMessage());
        }finally {
            if( urlConnection != null ) {
                urlConnection.disconnect();
            }
            if( bufferedReader != null) {
                try {
                    bufferedReader.close();
                }catch (IOException e) {
                    Log.e(TAG, "downloadFeedFromURL: BufferedReader Error closing stream" + e.getMessage() );
                }
            }
            Log.d(TAG, "downloadFeedFromURL: RESPONCE CODE : " + responseCode );
            Log.e(TAG, "downloadFeedFromURL: RESPONCE CODE : " + responseCode );
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;

    }
}
