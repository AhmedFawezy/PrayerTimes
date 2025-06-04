package com.PrayerTimes;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public abstract class WakefulIntentService extends IntentService {
    public static final String LOCK_NAME_STATIC = "com.commonsware.android.syssvc.AppService.Static";
    private static WakeLock lockStatic = null;

    abstract void doWakefulWork(Intent intent);

    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    private static synchronized WakeLock getLock(Context context) {
        WakeLock wakeLock;
        synchronized (WakefulIntentService.class) {
            if (lockStatic == null) {
                lockStatic = ((PowerManager) context.getSystemService("power")).newWakeLock(1, LOCK_NAME_STATIC);
                lockStatic.setReferenceCounted(true);
            }
            wakeLock = lockStatic;
        }
        return wakeLock;
    }

    public WakefulIntentService(String name) {
        super(name);
    }

    protected final void onHandleIntent(Intent intent) {
        try {
            doWakefulWork(intent);
        } finally {
            getLock(this).release();
        }
    }
}
