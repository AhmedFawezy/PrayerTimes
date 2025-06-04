package com.PrayerTimes;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeUpdaterReceiver extends BroadcastReceiver {
    Intent timeUpdaterServiceIntent;

    public void onReceive(Context context, Intent intent) {
        this.timeUpdaterServiceIntent = new Intent(context.getApplicationContext(), TimeUpdaterService.class);
        if (MainActivity.isMainScreenActive) {
            context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
        }
        if (isServiceRunning(TimeUpdaterService.class, context)) {
            context.stopService(this.timeUpdaterServiceIntent);
        }
        context.startService(this.timeUpdaterServiceIntent);
    }

    private boolean isServiceRunning(Class<?> serviceClass, Context context) {
        for (RunningServiceInfo service : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
