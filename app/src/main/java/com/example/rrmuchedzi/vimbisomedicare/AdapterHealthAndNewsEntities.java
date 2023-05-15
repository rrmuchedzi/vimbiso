package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This is the Adapter for the RecyclerView used to display Feed Entries
 */

class AdapterHealthAndNewsEntities extends RecyclerView.Adapter<AdapterHealthAndNewsEntities.HealthAndNewsHolder> {

    private Context mContext;
    private List <HealthNews> mFeedEntriesList;
    private static final String TAG = "AdapterHealthAndNewsEnt";

    public AdapterHealthAndNewsEntities(Context context, List<HealthNews> feedEntriesList ) {
        mContext = context;
        this.mFeedEntriesList = feedEntriesList;
    }

    @Override
    public HealthAndNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This method inflates prepares and inflates the XML that will be used for feed display, Returns XML View as RecyclerView.Holder
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.broswe_healthmedical_feed, parent, false);
        return new HealthAndNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(HealthAndNewsHolder holder, int position) {
        if( mFeedEntriesList != null && mFeedEntriesList.size() != 0 ) {
            HealthNews healthNewsFeedEntry = mFeedEntriesList.get(position);
            Log.d(TAG, "onBindViewHolder: Header - " + healthNewsFeedEntry.getPostBanner());
            Picasso.with(mContext).load(healthNewsFeedEntry.getPostBanner()).error(R.drawable.img_feed_placeholder).placeholder(R.drawable.img_feed_placeholder).into( holder.feedThumbnail );
            holder.feedTitle.setText( healthNewsFeedEntry.getTitle() );
            holder.feedDescription.setText( healthNewsFeedEntry.getDescription() );
        } else {
            holder.feedThumbnail.setImageResource( R.drawable.img_feed_placeholder );
            holder.feedTitle.setText( R.string.empty_feed_entries_title );
            holder.feedDescription.setText( R.string.empty_feed_entries_description );
        }
    }

    @Override
    public int getItemCount() {
        Integer feedEntries = ( ( mFeedEntriesList != null && mFeedEntriesList.size() != 0 ) ? mFeedEntriesList.size() : 1 ); //Return one at least
        return feedEntries;
    }

    void newDataNotificationPoint( List<HealthNews> newFeedEntries ) {
        mFeedEntriesList = newFeedEntries;
        notifyDataSetChanged();
    }

    static class HealthAndNewsHolder extends RecyclerView.ViewHolder {

        private ImageView feedThumbnail;
        private TextView feedTitle;
        private TextView feedDescription;

        public HealthAndNewsHolder(View view) {
            super(view);
            this.feedThumbnail = (ImageView) view.findViewById(R.id.feed_thumbnail_img);
            this.feedTitle = (TextView) view.findViewById(R.id.feed_title_text);
            this.feedDescription = (TextView) view.findViewById(R.id.feed_Description_text);
        }

    }

    public HealthNews getHealthNewsFeed(int position) {
        return mFeedEntriesList.get(position);
    }

}
