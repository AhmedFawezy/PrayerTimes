package com.PrayerTimes;



import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
public class SwypeManager extends SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 5;
    private Activity context;
    private Class<?> nextActivity;
    private Class<?> prevActivity;

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Intent intent;

        // Swipe to the left
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            intent = new Intent(this.context, this.nextActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.context.startActivity(intent);
            this.context.overridePendingTransition(R.anim.leftenter, R.anim.leftleave);
            this.context.finish();
            return true;
        }
        // Swipe to the right
        else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            intent = new Intent(this.context, this.prevActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
            this.context.overridePendingTransition(R.anim.rightenter, R.anim.rightleave);
            this.context.finish();
            return true;
        }

        return false;
    }

    public static GestureDetector AddSwypeHandler(Activity activity, Class<?> next, Class<?> prev) {
        SwypeManager event = new SwypeManager();
        event.context = activity;
        event.nextActivity = next;
        event.prevActivity = prev;
        return new GestureDetector(event);
    }
}
