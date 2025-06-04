package com.PrayerTimes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import java.util.Calendar;

public class AlarmService extends Service {
    AlarmReceiver acvr;
    Intent alarmIntent;
    Intent alarmIntent2;
    Intent alarmIntent3;
    Intent alarmIntent4;
    Intent alarmIntent5;
    Calendar[] alarmTimings = new Calendar[5];
    AlarmManager almgr1;
    AlarmManager almgr2;
    AlarmManager almgr3;
    AlarmManager almgr4;
    AlarmManager almgr5;
    Gson gson;
    IBinder mBinder;
    PendingIntent pendingIntent;
    PendingIntent pendingIntent2;
    PendingIntent pendingIntent3;
    PendingIntent pendingIntent4;
    PendingIntent pendingIntent5;

    protected void onHandleIntent(Intent intent) {
        Calendar[] timings = new Calendar[5];
        Object[] tempObjects = new Object[5];
        if (intent != null) {
            try {
                timings = (Calendar[]) Arrays.copyOf((Calendar[]) intent.getSerializableExtra("alarmTimesArray"), 5, Calendar[].class);
                saveTimings(timings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (intent == null) {
            SharedPreferences spEditor = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            try {
                String string = spEditor.getString("mySavedTimingsArr", "");
                if (string.isEmpty()) {
                    System.out.println("Error getting calendar timings array from disk.");
                } else {
                    timings = (Calendar[]) gson.fromJson(string, new TypeToken<Calendar[]>() {
														 }.getType());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        acvr = new AlarmReceiver();
        almgr1 = (AlarmManager) getSystemService("alarm");
        almgr2 = (AlarmManager) getSystemService("alarm");
        almgr3 = (AlarmManager) getSystemService("alarm");
        almgr4 = (AlarmManager) getSystemService("alarm");
        almgr5 = (AlarmManager) getSystemService("alarm");
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 134217728);
        pendingIntent2 = PendingIntent.getBroadcast(this, 1, alarmIntent2, 134217728);
        pendingIntent3 = PendingIntent.getBroadcast(this, 2, alarmIntent3, 134217728);
        pendingIntent4 = PendingIntent.getBroadcast(this, 3, alarmIntent4, 134217728);
        pendingIntent5 = PendingIntent.getBroadcast(this, 4, alarmIntent5, 134217728);
        setTimes(timings);
    }

    private void setAlarms() {
        if (alarmTimings[0] != null && alarmTimings[0].getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            almgr1.setExactAndAllowWhileIdle(0, alarmTimings[0].getTimeInMillis(), pendingIntent);
        }
        if (alarmTimings[1] != null && alarmTimings[1].getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            almgr2.setExact(0, alarmTimings[1].getTimeInMillis(), pendingIntent2);
        }
        if (alarmTimings[2] != null && alarmTimings[2].getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            almgr3.setExact(0, alarmTimings[2].getTimeInMillis(), pendingIntent3);
        }
        if (alarmTimings[3] != null && alarmTimings[3].getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            almgr4.setExact(0, alarmTimings[3].getTimeInMillis(), pendingIntent4);
        }
        if (alarmTimings[4] != null && alarmTimings[4].getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            almgr5.setExact(0, alarmTimings[4].getTimeInMillis(), pendingIntent5);
        }
        if (alarmTimings[4] == null || alarmTimings[4].getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            stopSelf();
        }
    }

    public void onCreate() {
        super.onCreate();
        alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("salatIntent", 0);
        alarmIntent2 = new Intent(this, AlarmReceiver.class);
        alarmIntent2.putExtra("salatIntent", 1);
        alarmIntent3 = new Intent(this, AlarmReceiver.class);
        alarmIntent3.putExtra("salatIntent", 2);
        alarmIntent4 = new Intent(this, AlarmReceiver.class);
        alarmIntent4.putExtra("salatIntent", 3);
        alarmIntent5 = new Intent(this, AlarmReceiver.class);
        alarmIntent5.putExtra("salatIntent", 4);
    }

    private void saveTimings(Calendar[] calendarArr) {
        Editor sp = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            sp.putString("mySavedTimingsArr", new Gson().toJson(calendarArr));
            sp.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimes(Calendar[] calendarArr) {
        for (int i = 0; i < 5; i++) {
            alarmTimings[i] = calendarArr[i];
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        onHandleIntent(intent);
        setAlarms();
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
