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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class AppointmentsListViewAdapter extends ArrayAdapter {

    private List<AppointmentObject> mAppointmentObjectList;
    private final int layoutResource;
    private final LayoutInflater mLayoutInflater;
    private int position;

    public AppointmentsListViewAdapter(Context context, int resource, List<AppointmentObject> dataProvider) {
        super(context, resource); //Requirement
        layoutResource = resource;
        mAppointmentObjectList = dataProvider;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAppointmentObjectList.size();
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

        AppointmentObject currentAppointment = mAppointmentObjectList.get(position);

        switch ( currentAppointment.getDoctorAvatar() ) {
            case 1 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_1);
                break;
            }
            case 2 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_2);
                break;
            }
            case 3 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_3);
                break;
            }
            case 4 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_4);
                break;
            }
            case 5 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_5);
                break;
            }
            case 6 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_6);
                break;
            }
            case 7 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_7);
                break;
            }
            case 8 : {
                viewHolder.mAvatar.setImageResource(R.drawable.avatar_8);
                break;
            }
            default:
                viewHolder.mAvatar.setImageResource(R.drawable.profile_avatar_placeholder);
        }

        viewHolder.mAppointmentName.setText( currentAppointment.getName() );
        Date date;
        String stringDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = df.parse(currentAppointment.getDate());
            stringDate = DateFormat.getDateInstance().format(date);
        } catch ( ParseException e) {
            e.printStackTrace();
        }

        viewHolder.mAppointmentDate.setText( String.format("%s,\t%s", ( stringDate != null ? stringDate : currentAppointment.getDate() ), currentAppointment.getTime() ) );

        return convertView;
    }

    private class ViewHolder {

        private ImageView mAvatar;
        private TextView mAppointmentName;
        private TextView mAppointmentDate;

        public ViewHolder(View view) {
            mAvatar = view.findViewById(R.id.history_med_image);
            mAppointmentName = view.findViewById(R.id.history_med_name);
            mAppointmentDate = view.findViewById(R.id.broswe_app_date);
        }
    }
}
