package com.PrayerTimes;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import com.libraryInfoMoon.*;
import android.content.res.*;
import android.graphics.*;

public class Qaaba extends Activity
{
	private GestureDetector gestureDetector;
	private double bearing;
    TextView compHeadingText,compcity;
    private ImageView compass,front;
    private float currentDegree = 0.0f;
    private ImageView qiblaPointer;
    private   float degree ;
	TextView moonset,sunset;
	QiblaCompassView compassView;
    // يمكنك تعريف قيم خط العرض والطول يدويًا
   // private double userLatitude = 27.2880813; // خط العرض الخاص بالمستخدم
   // private double userLongitude = 30.8995305; // خط الطول الخاص بالمستخدم
    private double qiblaLatitude = 21.42252; // خط العرض لمكة
    private double qiblaLongitude = 39.82621;
	private PrefsInterface ps;

	private String fontName;
	 // خط الطول لمكة
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qaaba);
		//خط النشاط
		fontName = "fonts/".concat("abbuline".concat(".otf")); 
		overrideFonts(this,getWindow().getDecorView());
		/////
		onConfigurationChanged(new Configuration());
		ps=new PrefsInterface(this);
		moonset=findViewById(R.id.moonset);
		sunset=findViewById(R.id.sunset);
		compcity=findViewById(R.id.comp_city);
		try{
		compassView = new QiblaCompassView(this);
		compassView = (QiblaCompassView) findViewById(R.id.img1);
		Display display = ((WindowManager) getSystemService(this.WINDOW_SERVICE))
			.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		compassView.setScreenResolution(width, height-3*height/7);
		updateWithNewLocation() ;

		double timezone =   ((TimeZone.getDefault().getOffset(new Date().getTime()) / 1000) / 60) / 60;

		//double longitude =30.9404816, latitude = 27.2739913,timezone1=2; int temperature=10, pressure=1010;//ANKARA position
        LunarPosition lunar = new LunarPosition();
		SolarPosition solar = new SolarPosition();
		double[] SPA = new double[3];


		GregorianCalendar c = new GregorianCalendar();
        double jd1= AstroLib.calculateJulianDay(c);
		//c=m(jd1);
		solar.calculateSunRiseTransitSet(SPA, jd1); 
		
			moonset.setText("Date" + AstroLib.fromJulianToCalendarStr(jd1));
			double []dd= lunar.calculateMoonRiseTransitSet(jd1,ps.getLat(), ps.getLon(), ps.getTimeZone(),10, 1010, 0);
			moonset.setText(""+lunar.calculateMoonRiseTransitSet(jd1, ps.getLat(), ps.getLon(), ps.getTimeZone(),10, 1010, 0));
			//moonset.setText("MoonRise"+AstroLib.getStringHHMMSSS(dd[0])+" "+"MoonTransit"+AstroLib.getStringHHMMSSS(dd[1])+" "+"MoonSet"+AstroLib.getStringHHMMSSS(dd[2])+"\n"+"SunRise"+AstroLib.getStringHHMMSSS( SPA[1])+" "+"SunTransit"+AstroLib.getStringHHMMSSS( SPA[0])+" "+"SunSet"+AstroLib.getStringHHMMSSS( SPA[2]));
			//moonset.setText("طلوع القمر :"+AstroLib.getStringHHMMSSS(dd[0])+"\n"+"القمر العابر :"+AstroLib.getStringHHMMSSS(dd[1])+"\n"+"غروب القمر :"+AstroLib.getStringHHMMSSS(dd[2]));
			//sunset.setText("شروق الشمس :"+AstroLib.getStringHHMMSSS( SPA[1])+"\n"+"ذروة الشمس :"+AstroLib.getStringHHMMSSS( SPA[0])+"\n"+"غروب الشمس :"+AstroLib.getStringHHMMSSS( SPA[2]));
			moonset.setText(getString(R.string.moonrise)+AstroLib.getStringHHMMSSS(dd[0])+"\n"+getString(R.string.moontransit)+AstroLib.getStringHHMMSSS(dd[1])+"\n"+getString(R.string.moonset)+AstroLib.getStringHHMMSSS(dd[2]));
			sunset.setText(getString(R.string.Sunrise)+AstroLib.getStringHHMMSSS( SPA[1])+"\n"+getString(R.string.Suntransit)+AstroLib.getStringHHMMSSS( SPA[0])+"\n"+getString(R.string.Sunset)+AstroLib.getStringHHMMSSS( SPA[2]));
			setShadow(moonset);
			setShadow(sunset);
		
		
			//للحصول علي خط الطول والعرض باستخدام الموقع للشبكة
			//bearing = getBearing();
			bearing = calculateAngle(ps.getLat(), ps.getLon(), qiblaLatitude, qiblaLongitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
		front=(ImageView)findViewById(R.id.front);
        compass = (ImageView) findViewById(R.id.imgViewCompass);
        qiblaPointer = (ImageView) findViewById(R.id.qibla_arrow);
        qiblaPointer.setRotation((float) bearing);
		front.setRotation((float)bearing);

        compHeadingText = (TextView) findViewById(R.id.comp_heading);
		
		if (android.os.Build. VERSION.SDK_INT > 4) {
			try{
            gestureDetector = SwypeManager.AddSwypeHandler(Qaaba.this, Moon.class, MainActivity.class);
        }catch(Exception e){
			e.printStackTrace();
			}
		compcity.setText(getString(R.string.city) + ps.getCityName()+ " \n " + getString(R.string.angleKaaba) + Float.toString((float) Math.floor(bearing)) + "\u00b0");
		compHeadingText.setText(getString(R.string.direction)+formatBearing(bearing)+" \n "+getString(R.string.distance)+distFrom(ps.getLat(),ps.getLon(),21.42252, 39.82621)+" "+getString(R.string.kielmeter));
		//setShadow(compHeadingText);
		//setShadow(compcity);
		}}
		

    // يمكنك إضافة دالة لحساب الزاوية بناءً على خطوط العرض والطول
    private double calculateAngle(double userLat, double userLong, double qiblaLat, double qiblaLong) {
        double userBearing = Math.toDegrees(Math.atan2(Math.sin(Math.toRadians(qiblaLong - userLong)) * Math.cos(Math.toRadians(qiblaLat)),
													   Math.cos(Math.toRadians(userLat)) * Math.sin(Math.toRadians(qiblaLat)) - Math.sin(Math.toRadians(userLat)) * Math.cos(Math.toRadians(qiblaLat)) * Math.cos(Math.toRadians(qiblaLong - userLong))));

        return (userBearing + 360) % 360;
    }
	private String formatBearing(double bearing) {
		if (bearing < 0 && bearing > -180) {
			// Normalize to [0,360]
			bearing = 360.0 + bearing;
		}
		if (bearing > 360 || bearing < -180) {
			return "Unknown";
		}

		/*String directions[] = {
			"N\t"+"الشَّمَالُ", "NNE\t"+"بَيْنَ الشَّمَالِ وَالشَّمَالِ الشَّرْقِيِّ", "NE\t"+"الشَّمَالُ الشَّرْقِيُّ", "ENE\t"+"بَيْنَ الشَّرْقِ وَالشَّمَالِ الشَّرْقِيِّ", "E\t"+"الشَّرْقُ", "ESE\t"+"بَيْنَ الشَّرْقِ وَالْجَنُوبِ الشَّرْقِيِّ", "SE\t"+"الْجَنُوبُ الشَّرْقِيُّ", "SSE\t"+"بَيْنَ الْجَنُوبِ وَالْجَنُوبِ الشَّرْقِيِّ",
			"S\t"+"الْجَنُوبُ", "SSW\t"+"بَيْنَ الْجَنُوبِ وَالْجَنُوبِ الْغَرْبِيِّ", "SW\t"+"الْجَنُوبُ الْغَرْبِيُّ", "WSW\t"+"بَيْنَ الْغَرْبِ وَالْجَنُوبِ الْغَرْبِيِّ", "W\t"+"الْغَرْبَ", "WNW\t"+"بَيْنَ الْغَرْبِ وَالشَّمَالِ الْغَرْبِيِّ", "NW\t"+"الْجَنُوبُ الْغَرْبِيُّ", "NNW\t"+"بَيْنَ الشَّمَالِ وَالشَّمَالِ الْغَرْبِيِّ",
		};*/
		String cardinal = getResources().getStringArray(R.array.directions)[(int) Math.floor(((bearing + 11.25) % 360) / 22.5)];


		return cardinal;
	}
	//  المسافة للمكة
	//المسافة بين نقطين باسخدام خط الطول والعرض
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; //meters 6378 6371000
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
			Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		float dist = (float) (earthRadius * c);

		return dist;


	
    }
	public boolean onTouchEvent(MotionEvent event) {
        if (android.os.Build. VERSION.SDK_INT <= 4 || !this.gestureDetector.onTouchEvent(event)) {
            return false;
        }
        return true;
    }
	private void updateWithNewLocation() {

		compassView.setLongitude(ps.getLon());
		compassView.setLatitude(ps.getLat());
		compassView.initCompassView();
		compassView.invalidate();

	}
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(1);
    }
	private void overrideFonts(final android.content.Context context, final View v) {

		try {
			Typeface  
				typeace = Typeface.createFromAsset(getAssets(), fontName);
			if ((v instanceof ViewGroup)) { 
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
					 i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				} 
			} 
			else { 
				if ((v instanceof TextView)) { 
					((TextView) v).setTypeface(typeace); 
				} 
				else { 
					if ((v instanceof EditText )) { 
						((EditText) v).setTypeface(typeace); 
					} 
					else { 
						if ((v instanceof Button)) { 
							((Button) v).setTypeface(typeace);
						} 
					} 
				} 
			} 
		} 
		catch(Exception e)

		{ 
			Toast.makeText(getApplicationContext(),"no file font",Toast.LENGTH_LONG).show();
		}
}
//ظل للنص

	public void setShadow(TextView tview){
		tview.setShadowLayer((float)3,(float)3,(float)3,Color.BLACK);
	}
}
