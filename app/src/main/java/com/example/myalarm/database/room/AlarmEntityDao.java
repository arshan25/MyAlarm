package com.example.myalarm.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myalarm.database.room.AlarmEntity;

import java.util.List;

@Dao
public interface AlarmEntityDao {

    @Insert
    Long insert(AlarmEntity alarmEntity);

    @Query("SELECT * FROM AlarmEntity")
    List<AlarmEntity>  getAllAlarms();

    @Query("SELECT * FROM AlarmEntity WHERE alarmId = :alarmId")
    List<AlarmEntity> getAlarmById(Long alarmId);

    @Update
    int updateUsers(AlarmEntity... alarmEntities);



}
