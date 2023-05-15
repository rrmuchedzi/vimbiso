package com.example.rrmuchedzi.vimbisomedicare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class DoctorsListViewAdapter extends ArrayAdapter {

    private List<DoctorObject> mDoctorObjectList;
    private final int layoutResource;
    private final LayoutInflater mLayoutInflater;
    private int position;

    public DoctorsListViewAdapter(Context context, int resource, List<DoctorObject> dataProvider) {
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

        DoctorObject currentDoctor = mDoctorObjectList.get(position);

        switch ( currentDoctor.getAvatar() ) {
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

        viewHolder.mDoctorName.setText( currentDoctor.getName() );
        viewHolder.mDoctorSpeciality.setText( currentDoctor.getSpeciality() );

        return convertView;
    }

    private class ViewHolder {

        private ImageView mAvatar;
        private TextView mDoctorName;
        private TextView mDoctorSpeciality;

        public ViewHolder(View view) {
            mAvatar = view.findViewById(R.id.form_doctor_entry_change_avatar);
            mDoctorName = view.findViewById(R.id.doctor_entry_name);
            mDoctorSpeciality = view.findViewById(R.id.doctor_entry_speciality);
        }
    }
}
