package com.PrayerTimes;



import android.content.Context;
import android.graphics.Canvas;
import  android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.Random;
import android.util.*;
import android.view.*;
import android.widget.*;

public class StarFieldView extends View {

	private static final int NUM_STARS = 100;

	private final int screenWidth;
	private final int screenHeight;

	private final Paint paint;
	private final Star[] stars;

	private Random random;

	public StarFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Get the screen dimensions
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

		// Create a new paint object for drawing the stars
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);

		// Create a new random number generator
		random = new Random();

		// Create an array of star objects
		stars = new Star[NUM_STARS];
		for (int i = 0; i < NUM_STARS; i++) {
			stars[i] = new Star(random.nextInt(screenWidth), random.nextInt(screenHeight), random.nextInt(256));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//// Clear the canvas
		//canvas.drawColor(Color.BLACK);

		// Draw the stars
		for (int i = 0; i < NUM_STARS; i++) {
			Star star = stars[i];
			//paint.setColor(Color.WHITE);
			paint.setColor(0xFFFFFFFF);
			//paint.setColor(Color.rgb(star.brightness, star.brightness, star.brightness));
			canvas.drawCircle(star.x, star.y, star.size, paint);

			// Update the star position and size
			star.x -= star.speed;
			star.size -= 0.1f;
			star.brightness -= 1;

			// If the star is off the screen, reset it to a new position and size
			if (star.x < -star.size) {
				star.x = screenWidth + random.nextInt(screenWidth);
				star.y = random.nextInt(screenHeight);
				star.size = random.nextInt(5) + 1;
				star.brightness = 255;
			} else if (star.size <= 0) {
				star.x = random.nextInt(screenWidth);
				star.y = random.nextInt(screenHeight);
				star.size = random.nextInt(5) + 1;
				star.brightness = 255;
			}
		}

		// Schedule a redraw of the view
		postInvalidateOnAnimation();
	}

	private static class Star {
		public int x;
		public int y;
		public int size;
		public int brightness;
		public int speed;

		public Star(int x, int y, int brightness) {
			this.x = x;
			this.y = y;
			this.brightness = brightness;
			size = 5;
			speed = 3;
		}
	}
}

