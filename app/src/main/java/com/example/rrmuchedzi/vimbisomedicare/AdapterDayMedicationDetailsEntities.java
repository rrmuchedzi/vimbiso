package com.example.rrmuchedzi.vimbisomedicare;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class AdapterDayMedicationDetailsEntities extends ArrayAdapter {

    private static final String TAG = "AdapterDayMedicationDet";
    private final int layoutResource;
    private final LayoutInflater mLayoutInflater;
    private List<DuplicatedScheduled> mMedicationScheduleds;

    public AdapterDayMedicationDetailsEntities(Context context, int resource, List<DuplicatedScheduled> dayMedication){
        super(context, resource);
        this.layoutResource = resource;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mMedicationScheduleds = dayMedication;
    }

    @Override
    public int getCount() {
        return mMedicationScheduleds.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final ViewHolder viewHolder;
        if( convertView == null ) {
            convertView = mLayoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final DuplicatedScheduled currentFeed = mMedicationScheduleds.get(position);

        final Integer pillImage = PillsAndMedicationManager.getPillIndentity( currentFeed.getMedicImageID());


        viewHolder.feedMedicationName.setText( currentFeed.getMedicName() );
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        final String timeConv = sdf.format(currentFeed.getTime());
        viewHolder.feedMedicationQuantity.setText( String.format("x%d %s to be taken at %s",currentFeed.getMedicQuantity(), currentFeed.getMedicUnits(),timeConv) );
        viewHolder.feedMedicationIMage.setImageResource(pillImage);
        viewHolder.feedMedicationTickBox.setImageResource( ( currentFeed.isTakenStatus() ? R.drawable.icon_complete_tickbox : R.drawable.icon_notcomplete_tickbox) );

        viewHolder.feedMedicationTickBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !currentFeed.isTakenStatus() ) {
                    viewHolder.feedMedicationTickBox.setImageResource(R.drawable.icon_complete_tickbox);
                    HistoryObject historyObject = new HistoryObject( currentFeed.get_ID(), currentFeed.getMedicImageID(), currentFeed.getMedicName(), new Date(), currentFeed.getTime(), currentFeed.getMedicQuantity()+"", currentFeed.getMedicUnits());
                    Log.d(TAG, "onClick: " + historyObject.toString());
                    currentFeed.setTakenStatus(true);
                    HomeActivity.mDatabaseResourcesManager.saveMedicationHistory( historyObject);
                    Snackbar.make(parent, "Marked as Taken", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar snackbar = Snackbar.make(parent, "Unmark as taken?", Snackbar.LENGTH_LONG).setAction("YES", new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(parent, "Complete", Snackbar.LENGTH_SHORT).show();
                            viewHolder.feedMedicationTickBox.setImageResource(R.drawable.icon_notcomplete_tickbox);
                            HistoryObject historyObject = new HistoryObject( currentFeed.get_ID(), currentFeed.getMedicImageID(), currentFeed.getMedicName(), new Date(), currentFeed.getTime(), currentFeed.getMedicQuantity()+"", currentFeed.getMedicUnits());
                            HomeActivity.mDatabaseResourcesManager.deleteMedicationHistory(historyObject);
                            currentFeed.setTakenStatus(false);
                        }
                    });

                    snackbar.show();
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final TextView feedMedicationName;
        final TextView feedMedicationQuantity;
        final ImageView feedMedicationIMage;
        final ImageView feedMedicationTickBox;

        public ViewHolder(View view) {
            this.feedMedicationName = view.findViewById(R.id.medication_feed_drugname);
            this.feedMedicationQuantity = view.findViewById(R.id.medication_feed_drugdescription);
            this.feedMedicationIMage = (ImageView) view.findViewById(R.id.medication_feed_image);
            this.feedMedicationTickBox = (ImageView) view.findViewById(R.id.medication_feed_tickbox);
        }
    }
}
