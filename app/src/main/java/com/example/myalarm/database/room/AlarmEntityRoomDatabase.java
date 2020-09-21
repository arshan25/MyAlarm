package com.example.myalarm.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AlarmEntity.class},version = 1)
public abstract  class AlarmEntityRoomDatabase extends RoomDatabase {
    public abstract AlarmEntityDao alarmEntityDao();

    private static volatile AlarmEntityRoomDatabase alarmRoomDb;

    public static AlarmEntityRoomDatabase getDatabase(final Context context) {
        if (alarmRoomDb == null) {
            synchronized (AlarmEntityRoomDatabase.class) {
                if (alarmRoomDb == null) {
                    alarmRoomDb  = Room.databaseBuilder(context,
                            AlarmEntityRoomDatabase.class, "Alarm_Database")
                            .build();
                }
            }
        }
        return alarmRoomDb;
    }
}
