package com.example.myalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myalarm.Model.Alarm;
import com.example.myalarm.Tasks.FlutterToNativeTasks;
import com.example.myalarm.Tasks.Ringtone;


import java.nio.channels.Channel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {

    public static final String CHANNEL = "com.example.arshan";
    String ringtone = "unknown";
    MethodChannel methodChannel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
           ringtone =  Ringtone.setRingtone(data);
           new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL)
                   .invokeMethod("ringtoneSelected",ringtone);
        }
    }




    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);


       new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            if (call.method.equals("save")) {
                                Map<String, Object> alarmData = call.arguments();
                                try {
                                    result.success(FlutterToNativeTasks.CreateAlarmTask(alarmData, getApplicationContext()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(call.method.equals("getAllAlarms")){
                                try {
                                    result.success(FlutterToNativeTasks.getAllAlarms(getApplicationContext()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(call.method.equals("cancel"))
                            {
                                Map<String, Object> alarmData = call.arguments();

                                try {
                                    result.success(FlutterToNativeTasks.cancelAlarmTask(alarmData, getApplicationContext()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(call.method.equals("restart"))
                            {
                                Map<String, Object> alarmData = call.arguments();

                                try {
                                    result.success(FlutterToNativeTasks.restartAlarmTask(alarmData, getApplicationContext()));
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                            else if(call.method.equals("getRingtone"))
                            {
                                areRingtoneAppsAvailable();
                                result.success(ringtone);
                            }
                            else if(call.method.equals("delete"))
                            {
                                List<Integer> alarmId = (List<Integer>)call.arguments;
                                FlutterToNativeTasks.deleteAllAlarms(getApplicationContext(),alarmId);
                            }
                            else
                            {
                                Log.d("MainAcitvity","platform call");
                            }


                        }

                );

    }

    public  void areRingtoneAppsAvailable()
    {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        PackageManager packageManager =getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if(isIntentSafe)
            startActivityForResult(intent,999);
        else
            Toast.makeText(getApplicationContext(),"No Ringtone Selector apps available in your phone",Toast.LENGTH_LONG);

    }
}