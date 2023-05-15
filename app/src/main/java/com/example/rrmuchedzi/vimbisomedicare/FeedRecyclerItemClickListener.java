package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * ClickListener for the
 */

public class FeedRecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "FeedRecyclerItemClickLi";

    interface OnRecyclerClickListener { //For call back
        void onItemClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public FeedRecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener){
        mListener = listener;

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                return super.onSingleTapUp(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: Starts");

        if( mGestureDetector != null ) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: Returned - "+ result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: Returned false, null mGestureDetector ");
            return false;
        }
    }
}
