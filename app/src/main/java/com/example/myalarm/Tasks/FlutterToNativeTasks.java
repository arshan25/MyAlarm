package com.example.myalarm.Tasks;


import android.content.Context;

import com.example.myalarm.Model.Alarm;

import com.example.myalarm.database.room.AlarmEntity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;



public  class FlutterToNativeTasks {


    public static void CreateAlarmTask(Map<String, Object> alarmData, Context context) throws ExecutionException, InterruptedException {
        Alarm alarm = new Alarm(alarmData);
        Long id = new DatabaseHelper(context).createAlarm(alarm); //creating alarm;
        new DatabaseHelper(context).updateAlarm(id,new AlarmHelper(context).setAlarmTask(alarm,id));
    }

    public static  void cancelAlarmTask(Map<String, Object> alarmData,Context context) throws ExecutionException, InterruptedException {

        Alarm alarm = new Alarm((Map<String, Object>) alarmData.get("alarm"));
        Class a  =alarmData.get("id").getClass();
        Long id = null;
        if(a.equals(Integer.class))
            id = ((Integer)alarmData.get("id")).longValue();
        else
          id = (Long)alarmData.get("id");
       new DatabaseHelper(context).updateAlarm(id,new AlarmHelper(context).cancelAlarm(alarm,id));
    }

    public static void restartAlarmTask(Map<String, Object> alarmData,Context context) throws ExecutionException, InterruptedException {

        Alarm alarm = new Alarm((Map<String, Object>) alarmData.get("alarm"));
        Class a  =alarmData.get("id").getClass();
        Long id = null;
        if(a.equals(Integer.class))
            id = ((Integer)alarmData.get("id")).longValue();
        else
            id = (Long)alarmData.get("id");

        new DatabaseHelper(context).updateAlarm(id,new AlarmHelper(context).setAlarmTask(alarm,id));
    }



    public static Map<Long,Map<String,Object>> getAllAlarms(Context context) throws ExecutionException, InterruptedException {

        List<AlarmEntity> allAlarms = new DatabaseHelper(context).getAllAlarms();

        Map<Long,Map<String,Object>> alarms = new HashMap<>();

        if(allAlarms.isEmpty())
            return null;
        for (AlarmEntity alarm: allAlarms) {
            alarms.put(alarm.getAlarmId(),Alarm.toMap(new Gson().fromJson(alarm.getAlarmData(),Alarm.class)));
        }

        return alarms;
    }

}
