package com.PrayerTimes;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class UpdateWidgetReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        RemoteViews rvs = new RemoteViews(context.getPackageName(), R.xml.widget1_info);
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, PrayTimesWidget.class), rvs);
    }
}

