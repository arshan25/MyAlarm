package com.example.myalarm.database.dbaccess;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myalarm.database.room.AlarmEntity;
import com.example.myalarm.database.room.AlarmEntityDao;
import com.example.myalarm.database.room.AlarmEntityRoomDatabase;

public class RoomAlarmSave implements StoreAlarm {

    private AlarmEntityDao alarmEntityDao;
    private AlarmEntityRoomDatabase alarmEntityDb;

    public RoomAlarmSave(Context context) {
        alarmEntityDb = AlarmEntityRoomDatabase.getDatabase(context);
        alarmEntityDao = alarmEntityDb.alarmEntityDao();
    }

    public void insert(AlarmEntity alarmEntity){
        new InsertAsyncTask(alarmEntityDao).execute(alarmEntity);
    }

    private class InsertAsyncTask extends AsyncTask<AlarmEntity,Void,Void> {

        AlarmEntityDao alarmEntityDao;

        public InsertAsyncTask(AlarmEntityDao alarmEntityDao){
           this.alarmEntityDao = alarmEntityDao;
        }
        @Override
        protected Void doInBackground(AlarmEntity... alarmEntities) {
            alarmEntityDao.insert(alarmEntities[0]);
            Log.d("arshan","arshan");
            return null;
        }
    }
}
