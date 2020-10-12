package com.example.myalarm.Model;


import com.example.myalarm.utiltiy.AlarmJsonContract;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Alarm {

    /// To store alarm time
     Calendar alarmTime;

    /// snooze time after how much time alarm should re occur default range 5 - 120 min
    Integer snoozeTime;



    public Calendar getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Integer getSnoozeTime() {
        return snoozeTime;
    }

    public void setSnoozeTime(Integer snoozeTime) {
        this.snoozeTime = snoozeTime;
    }

    public Boolean getVibrate() {
        return isVibrate;
    }

    public void setVibrate(Boolean vibrate) {
        isVibrate = vibrate;
    }

    public Map<String, Boolean> getRepeatCustom() {
        return repeatCustom;
    }

    public void setRepeatCustom(Map<String, Boolean> repeatCustom) {
        this.repeatCustom = repeatCustom;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /// whether to vibrate when alarm is ringing
    Boolean isVibrate;

    /// repeat on specified days or repeat after certain time
    Map<String, Boolean> repeatCustom;

    ///type of repeat once, everyday, mon to fri, custom,
    int repeatType;

    ///Reason why the alarm needed
    String reason;

    /// reason why alarm is active or not

    Boolean active;


    /// Ringtone path

    public String getRingtonePath() {
        return ringtonePath;
    }

    public void setRingtonePath(String ringtonePath) {
        this.ringtonePath = ringtonePath;
    }

    String ringtonePath;

    public Alarm() {
        /// setting default time right now
        alarmTime = Calendar.getInstance();

        /// setting default snooze time 5 min to 120 min
        snoozeTime = 5;

        ///setting default vibrate
        isVibrate = false;

        /// setting default repeat type
        repeatType = RepeatType.ONCE.ordinal();

        /// setting repeat custom
        repeatCustom = null;

        ///setting reason to null;
        reason = null;

        /// setting boolean to false;
        active = false;

        ///setting ringtone path to null
        ringtonePath = null;
    }

    public Alarm(Map<String, Object> alarmData) {
        /// setting default time right now
        alarmTime = Calendar.getInstance();
        try {

            Map<String, Integer> date = (Map<String,Integer>) alarmData.get(AlarmJsonContract.alarmDate);
            Map<String, Integer> time = ((Map<String,Integer>) alarmData.get(AlarmJsonContract.alarmTime));
            alarmTime.set(date.get(AlarmJsonContract.year), date.get(AlarmJsonContract.month)-1,
                    date.get(AlarmJsonContract.day), time.get(AlarmJsonContract.hour),
                    time.get(AlarmJsonContract.minute),0);

            /// setting default snooze time 5 min to 120 min
            snoozeTime = (Integer) alarmData.get(AlarmJsonContract.snoozeTime);

            ///setting default vibrate
            isVibrate = (Boolean) alarmData.get(AlarmJsonContract.isVibrate);

            /// setting default repeat type
            repeatType = (Integer) alarmData.get(AlarmJsonContract.repeatType);

            /// setting repeat custom
            repeatCustom = (Map) alarmData.get(AlarmJsonContract.repeatCustom);

            ///setting reason to null;
            reason = (String) alarmData.get(AlarmJsonContract.reason);

            ///setting active
            active = (Boolean) alarmData.get(AlarmJsonContract.active);

            /// setting ringtone path
            ringtonePath = (String)alarmData.get(AlarmJsonContract.ringtonePath);

        }catch (Exception e) {
          throw e;
        }
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmTime=" + alarmTime +
                ", snoozeTime=" + snoozeTime +
                ", isVibrate=" + isVibrate +
                ", repeatCustom=" + repeatCustom +
                ", repeatType=" + repeatType +
                ", active=" + active +
                ", reason='" + reason + '\'' +
                ", ringtonePath="+ringtonePath+
                '}';
    }

    public static Map<String, Object> toMap(Alarm alarm) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> time = new HashMap<>();
        Map<String, Object> date = new HashMap<>();
        time.put(AlarmJsonContract.hour, alarm.alarmTime.get(Calendar.HOUR_OF_DAY));
        time.put(AlarmJsonContract.minute, alarm.alarmTime.get(Calendar.MINUTE));
        date.put(AlarmJsonContract.year, alarm.alarmTime.get(Calendar.YEAR));
        date.put(AlarmJsonContract.month, alarm.alarmTime.get(Calendar.MONTH)+1);
        date.put(AlarmJsonContract.day, alarm.alarmTime.get(Calendar.DAY_OF_MONTH));
        map.put(AlarmJsonContract.alarmTime, time);
        map.put(AlarmJsonContract.alarmDate, date);
        map.put(AlarmJsonContract.snoozeTime, alarm.snoozeTime);
        map.put(AlarmJsonContract.reason, alarm.reason);
        map.put(AlarmJsonContract.repeatType, alarm.repeatType);
        map.put(AlarmJsonContract.repeatCustom, alarm.repeatCustom);
        map.put(AlarmJsonContract.isVibrate, alarm.isVibrate);
        map.put(AlarmJsonContract.active,alarm.active);
        map.put(AlarmJsonContract.ringtonePath,alarm.ringtonePath);

        return map;

    }


}


