package com.PrayerTimes;
import android.os.*;
import android.content.*;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public abstract class WakeLocker {
    private static WakeLock wakeLock;

    public static void acquire(Context ctx) {
		if (wakeLock != null) {
			wakeLock.release();
		}
		PowerManager powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "PRTM");
		wakeLock.acquire();
	}
	

    public static void release() {
        if (wakeLock != null) {
            wakeLock.release();
        }
        wakeLock = null;
    }
}
