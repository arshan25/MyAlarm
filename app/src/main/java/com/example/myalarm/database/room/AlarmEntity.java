package com.example.myalarm.database.room;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlarmEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "AlarmId")
    public Long alarmId;

    @NonNull
    public Long getAlarmId() {
        return alarmId;
    }

    @NonNull
    public String getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(@NonNull String alarmData) {
        this.alarmData = alarmData;
    }

    public void setAlarmId(@NonNull Long alarmId) {
        this.alarmId = alarmId;
    }

    @NonNull
    @ColumnInfo(name = "AlarmData")
    public String alarmData;


    public AlarmEntity(){}

    public AlarmEntity(String alarmData){
        this.alarmData = alarmData;
    }



}
