package com.PrayerTimes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrayTimesWidget extends AppWidgetProvider {

    private PendingIntent pendingIntent = null;
    PrayTimeInterface pti;
	

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        pti = new PrayTimeInterface(context);
        schedule(context);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget1);
			Date d=new Date();
			java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("yyyy");
			int y=Integer.parseInt(dt.format(new Date()));
			CopticDate c=new CopticDate();
			String s=c.convertToEg(context, d,y);
			// التحقق إذا كان اليوم الحالي هو يوم الجمعة 
			String textToShow = context.getString(R.string.dhuhr___);
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == Calendar.FRIDAY) {
				textToShow = context.getString(R.string.friday);
			}
			//String[] hijri_months = new String[]{"محرم", "صفر", "ربيع اول", "ربيع اخر", "جماد اول", "جماد اخر", "رجب", "شعبان", "رمضان", "شوال", "ذو القعده", "ذو الحجه"};
			PrefsInterface ps1=new PrefsInterface(context);
			ModelDate hj=DateConversion.GregToIsl(new Date(), ps1.getHijriOffset());
			String hij=(new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime()) + " " + hj.formatString(context.getResources().getStringArray(R.array.hijri_months)) +" "+ context.getString(R.string.datehj));
			
            rvs.setTextViewText(R.id.widgetDateDesc, new StringBuilder(String.valueOf(new SimpleDateFormat("EEE- dd - MMM - yyyy").format(Calendar.getInstance().getTime()))).append("    "+"\n").append(hij).append("\n").append(String.valueOf(new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime()))+" "+s+" "+context.getString(R.string.dateco).toString()));
			rvs.setTextViewText(R.id.DhuhrDesc,textToShow);

			// rvs.setTextViewText(R.id.widgetDateDesc, new StringBuilder(String.valueOf(new SimpleDateFormat("dd - MMM - yyyy").format(Calendar.getInstance().getTime()))).append("    ").append(HijriCalendar.writeIslamicDate().toString()));
            rvs.setTextViewText(R.id.wFajrTime, pti.getFajr());
            rvs.setTextViewText(R.id.wDhuhrTime, pti.getDhuhr());
            rvs.setTextViewText(R.id.wAsrTime, pti.getAsr());
            rvs.setTextViewText(R.id.wMaghribTime, pti.getMaghrib());
            rvs.setTextViewText(R.id.wIshaTime, pti.getIsha());
            rvs.setOnClickPendingIntent(R.id.mainWidgetLinLayout, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));
            appWidgetManager.updateAppWidget(appWidgetId, rvs);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public void schedule(Context context) {
        Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        pendingIntent = PendingIntent.getService(context, 0, new Intent(context.getApplicationContext(), UpdateWidgetReceiver.class), 0);
        alarmManager.setRepeating(1, calendar.getTimeInMillis(), 86400000, pendingIntent);
    }
}

