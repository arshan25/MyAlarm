package com.example.myalarm.Tasks;

import android.content.Context;
import android.widget.Toast;


import com.example.myalarm.AlarmActivity;
import com.example.myalarm.Model.Alarm;
import com.example.myalarm.database.dbaccess.RoomAlarmSave;
import com.example.myalarm.database.room.AlarmEntity;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DatabaseHelper {

    private RoomAlarmSave roomOperations;
    private Context context;
    public DatabaseHelper(Context context) {
        this.context=context;
        roomOperations = new RoomAlarmSave(context);
    }


    public List<AlarmEntity> getAllAlarms() throws ExecutionException, InterruptedException {
        return roomOperations.getAllAlarms();
    }

    public AlarmEntity getAlarmById(Long id) throws ExecutionException, InterruptedException {
        return roomOperations.getAlarmById(id);
    }


    public  int updateAlarm(Long id,Alarm  alarm) throws ExecutionException, InterruptedException {
        AlarmEntity updateAlarmEntity = new AlarmEntity(new Gson().toJson(alarm));
        updateAlarmEntity.setAlarmId(id);
        return roomOperations.update(updateAlarmEntity);
    }

    public  Long createAlarm(Alarm alarm) throws ExecutionException, InterruptedException {
        Toast.makeText(context,"Alarm set Successfully",Toast.LENGTH_LONG).show();
        return roomOperations.insert(new AlarmEntity(new Gson().toJson(alarm)));

    }
}
