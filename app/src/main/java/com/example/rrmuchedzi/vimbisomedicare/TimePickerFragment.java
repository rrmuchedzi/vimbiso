package com.example.rrmuchedzi.vimbisomedicare;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Rufaro R.Muchedzi on 12/7/2017.
 */

public class TimePickerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        return new TimePickerDialog( getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, true );
        //Todo, Implement past time restrictions on selections
    }
}
