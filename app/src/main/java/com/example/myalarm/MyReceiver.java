package com.example.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Intent alarmIntent = new Intent(context,MyService.class);
        Long alarmId = intent.getLongExtra("ALARMID",0);
        alarmIntent.putExtra("ALARMID",alarmId);

            context.startService(alarmIntent);

    }
}
