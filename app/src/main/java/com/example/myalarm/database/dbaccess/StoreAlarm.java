package com.example.myalarm.database.dbaccess;

import com.example.myalarm.database.room.AlarmEntity;

import java.util.concurrent.ExecutionException;

public interface StoreAlarm {

    Long insert(AlarmEntity alarmEntity) throws ExecutionException, InterruptedException;
}
