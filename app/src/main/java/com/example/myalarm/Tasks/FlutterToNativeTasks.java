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


    public static Map<Long,Map<String,Object>> CreateAlarmTask(Map<String, Object> alarmData, Context context) throws ExecutionException, InterruptedException {
        Alarm alarm = new Alarm(alarmData);
        Long id = new DatabaseHelper(context).createAlarm(alarm); //creating alarm;
        Alarm updatedAlarm = new AlarmHelper(context).setAlarmTask(alarm,id);
        new DatabaseHelper(context).updateAlarm(id,updatedAlarm);
        Map<Long,Map<String,Object>> returnMap = new HashMap<>();
        returnMap.put(id,Alarm.toMap(updatedAlarm));
        return returnMap;
    }

    public static  Map<String,Object> cancelAlarmTask(Map<String, Object> alarmData,Context context) throws ExecutionException, InterruptedException {

        Alarm alarm = new Alarm((Map<String, Object>) alarmData.get("alarm"));
        Class a  =alarmData.get("id").getClass();
        Long id;
        if(a.equals(Integer.class))
            id = ((Integer)alarmData.get("id")).longValue();
        else
          id = (Long)alarmData.get("id");
        Alarm updatedAlarm = new AlarmHelper(context).cancelAlarm(alarm,id);
       new DatabaseHelper(context).updateAlarm(id,updatedAlarm);
       return Alarm.toMap(updatedAlarm);
    }

    public static Map<String,Object> restartAlarmTask(Map<String, Object> alarmData,Context context) throws ExecutionException, InterruptedException {

        Alarm alarm = new Alarm((Map<String, Object>) alarmData.get("alarm"));
        Class a  =alarmData.get("id").getClass();
        Long id;
        if(a.equals(Integer.class))
            id = ((Integer)alarmData.get("id")).longValue();
        else
            id = (Long)alarmData.get("id");
        Alarm updatedAlarm = new AlarmHelper(context).setAlarmTask(alarm,id);
        new DatabaseHelper(context).updateAlarm(id,updatedAlarm);

        return Alarm.toMap(updatedAlarm);
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

    public static void deleteAllAlarms(Context context,List<Integer> alarmId)
    {

        new DatabaseHelper(context).deleteAlarms(alarmId);
    }

}
