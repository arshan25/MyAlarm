package com.example.myalarm.database;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlarmEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "AlarmId")
    public Integer alarmId;

    @NonNull
    @ColumnInfo(name = "AlarmData")
    public String AlarmData;

}
