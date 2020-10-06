package com.example.myalarm.Tasks;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.example.myalarm.Model.Alarm;
import com.example.myalarm.MyReceiver;


import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmHelper {

    Context context;
    public AlarmHelper(Context context){
       this.context = context;
    }

    public Alarm setAlarmTask(Alarm alarm,Long id){

        Calendar  userAlarm = alarm.getAlarmTime();
        Calendar now = Calendar.getInstance();

        if(userAlarm.before(now) || userAlarm.equals(now)) {
            int hour = userAlarm.get(Calendar.HOUR);
            int minute = userAlarm.get(Calendar.MINUTE);
            userAlarm = now;
            userAlarm.set(Calendar.HOUR,hour);
            userAlarm.set(Calendar.MINUTE,minute);
            userAlarm.set(Calendar.DAY_OF_MONTH,now.get(Calendar.DAY_OF_MONTH));
            userAlarm.add(Calendar.DAY_OF_MONTH,1);
            alarm.setAlarmTime(userAlarm);
        }
        Intent alarmIntent = new Intent(context, MyReceiver.class);
        alarmIntent.putExtra("ALARMID",id);
        int requestCode = Integer.parseInt(userAlarm.get(Calendar.HOUR)+""+userAlarm.get(Calendar.MINUTE));
        PendingIntent pd = PendingIntent.getBroadcast(context,requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        setAlarm(pd, alarm.getRepeatType() !=0 ,userAlarm.getTimeInMillis());
        return alarm;

    }



    public Alarm cancelAlarm(Alarm alarm,Long id){

        Intent alarmIntent = new Intent(context, MyReceiver.class);
        alarmIntent.putExtra("ALARMID",id);
        int requestCode = Integer.parseInt(alarm.getAlarmTime().get(Calendar.HOUR)+""+alarm.getAlarmTime().get(Calendar.MINUTE));
        PendingIntent pd = PendingIntent.getBroadcast(context,requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pd);
        return alarm;

    }





    public void setAlarm(PendingIntent pd,Boolean repeat,long startTime)
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if(repeat) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime+1000,24*60*60*1000, pd);
        }
        else
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,startTime, pd);
            //alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(setAlarmCalendar.getTimeInMillis(), Miuipending), pd);// working fine
        } else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,startTime, pd);

    }

}
