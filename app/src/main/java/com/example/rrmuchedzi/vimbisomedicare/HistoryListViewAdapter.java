package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class HistoryListViewAdapter extends ArrayAdapter {

    private List<HistoryObject> mDoctorObjectList;
    private final int layoutResource;
    private final LayoutInflater mLayoutInflater;
    private int position;

    public HistoryListViewAdapter(Context context, int resource, List<HistoryObject> dataProvider) {
        super(context, resource); //Requirement
        layoutResource = resource;
        mDoctorObjectList = dataProvider;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDoctorObjectList.size(); //Todo, implement to show atleast once entry
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if( convertView == null ) {
            convertView = mLayoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HistoryObject currentHistoryElement = mDoctorObjectList.get(position);

        switch ( currentHistoryElement.getMedication_image_id() ) {
            case 1 : {
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_blue);
                break;
            }
            case 2 : {
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_green);
                break;
            }
            case 3 : {
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_orange);
                break;
            }
            case 4 : {
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_purple);
                break;
            }
            case 5 : {
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_red);
                break;
            }
            default:
                viewHolder.mAvatar.setImageResource(R.drawable.drugpill_red);
        }

        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.mMeds_name.setText( currentHistoryElement.getMedication_name() );
        viewHolder.mMeds_dossage.setText( currentHistoryElement.getMedication_dosage() + " " + currentHistoryElement.getMedication_dosage_quantity() );
        viewHolder.mMeds_date.setText( mDateFormat.format(currentHistoryElement.getMedication_date()) );

        return convertView;
    }

    private class ViewHolder {

        private CircleImageView mAvatar;
        private TextView mMeds_name;
        private TextView mMeds_dossage;
        private TextView mMeds_date;

        public ViewHolder(View view) {
            mAvatar = view.findViewById(R.id.history_med_image);
            mMeds_name = view.findViewById(R.id.history_med_name);
            mMeds_dossage = view.findViewById(R.id.history_med_dossage);
            mMeds_date = view.findViewById(R.id.history_med_date);
        }
    }
}
