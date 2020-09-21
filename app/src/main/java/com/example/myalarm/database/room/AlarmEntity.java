package com.example.myalarm.database.room;



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
    public Integer getAlarmId() {
        return alarmId;
    }

    @NonNull
    public String getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(@NonNull String alarmData) {
        this.alarmData = alarmData;
    }

    public void setAlarmId(@NonNull Integer alarmId) {
        this.alarmId = alarmId;
    }

    @NonNull
    @ColumnInfo(name = "AlarmData")
    public String alarmData;



    public AlarmEntity(Integer alarmId, String alarmData){
        this.alarmId = alarmId;
        this.alarmData = alarmData;
    }



}
