package com.example.myalarm;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.myalarm.Model.Alarm;
import com.example.myalarm.Tasks.AlarmHelper;
import com.example.myalarm.Tasks.DatabaseHelper;
import com.example.myalarm.database.room.AlarmEntity;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class AlarmActivity extends AppCompatActivity {

    Button snooze;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Intent intent = getIntent();
        Long alarmId = intent.getLongExtra("ALARMID",0);
        if(alarmId==0)
            Toast.makeText(getApplicationContext(),"Got o",Toast.LENGTH_LONG).show();
        snooze = findViewById(R.id.snooze);
        cancel = findViewById(R.id.cancel);
        snooze.setOnClickListener(view -> {

        });

        cancel.setOnClickListener(view -> {
            try {
                AlarmEntity alarmEntity= new DatabaseHelper(this).getAlarmById(alarmId);
                Gson gson = new Gson();
                Alarm alarm = gson.fromJson(alarmEntity.getAlarmData(),Alarm.class);
                alarm.setActive(false);
                alarmEntity.setAlarmData(gson.toJson(alarm));
                new DatabaseHelper(getApplicationContext()).updateAlarm(alarmEntity.alarmId,alarm);
                new AlarmHelper(getApplicationContext()).cancelAlarm(alarm,alarmId);
                finish();


            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
