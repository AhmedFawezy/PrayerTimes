package com.PrayerTimes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Notification.Builder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.graphics.*;
import android.net.*;

public class AlarmReceiver extends BroadcastReceiver {
    boolean[] adhanChecks = new boolean[5];
    private Intent adhanServiceIntent;
    boolean isAdhanEnabled = false;
    boolean isNotifsEnabled = false;
	//private String[] prayerNames = new String[]{"وقت صلاة الفجر", "وقت صلاة الظهر", "وقت صلاة العصر", "وقت صلاة المغرب", "وقت صلاة العشاء"};
   // private String[] prayerNames = new String[]{"Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"};
    private SimpleDateFormat sdfDateFormat = new SimpleDateFormat("hh:mm a");
    SharedPreferences sp1;
    SharedPreferences sp2;
    SharedPreferences sp3;
    SharedPreferences sp4;
    SharedPreferences sp5;
    SharedPreferences spAdhan;
    SharedPreferences spNotifs;
	PrefsInterface ps;
    public void setChecks(Context context) {
        sp1 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        adhanChecks[0] = sp1.getBoolean("myFajrAdhanOpt", false);
        sp2 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        adhanChecks[1] = sp2.getBoolean("myDhuhrAdhanOpt", false);
        sp3 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        adhanChecks[2] = sp3.getBoolean("myAsrAdhanOpt", false);
        sp4 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        adhanChecks[3] = sp4.getBoolean("myMaghribAdhanOpt", true);
        sp5 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        adhanChecks[4] = sp5.getBoolean("myIshaAdhanOpt", false);
        spNotifs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        isNotifsEnabled = spNotifs.getBoolean("myNotifsOpt", false);
        spAdhan = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        isAdhanEnabled = spAdhan.getBoolean("myAdhanOpt", false);
    }

    public void addNotification(Context context, String prayerName) {
		ps = new PrefsInterface(context);
		Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.abdulb);
		long[] pattern = {0, 1000, 500, 1000}; // Vibration pattern: wait 0ms, vibrate for 1000ms, wait 500ms, vibrate for 1000ms
		
        Builder builder = (Builder) new Builder(context).setSmallIcon(R.drawable.pray2).setContentTitle(context.getString(R.string.now_time)).setColor(Color.parseColor("#6F1CDF")).setContentText(new StringBuilder(String.valueOf(prayerName)).append(" ").append(sdfDateFormat.format(Calendar.getInstance().getTime())).toString()+"\n\n"+context.getString(R.string.my_city)+"\n\n"+ps.getCityName());
		builder.setAutoCancel(true)/*.setSound(soundUri)*/;
		//.setVibrate(pattern)
		

        builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
        ((NotificationManager) context.getSystemService("notification")).notify(0, builder.build());
    }
	

    public void onReceive(Context context, Intent intent) {
		//WakeLocker.acquire(context);
		//WakefulIntentService.acquireStaticLock(context);
        setChecks(context);
		
        adhanServiceIntent = new Intent(context, AdhanService.class).putExtras(intent);
		int requestCode = intent.getExtras().getInt("salatIntent");
        if (isNotifsEnabled) {
            addNotification(context,context.getResources().getStringArray(R.array.azans) [requestCode]);
        }
        if (isAdhanEnabled && adhanChecks[requestCode]) {
            context.startService(adhanServiceIntent);
			adhanServiceIntent.putExtra("salatIntent",requestCode);
        }
		//WakeLocker.release();
    }
}
