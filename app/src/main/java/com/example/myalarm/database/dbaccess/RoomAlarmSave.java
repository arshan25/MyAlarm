package com.example.myalarm.database.dbaccess;

import android.app.AlarmManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myalarm.database.room.AlarmEntity;
import com.example.myalarm.database.room.AlarmEntityDao;
import com.example.myalarm.database.room.AlarmEntityRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomAlarmSave implements StoreAlarm {

    private AlarmEntityDao alarmEntityDao;
    private AlarmEntityRoomDatabase alarmEntityDb;

    public RoomAlarmSave(Context context) {
        alarmEntityDb = AlarmEntityRoomDatabase.getDatabase(context);
        alarmEntityDao = alarmEntityDb.alarmEntityDao();
    }

    public Long insert(AlarmEntity alarmEntity) throws ExecutionException, InterruptedException {
      return  new InsertAsyncTask(alarmEntityDao).execute(alarmEntity).get();
    }

    public List<AlarmEntity> getAllAlarms() throws ExecutionException, InterruptedException {
        return new GetAllAlarmsAsynTask(alarmEntityDao).execute().get();
    }

    public int update(AlarmEntity... alarmEntities) throws ExecutionException, InterruptedException {
        return new UpdateAlarmsAsyncTask(alarmEntityDao).execute(alarmEntities).get();
    }


    public AlarmEntity getAlarmById(Long id) throws ExecutionException, InterruptedException {

         List<AlarmEntity> alarm =new GetAlarmByIdAsyncTask(alarmEntityDao).execute(id).get();
         if(alarm.isEmpty())
             return null;
         else if(alarm.size() > 2)
             return null;
         else
             return alarm.get(0);
    }

    private class InsertAsyncTask extends AsyncTask<AlarmEntity,Void,Long> {

        AlarmEntityDao alarmEntityDao;

        public InsertAsyncTask(AlarmEntityDao alarmEntityDao){
           this.alarmEntityDao = alarmEntityDao;
        }
        @Override
        protected Long doInBackground(AlarmEntity... alarmEntities) {
           return alarmEntityDao.insert(alarmEntities[0]);


        }
    }

    private class GetAllAlarmsAsynTask extends AsyncTask<Void,Void,List<AlarmEntity>> {
        AlarmEntityDao alarmEntityDao;

        public GetAllAlarmsAsynTask(AlarmEntityDao alarmEntityDao){
            this.alarmEntityDao = alarmEntityDao;
        }
        @Override
        protected List<AlarmEntity> doInBackground(Void... voids) {
            return alarmEntityDao.getAllAlarms();
        }
    }

    private class UpdateAlarmsAsyncTask extends AsyncTask<AlarmEntity,Void,Integer> {
        AlarmEntityDao alarmEntityDao;
        public UpdateAlarmsAsyncTask(AlarmEntityDao alarmEntityDao) {
            this.alarmEntityDao = alarmEntityDao;
        }

        @Override
        protected Integer doInBackground(AlarmEntity... alarmEntities) {
            return alarmEntityDao.updateUsers(alarmEntities);

        }
    }

    private class GetAlarmByIdAsyncTask extends AsyncTask<Long,Void,List<AlarmEntity>> {
        AlarmEntityDao alarmEntityDao;

        public GetAlarmByIdAsyncTask(AlarmEntityDao alarmEntityDao) {
        this.alarmEntityDao = alarmEntityDao;
        }


        @Override
        protected List<AlarmEntity> doInBackground(Long... longs) {
            return alarmEntityDao.getAlarmById(longs[0]);
        }
    }
}
