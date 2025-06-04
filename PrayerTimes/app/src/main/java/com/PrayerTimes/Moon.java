package com.PrayerTimes;
import android.app.*;
import android.view.*;
import android.view.View.*;
import android.os.*;
import android.widget.*;
import java.text.*;
import java.util.*;
import android.graphics.*;
import com.libraryInfoMoon.*;
public class Moon extends  Activity 
{
	TextView txthj,datem,datek,textview2,textview3,textview4,textview5;
	ImageView imageview1;
	
	private GestureDetector gestureDetector;
    private OnTouchListener gestureListener;
	private float motionStartX;
    private float motionStartY;
	private long motionTime;
	public static final int[] moons = new int[]{R.drawable.moon1, R.drawable.moon2, R.drawable.moon3, R.drawable.moon4, R.drawable.moon5, R.drawable.moon6, R.drawable.moon7, R.drawable.moon8, R.drawable.moon9, R.drawable.moon10, R.drawable.moon11, R.drawable.moon12, R.drawable.moon13, R.drawable.moon14, R.drawable.moon15};
//public static final String []moon_phases={"New moon","Waxing crescent moon","First quarter moon","Waxing gibbous moon","Full moon","Waning gibbous moon","Third quarter moon","Waning crescent moon","Dark moon"};
	//public static final String []moon_phases={"محاق","هلال","تربيع أول","أحدب متزايد","بدر","أحدب متناقص","تربيع ثاني","هلال آخر الشهر ","القمر المظلم"};

	private PrefsInterface psf;

	private String fontName;
	

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {setTitle("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moon);
		//خط النشاط
		fontName = "fonts/".concat("abbuline".concat(".otf")); 
		overrideFonts(this,getWindow().getDecorView());
		/////
		psf=new PrefsInterface(this);
		imageview1=findViewById(R.id.imageview1);

        TextView hdt = (TextView) findViewById(R.id.dhj);
		textview2=findViewById(R.id.textview2);
		textview3=findViewById(R.id.textview3);
		textview4=findViewById(R.id.textview4);
		textview5=findViewById(R.id.textview5);
		datem=findViewById(R.id.datem);
		datek=findViewById(R.id.datek);
		
		//String[] hijri_months = new String[]{"محرم", "صفر", "ربيع اول", "ربيع اخر", "جماد اول", "جماد اخر", "رجب", "شعبان", "رمضان", "شوال", "ذو القعده", "ذو الحجه"};
		//String[] hijri_months = new String[]{"Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani", "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"};
		try{
		ModelDate hj=DateConversion.GregToIsl(new Date(),psf.getHijriOffset());
			hdt.setText(new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime())+" "+hj.formatString(getResources().getStringArray(R.array.hijri_months))+" "+getString(R.string.datehj));
		//التاريخ الميلادي
		java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("EEE d MMM yyyy");
		datem.setText(dt.format(new Date())+" "+getString(R.string.dategr));
		
		
		//القبطي
		Date de=new Date();
		java.text.SimpleDateFormat dt1 = new java.text.SimpleDateFormat("yyyy");
		int y=Integer.parseInt(dt1.format(new Date()));
		CopticDate cd=new CopticDate();
		String s=cd.convertToEg(this,de,y);
		datek.setText(s+" "+getString(R.string.dateco));
		setShadow(datek);
		setShadow(datem);
		setShadow(hdt);
		imageview1.setImageResource(moons[(hj.getDay() - 1) / 2]);
		textview2.setText(getString(R.string.moonphases)+getResources().getStringArray(R.array.moon_phases)[DateConversion.moonPhase(Calendar.getInstance().getTime())]+"\n"+String.format(getString(R.string.moonIllumination)+"%.1f",(DateConversion.getIllumination(DateConversion.getJulian(Calendar.getInstance().getTime())))));
		textview3.setText(getString(R.string.agemoon)+DateConversion.getAge(Calendar.getInstance().getTime())+"\n"+String.format(getString(R.string.julian)+"%.2f",(DateConversion.getJulian(Calendar.getInstance().getTime()))));
		textview2.setTextSize(2,14f);
		textview3.setTextSize(2,14f);
		textview4.setTextSize(2,14f);
		textview5.setTextSize(2,14f);
		LunarPosition lunar = new LunarPosition();
		SolarPosition solar = new SolarPosition();
		double[] SPA = new double[3];


		GregorianCalendar c = new GregorianCalendar();
        double jd1= AstroLib.calculateJulianDay(c);
		//c=m(jd1);
		solar.calculateSunRiseTransitSet(SPA, jd1); 
		
			textview4.setText("Date" + AstroLib.fromJulianToCalendarStr(jd1));
			double []dd= lunar.calculateMoonRiseTransitSet(jd1,psf.getLat(), psf.getLon(), psf.getTimeZone(),10, 1010, 0);
			textview4.setText(""+lunar.calculateMoonRiseTransitSet(jd1, psf.getLat(), psf.getLon(), psf.getTimeZone(),10, 1010, 0));
			//textview4.setText("MoonRise"+AstroLib.getStringHHMMSSS(dd[0])+"\n"+"MoonTransit"+AstroLib.getStringHHMMSSS(dd[1])+"\n"+"MoonSet"+AstroLib.getStringHHMMSSS(dd[2])+"\n"+"SunRise"+AstroLib.getStringHHMMSSS( SPA[1])+"\n"+"SunTransit"+AstroLib.getStringHHMMSSS( SPA[0])+"\n"+"SunSet"+AstroLib.getStringHHMMSSS( SPA[2]));
			//textview4.setText("طلوع القمر :"+AstroLib.getStringHHMMSSS(dd[0])+"\n"+"القمر العابر :"+AstroLib.getStringHHMMSSS(dd[1])+"\n"+"غروب القمز :"+AstroLib.getStringHHMMSSS(dd[2])+"\n"+"شروق الشمس :"+AstroLib.getStringHHMMSSS( SPA[1])+"\n"+"ذروة الشمس :"+AstroLib.getStringHHMMSSS( SPA[0])+"\n"+"غروب الشمس :"+AstroLib.getStringHHMMSSS( SPA[2]));
			textview4.setText(getString(R.string.moonrise)+AstroLib.getStringHHMMSSS(dd[0])+"\n"+getString(R.string.moontransit)+AstroLib.getStringHHMMSSS(dd[1])+"\n"+getString(R.string.moonset)+AstroLib.getStringHHMMSSS(dd[2]));
			textview5.setText(getString(R.string.Sunrise)+AstroLib.getStringHHMMSSS( SPA[1])+"\n"+getString(R.string.Suntransit)+AstroLib.getStringHHMMSSS( SPA[0])+"\n"+getString(R.string.Sunset)+AstroLib.getStringHHMMSSS( SPA[2]));
			setShadow(textview4);
			setShadow(textview5);
			
		//imageview1.setRotation(149598000-(float)getNewMoon(getJulian((calendar.getTime()))));
		if (android.os.Build. VERSION.SDK_INT > 4) {
			try{
				gestureDetector = SwypeManager.AddSwypeHandler(this, MainActivity.class, Qaaba.class);

			}
			catch(Exception exception){
				exception.printStackTrace();
			}
		 gestureListener = new OnTouchListener() {



		 public boolean onTouch(View v, MotionEvent event) {
		 gestureDetector.onTouchEvent(event);
		 return true;
		 }
		 };
		 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }}
		 public boolean onTouchEvent(MotionEvent event) {
		 if (android.os.Build.VERSION.SDK_INT > 4) {
		 if (event.getAction() == 0) {
		 this.motionStartX = event.getX();
		 this.motionStartY = event.getY();
		 }
		 if (event.getAction() == 2 && (Math.abs(this.motionStartX - event.getX()) > 20.0f || Math.abs(this.motionStartY - event.getY()) > 20.0f)) {
		 this.motionTime = System.currentTimeMillis();
		 }
		 this.gestureDetector.onTouchEvent(event);
		 }
		 return false;
		 }
		/*if (android.os.Build. VERSION.SDK_INT > 4) {
            this.gestureDetector = SwypeManager.AddSwypeHandler(this, MainActivity.class, qaabaActivity.class);
        }
    }
	public boolean onTouchEvent(MotionEvent event) {
        if (android.os.Build. VERSION.SDK_INT <= 4 || !this.gestureDetector.onTouchEvent(event)) {
            return false;
        }
        return true;*/
    
		

//تغيير خط النشاط
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
			Toast.makeText(getApplicationContext(),getString(R.string.nofilefont),Toast.LENGTH_LONG).show();
		}
		
		}
		//ظل النص
	public void setShadow(TextView tview){
		tview.setShadowLayer((float)3,(float)3,(float)3,Color.BLACK);
	}
		}
