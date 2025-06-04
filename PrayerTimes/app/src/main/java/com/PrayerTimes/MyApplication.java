package com.PrayerTimes;
import android.content.*;
import java.util.*;
import android.content.res.*;

public final class MyApplication extends android.app.Application
{

	private static int n;
	
    @Override
    public void onCreate() {
		
        super.onCreate();
		
		ChangeFontDefault.overrideFont(this, "SERIF", "fonts/abbuline.otf");
		Localization(this);

	}
	public static void Localization(Context context) {
        
        PrefsInterface prefsInterface ;
        prefsInterface = new PrefsInterface(context);
         
        
		switch (prefsInterface.getLanguage())
		{
            case 0:
				 n=0;
				if(prefsInterface.getLanguage()== n){
				Locale englishLocale = new Locale("en");
				Locale.setDefault(englishLocale);
				Configuration config = new Configuration();
				config.locale = englishLocale;
				context. getResources().updateConfiguration(config, context. getResources().getDisplayMetrics());
				}
                return;
            case 1:
                n=1;
				if(prefsInterface.getLanguage()==n){
					Locale englishLocale = new Locale("ar");
					Locale.setDefault(englishLocale);
					Configuration config = new Configuration();
					config.locale = englishLocale;
					context. getResources().updateConfiguration(config, context. getResources().getDisplayMetrics());
				}
                 
                return;
            default:
                return;
        }}
	}


