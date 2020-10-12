package com.example.myalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.myalarm.Model.Alarm;
import com.example.myalarm.Tasks.AlarmHelper;
import com.example.myalarm.Tasks.DatabaseHelper;
import com.example.myalarm.database.room.AlarmEntity;
import com.example.myalarm.utiltiy.DaysOfWeek;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent alarmIntent = new Intent(this, AlarmActivity.class);
        Long alarmId = intent.getLongExtra("ALARMID",0);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("ALARMID",alarmId);
        AlarmEntity alarmEntity = null;
        Alarm alarm = null;
        try {
            alarmEntity =   new DatabaseHelper(getApplicationContext()).getAlarmById(alarmId);
            if(alarmEntity!=null) {
                Gson gson = new Gson();
                alarm = gson.fromJson(alarmEntity.getAlarmData(), Alarm.class);
                if (alarm.getRepeatType() == 0)
                    alarm.setActive(false);
                alarmEntity.setAlarmData(gson.toJson(alarm));
                new DatabaseHelper(getApplicationContext()).updateAlarm(alarmEntity.alarmId, alarm);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if(alarm!=null)
        {

            if(alarm.getRepeatType()==0 || alarm.getRepeatType()==1)
                startActivity(alarmIntent);


            else if(alarm.getRepeatType()==2)
            {
                Calendar now = Calendar.getInstance();
                if(now.get(Calendar.DAY_OF_WEEK)!=1 && now.get(Calendar.DAY_OF_WEEK)!=7);
                {
                    //1 means sunday and 7 means saturday
                   startActivity(alarmIntent);
                }


            }
            else if(alarm.getRepeatType()==3)
            {
                Calendar now = Calendar.getInstance();
                Boolean isToday = alarm.getRepeatCustom().get(DaysOfWeek.values()[now.get(Calendar.DAY_OF_WEEK)].toString().toLowerCase());
                if(isToday!=null)
                {
                    if(isToday)
                        startActivity(alarmIntent);

                }
                else
                    try {
                        throw new Exception("Developer exception of getting day of week");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }

        }
        else
        {
            try {
                throw new Exception("Developer Exception of alarm Id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this,"service is done",Toast.LENGTH_LONG).show();
        stopSelf();
        return START_REDELIVER_INTENT;

    }
}
