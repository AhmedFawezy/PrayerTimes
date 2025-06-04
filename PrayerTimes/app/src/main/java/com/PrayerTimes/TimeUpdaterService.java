package com.PrayerTimes;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUpdaterService extends Service {
    Intent alarmServiceIntent;
    Calendar[] alarmTimesArray = new Calendar[5];
    Date asrTime;
    Date dhuhrTime;
    SimpleDateFormat dtFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    Date fajrTime;
    Date ishaTime;
    IBinder mBinder;
    Date maghribTime;
    Date nextPrayerTime;
    PrayTimeInterface pi;
    Calendar tFive = Calendar.getInstance();
    Calendar tFour = Calendar.getInstance();
    Calendar tOne = Calendar.getInstance();
    Calendar tThree = Calendar.getInstance();
    Calendar tTwo = Calendar.getInstance();
    Date[] timings;

    private void setTimes() {
        this.pi = new PrayTimeInterface(getApplicationContext());
        Date cd = Calendar.getInstance().getTime();
        if (this.pi.getCityObj() != null) {
            try {
                this.fajrTime = this.dtFormat.parse(this.pi.getFajr());
                this.fajrTime.setYear(cd.getYear());
                this.fajrTime.setMonth(cd.getMonth());
                this.fajrTime.setDate(cd.getDate());
                this.dhuhrTime = this.dtFormat.parse(this.pi.getDhuhr());
                this.dhuhrTime.setYear(cd.getYear());
                this.dhuhrTime.setMonth(cd.getMonth());
                this.dhuhrTime.setDate(cd.getDate());
                this.asrTime = this.dtFormat.parse(this.pi.getAsr());
                this.asrTime.setYear(cd.getYear());
                this.asrTime.setMonth(cd.getMonth());
                this.asrTime.setDate(cd.getDate());
                this.maghribTime = this.dtFormat.parse(this.pi.getMaghrib());
                this.maghribTime.setYear(cd.getYear());
                this.maghribTime.setMonth(cd.getMonth());
                this.maghribTime.setDate(cd.getDate());
                this.ishaTime = this.dtFormat.parse(this.pi.getIsha());
                this.ishaTime.setYear(cd.getYear());
                this.ishaTime.setMonth(cd.getMonth());
                this.ishaTime.setDate(cd.getDate());
                this.tOne.setTime(this.fajrTime);
                this.tTwo.setTime(this.dhuhrTime);
                this.tThree.setTime(this.asrTime);
                this.tFour.setTime(this.maghribTime);
                this.tFive.setTime(this.ishaTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.timings = new Date[]{this.fajrTime, this.dhuhrTime, this.asrTime, this.maghribTime, this.ishaTime};
        }
    }

    private void setDailyAlarms() {
        this.alarmTimesArray[0] = this.tOne;
        this.alarmTimesArray[1] = this.tTwo;
        this.alarmTimesArray[2] = this.tThree;
        this.alarmTimesArray[3] = this.tFour;
        this.alarmTimesArray[4] = this.tFive;
        this.alarmServiceIntent.putExtra("alarmTimesArray", this.alarmTimesArray);
        startService(this.alarmServiceIntent);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        setTimes();
        setDailyAlarms();
        return 1;
    }

    public void onCreate() {
        super.onCreate();
        this.alarmServiceIntent = new Intent(this, AlarmService.class);
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    protected void onHandleIntent(Intent intent) {
    }
}
