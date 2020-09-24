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

    /// whether to vibrate when alarm is ringing
    Boolean isVibrate;

    /// repeat on specified days or repeat after certain time
    Map<String, Boolean> repeatCustom;

    ///type of repeat once, everyday, mon to fri, custom,
    int repeatType;

    ///Reason why the alarm needed
    String reason;

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

        ///setting resason to null;
        reason = null;
    }

    public Alarm(Map<String, Object> alarmData) {
        /// setting default time right now
        alarmTime = Calendar.getInstance();
        try {

            Map<String, Integer> date = (Map) alarmData.get(AlarmJsonContract.alarmDate);
            Map<String, Integer> time = ((Map) alarmData.get(AlarmJsonContract.alarmTime));
            alarmTime.set(date.get(AlarmJsonContract.year), date.get(AlarmJsonContract.month),
                    date.get(AlarmJsonContract.year), time.get(AlarmJsonContract.hour),
                    time.get(AlarmJsonContract.minute));

            /// setting default snooze time 5 min to 120 min
            snoozeTime = (Integer) alarmData.get(AlarmJsonContract.snoozeTime);

            ///setting default vibrate
            isVibrate = (Boolean) alarmData.get(AlarmJsonContract.isVibrate);

            /// setting default repeat type
            repeatType = (Integer) alarmData.get(AlarmJsonContract.repeatType);

            /// setting repeat custom
            repeatCustom = (Map) alarmData.get(AlarmJsonContract.repeatCustom);

            ///setting resason to null;
            reason = (String) alarmData.get(AlarmJsonContract.reason);
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
                ", reason='" + reason + '\'' +
                '}';
    }

    public static Map<String, Object> toMap(Alarm alarm) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> time = new HashMap<>();
        Map<String, Object> date = new HashMap<>();
        time.put(AlarmJsonContract.hour, alarm.alarmTime.get(Calendar.HOUR));
        time.put(AlarmJsonContract.minute, alarm.alarmTime.get(Calendar.MINUTE));
        date.put(AlarmJsonContract.year, alarm.alarmTime.get(Calendar.YEAR));
        date.put(AlarmJsonContract.month, alarm.alarmTime.get(Calendar.MONTH));
        date.put(AlarmJsonContract.day, alarm.alarmTime.get(Calendar.DAY_OF_MONTH));
        map.put(AlarmJsonContract.alarmTime, time);
        map.put(AlarmJsonContract.alarmDate, date);
        map.put(AlarmJsonContract.snoozeTime, alarm.snoozeTime);
        map.put(AlarmJsonContract.reason, alarm.reason);
        map.put(AlarmJsonContract.repeatType, alarm.repeatType);
        map.put(AlarmJsonContract.repeatCustom, alarm.repeatCustom);
        map.put(AlarmJsonContract.isVibrate, alarm.isVibrate);

        return map;

    }


}


