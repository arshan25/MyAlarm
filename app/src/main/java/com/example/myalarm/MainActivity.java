package com.example.myalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import com.example.myalarm.Model.Alarm;
import com.example.myalarm.Tasks.FlutterToNativeTasks;


import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {

    Button button;


    FlutterEngine flutterEngine;

    public static final String CHANNEL = "com.example.arshan";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




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
                                    FlutterToNativeTasks.CreateAlarmTask(alarmData, getApplicationContext());
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
                                    FlutterToNativeTasks.cancelAlarmTask(alarmData, getApplicationContext());
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(call.method.equals("restart"))
                            {
                                Map<String, Object> alarmData = call.arguments();

                                try {
                                    FlutterToNativeTasks.restartAlarmTask(alarmData, getApplicationContext());
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                            else
                            {
                                Log.d("MainAcitvity","platform call");
                            }


                        }

                );

    }
}