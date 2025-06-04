package com.PrayerTimes;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.*;

public class CountDownService extends Service {
    Date asrTime;
    CountDownTime ctx;
    Date dhuhrTime;
    Date fajrTime;
    Date ishaTime;
    Date maghribTime;
    Date nextPrayerTime;
    Intent nextTextIntent;
    String prayerName;
    SimpleDateFormat sdfDateFormat = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
    SharedPreferences spManager;
    Date[] times = null;

    public class CountDownTime extends CountDownTimer {
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onTick(long millisUntilFinished) {
			try{
				String textString1 = getString(R.string.countdowntimenext)+" "+prayerName+" "+getString(R.string.countdowntimein);
				String textString = String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(millisUntilFinished / 3600000), Long.valueOf((millisUntilFinished / 60000) % 60), Long.valueOf((millisUntilFinished / 1000) % 60)});
				nextTextIntent = new Intent("update_next_text");
				nextTextIntent.putExtra("countdownTime", textString);
				nextTextIntent.putExtra("countdownprayerName",textString1);
				sendBroadcast(nextTextIntent);
			}
			catch(Exception exception){
				exception.printStackTrace();
			}
        }

        public void onFinish() {
            getNextPrayer();
        }
    }

    public void setTimes(Date[] arr) {
        fajrTime = arr[0];
        dhuhrTime = arr[1];
        asrTime = arr[2];
        maghribTime = arr[3];
        ishaTime = arr[4];
    }

    protected void onHandleIntent(Intent intent) {
        try {
            times = (Date[]) Arrays.copyOf((Object[]) intent.getSerializableExtra("timingsArray"), 5, Date[].class);
            setTimes(times);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        onHandleIntent(intent);
        getNextPrayer();
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startTicking() {
        Date nowDate = Calendar.getInstance().getTime();
        long difference = 0;
        if (nextPrayerTime != null) {
            difference = nextPrayerTime.getTime() - nowDate.getTime();
        }
        if (nextPrayerTime == null) {
            onDestroy();
        }
        if (difference > 0) {
            ctx = new CountDownTime(difference, 1000);
            ctx.start();
        }
    }

    public void getNextPrayer() {
        Date nowDate = Calendar.getInstance().getTime();
        if (nowDate.before(fajrTime)) {
            nextPrayerTime = fajrTime;
            prayerName = getString(R.string.countdowntimefajr);
        }
        if (nowDate.before(dhuhrTime) && nowDate.after(fajrTime)) {
            nextPrayerTime = dhuhrTime;
            prayerName = getString(R.string.countdowntimedohr);
        }
        if (nowDate.before(asrTime) && nowDate.after(dhuhrTime)) {
            nextPrayerTime = asrTime;
            prayerName = getString(R.string.countdowntimeasr);
        }
        if (nowDate.before(maghribTime) && nowDate.after(asrTime)) {
            nextPrayerTime = maghribTime;
            prayerName = getString(R.string.countdowntimemaghrib);
        }
        if (nowDate.before(ishaTime) && nowDate.after(maghribTime)) {
            nextPrayerTime = ishaTime;
            prayerName = getString(R.string.countdowntimeisha);
        }
        if (nowDate.after(ishaTime)) {
            prayerName = "";
            nextPrayerTime = null;
            stopSelf();
        }
        startTicking();
    }
}
