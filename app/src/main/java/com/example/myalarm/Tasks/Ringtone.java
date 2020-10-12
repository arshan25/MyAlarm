package com.example.myalarm.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class Ringtone {

     static Activity activity = new Activity();



    public static String setRingtone(Intent userSelectedRingtone)
    {

        Uri uri = userSelectedRingtone.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (uri != null) {
            return uri.getPath();
        } else {
            return "Default";
        }
    }



}
