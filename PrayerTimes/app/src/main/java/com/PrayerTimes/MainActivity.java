package com.PrayerTimes;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.GestureDetector;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.animation.*;
import android.view.animation.*;
import android.graphics.drawable.*;
import android.text.*;
import android.content.res.*;
import android.widget.*;

public class MainActivity extends Activity {
    public static Context appContext;
    public static boolean isMainScreenActive = false;
    Builder aboutWindow;
    boolean adhanEnabled;
    AlarmManager am;
    TextView asr;
    TextView asrDesc;
	Date datehij;
    Date asrTime;
    private BroadcastReceiver bcsr = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
			
			try{
				
				String ss=intent.getExtras().getString("countdownTime");
				ss=ss.replaceAll("٠" , "0").replaceAll( "١" , "1")
					.replaceAll("٢" , "2").replaceAll("٣" , "3")
					.replaceAll("٤" , "4").replaceAll("٥" , "5")
					.replaceAll("٦" , "6").replaceAll("٧" , "7")
					.replaceAll("٨" , "8").replaceAll("٩" , "9");
				nextTextView.setText(ss);
				NextTextTime.setText(intent.getExtras().getString("countdownprayerName"));
				String texcolor=intent.getExtras().getString("countdownprayerName");
				if(texcolor.contains(context.getString(R.string.countdowntimefajr))){
					fajrDesc.setTextColor(Color.CYAN);
				}else if(texcolor.contains(context.getString(R.string.countdowntimedohr))){
					dhuhrDesc.setTextColor(Color.CYAN);
				} else if(texcolor.contains(context.getString( R.string.countdowntimeasr))){
					asrDesc.setTextColor(Color.CYAN);
				} else if(texcolor.contains(context.getString(R.string.countdowntimemaghrib))){
					maghribDesc.setTextColor(Color.CYAN);
				} else if(texcolor.contains(context.getString(R.string.countdowntimeisha))){
					ishaDesc.setTextColor(Color.CYAN);
				} else {
					
					fajrDesc.setTextColor(Color.CYAN);
					
					 
				}
				 
			}
			catch(Exception exception){
				exception.printStackTrace();
			}
			
	
			/*try{
			Locale locale = Locale.ENGLISH;
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss",Locale.ENGLISH);
			String formattedText = sdf.format(intent.getExtras().getString("countdownTime"));
			nextTextView.setText(formattedText);
			}catch(Exception e1){
				
			}*/
			
        }
    };
    TextView city;
    Intent countdownServiceIntent;
    TextView dateDesc;
    TextView dhuhr;
    TextView dhuhrDesc;
    Date dhuhrTime;
    SimpleDateFormat dtFormat;
    TextView fajr;
    TextView fajrDesc;
    Date fajrTime;
    TextView isha;
    TextView ishaDesc;
    Date ishaTime;
    TextView maghrib;
    TextView maghribDesc;
    Date maghribTime;
    Date nextPrayerTime;
    TextView nextTextView;
	TextView NextTextTime;
	TextView amsik;
	private GestureDetector gestureDetector;
	
    boolean notifsEnabled;
    PendingIntent pendingIntent;
    public PrayTimeInterface pi;
    SharedPreferences sp;
    SharedPreferences sp2;
    SharedPreferences sp3;
	PrefsInterface ps1;
    TextView sunrise;
    TextView sunriseDesc;
    Calendar tFive = Calendar.getInstance();
    Calendar tFour = Calendar.getInstance();
    Calendar tOne = Calendar.getInstance();
    Calendar tThree = Calendar.getInstance();
    Calendar tTwo = Calendar.getInstance();
    Date testerDate;
    Typeface tf;
    Intent timeUpdaterServiceIntent;
    Date[] timings = new Date[5];
    Intent updateAlarmIntent;

    protected void onCreate(Bundle savedInstanceState) {
		ChangeFontDefault.overrideFont(this, "SERIF", "fonts/abbuline.otf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		if (android.os.Build.VERSION.SDK_INT > 4) {
            gestureDetector = SwypeManager.AddSwypeHandler(MainActivity.this, Qaaba.class, Moon.class);
        }
    
		//تغيير لغة النشاط
		/*Locale englishLocale = new Locale("en");
		Locale.setDefault(englishLocale);
		Configuration config = new Configuration();
		config.locale = englishLocale;
		getResources().updateConfiguration(config, getResources().getDisplayMetrics());*/
		
        pi = new PrayTimeInterface(getApplicationContext());
        initTextViews();
        dtFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        sp = getSharedPreferences("com.PrayerTimes", 0);
        sp2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sp3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        updateAlarmIntent = new Intent(this, TimeUpdaterReceiver.class);
       countdownServiceIntent = new Intent(this, CountDownService.class);
        //tf = Typeface.createFromAsset(getAssets(), "fount_en2.ttf");
		tf.createFromAsset(getAssets(),"fonts/abbuline.otf");
        setTypefaces();
		animateTextWithBitmapShader(nextTextView,R.drawable.image_2);
		//nextTextView.getPaint().setStyle(Paint.Style.STROKE);
		
		//date hijri
		ps1=new PrefsInterface(this);
		
        TextView hdt = (TextView) findViewById(R.id.digitalClock1);
		//String[] hijri_months = new String[]{"محرم", "صفر", "ربيع اول", "ربيع اخر", "جماد اول", "جماد اخر", "رجب", "شعبان", "رمضان", "شوال", "ذو القعده", "ذو الحجه"};
		//String[] hijri_months = new String[]{"Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani", "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"};
		ModelDate hj=DateConversion.GregToIsl(new Date(),ps1.getHijriOffset());
		hdt.setText(new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime())+" "+hj.formatString(getResources().getStringArray(R.array.hijri_months))+getString(R.string.datehj));
		
	
       // hdt.setText(HijriCalendar.writeIslamicDate());
        hdt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        displayDate();
        setTextToTimes();
        setTimes();
		isTodayFriday();
        registerReceiver(bcsr, new IntentFilter("update_next_text"));
        if (pi.getCityObj() != null && Calendar.getInstance().getTimeInMillis() < tFive.getTimeInMillis()) {
            countdownServiceIntent.putExtra("timingsArray", timings);
            startService(countdownServiceIntent);
        }
        adhanEnabled = sp2.getBoolean("myAdhanOpt", false);
        notifsEnabled = sp3.getBoolean("myNotifsOpt", false);
        boolean isRunning = isServiceRunning(AlarmService.class);
        boolean doneForTheDay = false;
        if (pi.getCityObj() != null) {
            doneForTheDay = Calendar.getInstance().getTimeInMillis() >= tFive.getTimeInMillis();
        }
        if (!isRunning && (adhanEnabled || (notifsEnabled && !doneForTheDay))) {
            timeUpdaterServiceIntent = new Intent(this, TimeUpdaterService.class);
            startService(timeUpdaterServiceIntent);
        }
        am = (AlarmManager) getSystemService("alarm");
        if (pi.getCityObj() != null) {
            scheduleUpdate();
			 
        }
       // AppRater.app_launched(this);
        isMainScreenActive = true;
    }

    public void onStart() {
        super.onStart();
        appContext = getApplicationContext();
    }

    public static Context getMainContext() {
        return appContext;
    }

    public void onStop() {
		
        isMainScreenActive = false;
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
        if (sp.getBoolean("firstrun", true)) {
            Builder firstRunDialog = new Builder(this);
            firstRunDialog.setTitle(getString(R.string.dialogtitle));
            TextView alertTextView = new TextView(this);
			alertTextView.setTextColor(Color.BLACK);
            alertTextView.setText(getString(R.string.dialogtxt));
            firstRunDialog.setView(alertTextView);
			firstRunDialog.setCancelable(false);
            firstRunDialog.setPositiveButton(getString(R.string.settings), new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(MainActivity.this, SettingsActivity.class));
					}
				});
            firstRunDialog.show();
            sp.edit().putBoolean("firstrun", false).commit();
        }
        onCreate(null);
    }

    public void onDestroy() {
        unregisterReceiver(bcsr);
        if (pi.getCityObj() != null) {
            stopService(countdownServiceIntent);
        }
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
           startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id != R.id.about) {
            return super.onOptionsItemSelected(item);
        } else {
            showAboutDialog();
            return true;
        }}
	public boolean onTouchEvent(MotionEvent event) {
        if (android.os.Build.VERSION.SDK_INT <= 4 || !this.gestureDetector.onTouchEvent(event)) {
            return false;
        }
        return true;


	}
    

    public void goToSettings(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

     
    public void showAboutDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this,5).create();
		LayoutInflater inflater = getLayoutInflater();

		View convertView = (View) inflater.inflate(R.layout.custom_dialog, null);
		dialog.setView(convertView);
		dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.parseColor("#00000000")));
		convertView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
		TextView txt1 = (TextView)
			convertView.findViewById(R.id.textview1);//on custome_dialog
		txt1.setText(getString(R.string.dialoginfo));
        txt1.setAutoLinkMask(-1);
        txt1.setTextColor(android.graphics.Color.parseColor("#8C8700"));
        txt1.setTextSize(16.0f);
		txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 1);
		//txt1.setTextIsSelectable(true);
		txt1.setLineSpacing(1,1.2f);

        txt1.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        android.text.util.Linkify.addLinks(txt1, 1);
        //dialog.setIcon(R.drawable.afg1);
        //dialog.setTitle("About");
		Button btn1 = (Button) convertView.findViewById(R.id.button1);//on custome_dialog
		btn1.setBackground(getDrawable(R.drawable.bgbutton));
		btn1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 1);
		//convertView.setBackgroundColor(Color.parseColor("#FAF3D6"));
		FrameLayout fl=(FrameLayout)convertView.findViewById(R.id.mainFrameLayout1);
		LinearLayout lnn=(LinearLayout)convertView.findViewById(R.id.linear);

		bgview(fl);
		btn1.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					dialog.dismiss();


				}
			});

		dialog.show();

    }
	public void bgview(View vv){
		int[] colorsCRNKC = { Color.parseColor("#00000000"), Color.parseColor("#00000000") }; android.graphics.drawable.GradientDrawable CRNKC = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNKC);
		CRNKC.setCornerRadii(new float[]{(int)360,(int)360,(int)360,(int)360,(int)360,(int)360,(int)360,(int)360});
		CRNKC.setStroke((int) 3, Color.parseColor("#8C8700"));
		vv.setElevation((float) 5);
		vv.setBackground(CRNKC);
	}
    public void displayDate() {
        dateDesc.setText(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
    }

    public void initTextViews() {
        city = (TextView) findViewById(R.id.CityText);
        fajr = (TextView) findViewById(R.id.fajrTime);
        sunrise = (TextView) findViewById(R.id.sunriseTime);
        dhuhr = (TextView) findViewById(R.id.dhuhrTime);
        asr = (TextView) findViewById(R.id.asrTime);
        maghrib = (TextView) findViewById(R.id.maghribTime);
        isha = (TextView) findViewById(R.id.ishaTime);
        nextTextView = (TextView) findViewById(R.id.NextText);
		NextTextTime=(TextView)findViewById(R.id.NextTextTime);
        dateDesc = (TextView) findViewById(R.id.dateTextView);
        fajrDesc = (TextView) findViewById(R.id.FajrText);
        sunriseDesc = (TextView) findViewById(R.id.SunriseText);
        dhuhrDesc = (TextView) findViewById(R.id.DhuhrText);
        asrDesc = (TextView) findViewById(R.id.AsrText);
        maghribDesc = (TextView) findViewById(R.id.MaghribText);
        ishaDesc = (TextView) findViewById(R.id.IshaText);
		amsik=(TextView)findViewById(R.id.amsik);
		
    }

   public void setTextToTimes() {
		
        city.setText(pi.getCity());
        fajr.setText(pi.getFajr());
        sunrise.setText(pi.getSunrise());
        dhuhr.setText(pi.getDhuhr());
        asr.setText(pi.getAsr());
        maghrib.setText(pi.getMaghrib());
        isha.setText(pi.getIsha());
	   
		//الامساك في رمضان
	   try{
		    
		   // استرجاع القيمة المدخلة 
		   String inputTime = fajr.getText().toString();
		   // تحديد تنسيق الوقت
		   java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a",Locale.ENGLISH);
		   java.util.Date date1 = null;
		   try {
			   date1 = sdf.parse(inputTime);
			   // ناقص 25 دقيقة بالميللي ثانية
			   date1.setTime(date1.getTime() - (25 * 60 * 1000)); 
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		   // تنسيق الوقت النهائي

		   String formattedTime = sdf.format(date1);
		   // عرض الوقت النهائي في TextView
		   amsik.setText(formattedTime.replaceAll("AM",getString(R.string.amsik)));
	   }

	   catch(Exception e){
		   e.printStackTrace();
	   }
	   
    }
	// التحقق إذا كان اليوم الحالي هو يوم الجمعة 
	public void isTodayFriday(){
		 
		 dhuhrDesc = findViewById(R.id.DhuhrText);
		String textToShow = getString(R.string.dhuhr___);
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.FRIDAY) {
			textToShow = getString(R.string.friday);
		}
		dhuhrDesc.setText(textToShow);

		
	}
    private void setTypefaces() {
       // city.setTypeface(tf);
        fajr.setTypeface(tf);
        sunrise.setTypeface(tf);
        dhuhr.setTypeface(tf);
        asr.setTypeface(tf);
        maghrib.setTypeface(tf);
        isha.setTypeface(tf);
        
        fajrDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        sunriseDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        dhuhrDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        asrDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        maghribDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
        ishaDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
		nextTextView.setTypeface(tf);
		NextTextTime.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
		//dateDesc.setTypeface(tf);
		nextTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/digital1.ttf"), 0);
		dateDesc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
		city.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/abbuline.otf"), 0);
		 
		
		
    }

    private void scheduleUpdate() {
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 7, updateAlarmIntent, 134217728);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(10, 0);
        calendar.set(9, 0);
        calendar.add(5, 1);
        am.setRepeating(1, calendar.getTimeInMillis(), 86400000, pendingIntent);
    }

    private void setTimes() {
        Date cd = Calendar.getInstance().getTime();
        if (pi.getCityObj() != null) {
            try {
                fajrTime = dtFormat.parse(pi.getFajr());
                fajrTime.setYear(cd.getYear());
                fajrTime.setMonth(cd.getMonth());
                fajrTime.setDate(cd.getDate());
                dhuhrTime = dtFormat.parse(pi.getDhuhr());
                dhuhrTime.setYear(cd.getYear());
                dhuhrTime.setMonth(cd.getMonth());
                dhuhrTime.setDate(cd.getDate());
                asrTime = dtFormat.parse(pi.getAsr());
                asrTime.setYear(cd.getYear());
                asrTime.setMonth(cd.getMonth());
                asrTime.setDate(cd.getDate());
                maghribTime = dtFormat.parse(pi.getMaghrib());
                maghribTime.setYear(cd.getYear());
                maghribTime.setMonth(cd.getMonth());
                maghribTime.setDate(cd.getDate());
                ishaTime = dtFormat.parse(pi.getIsha());
                ishaTime.setYear(cd.getYear());
                ishaTime.setMonth(cd.getMonth());
                ishaTime.setDate(cd.getDate());
                tOne.setTime(fajrTime);
                tTwo.setTime(dhuhrTime);
                tThree.setTime(asrTime);
                tFour.setTime(maghribTime);
                tFive.setTime(ishaTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timings[0] = fajrTime;
            timings[1] = dhuhrTime;
            timings[2] = asrTime;
            timings[3] = maghribTime;
            timings[4] = ishaTime;
        }
    }

    private void checkIfArabicLocale() {
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        for (RunningServiceInfo service : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
	//دالة تحريك الماسك داخل النص
	private void animateTextWithBitmapShader(final TextView textView, int drawableRes) {



		// تحميل الصورة وتحويلها إلى Bitmap
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableRes);
		// إنشاء BitmapShader
		BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		// تعيين Shader للنص داخل TextView
		textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		textView.getPaint().setShader(shader);
		// تحريك النص باستخدام ObjectAnimator
		ObjectAnimator animator = ObjectAnimator.ofFloat(this, "shaderOffset", 0, bitmap.getWidth());
		animator.setDuration(2000);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// Method to update the shader offset on each animation frame
					float shaderOffset = (float) animation.getAnimatedValue();
					Matrix matrix = new Matrix();
					matrix.setTranslate(shaderOffset, 0);
					textView.getPaint().getShader().setLocalMatrix(matrix);
					textView.invalidate();
					
				}
			});
		animator.start();

	}
	
		
	
	
}
