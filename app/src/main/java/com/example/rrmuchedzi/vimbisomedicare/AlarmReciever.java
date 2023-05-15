package com.example.rrmuchedzi.vimbisomedicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {

    private static final String TAG = "AlarmReciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Called back");
        Log.d(TAG, "========= ALARM RINGING, ALARM RINGING =========");
        Toast.makeText(context, "Don't panik but your time is up!!!!.",
                Toast.LENGTH_LONG).show();
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
//        ringtone.play();

        new HomeActivity().showNotification("Ma Notifaction Acho");

        Log.d(TAG, "onReceive: Called end");
    }

    public AlarmReciever() {
        super();
    }
}
