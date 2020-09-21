package com.example.myalarm.database.room;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.myalarm.database.room.AlarmEntity;

@Dao
public interface AlarmEntityDao {

    @Insert
    void insert(AlarmEntity alarmEntity);
}
