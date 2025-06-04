package com.PrayerTimes;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;

public class PrefsInterface {
	/*private boolean isManualInputEnabled;
	private boolean isManualInputEnabled1;
	private SharedPreferences minputEnabled;
	*/
	private boolean datedst;
	private boolean myDstOpt;
	private SharedPreferences adjustments0,adjustments1,adjustments2,adjustments3,adjustments4;
    private boolean adhanEnabled;
	private int hourformat;
	private int aadjustmentstimes0,aadjustmentstimes1,aadjustmentstimes2,aadjustmentstimes3,aadjustmentstimes4;
	private SharedPreferences hourPreferences;
	private int highLatitudes;
	private SharedPreferences highlatit;
	private int hijri;
	private SharedPreferences hijriday;
    private int asrMethod;
    private SharedPreferences asrPreferences;
    private int calcMethod;
    private SharedPreferences calcPreferences;
    private Gson gson;
    private CityObj myCity;
    private boolean notifsEnabled;
    private SharedPreferences sPreferences;
	private int language;
    private SharedPreferences slanguage;
	
    public PrefsInterface(Context context) {
        gson = new Gson();
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json1 = sPreferences.getString("myJsonCity", "");
        if (json1.isEmpty()) {
            System.out.println("Error getting city from settings.");
        } else {
            myCity = (CityObj) gson.fromJson(json1, new TypeToken<CityObj>() {
													   }.getType());
        }
        calcPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json2 = sPreferences.getString("myCalcMethod", "");
        if (!json2.isEmpty()) {
            calcMethod = ((Integer) gson.fromJson(json2, new TypeToken<Integer>() {
															}.getType())).intValue();
        }
        asrPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json3 = sPreferences.getString("myAsrMethod", "");
        if (!json3.isEmpty()) {
            asrMethod = ((Integer) gson.fromJson(json3, new TypeToken<Integer>() {
														   }.getType())).intValue();
        }
		hourPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json4 = sPreferences.getString("timeformathour", "");
        if (!json4.isEmpty()) {
            hourformat = ((Integer) gson.fromJson(json4, new TypeToken<Integer>() {
												 }.getType())).intValue();
        }
		highlatit = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json5 = sPreferences.getString("highlatitudes", "");
        if (!json5.isEmpty()) {
            highLatitudes = ((Integer) gson.fromJson(json5, new TypeToken<Integer>() {
												  }.getType())).intValue();
        }
		hijriday = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json6 = sPreferences.getString("hijriday", "");
        if (!json6.isEmpty()) {
            hijri = ((Integer) gson.fromJson(json6, new TypeToken<Integer>() {
													 }.getType())).intValue();
        }
		slanguage = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String jsonselectlanguage = slanguage.getString("selectlanguage", "");
        if (!jsonselectlanguage.isEmpty()) {
            language = ((Integer) gson.fromJson(jsonselectlanguage, new TypeToken<Integer>() {
												 }.getType())).intValue();
        }
		adjustments0 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json7 = sPreferences.getString("adjustments0", "");
        if (!json7.isEmpty()) {
            aadjustmentstimes0 = ((Integer) gson.fromJson(json7, new TypeToken<Integer>() {
														  }.getType())).intValue();
        }
		adjustments1 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json8 = sPreferences.getString("adjustments1", "");
        if (!json8.isEmpty()) {
            aadjustmentstimes1 = ((Integer) gson.fromJson(json8, new TypeToken<Integer>() {
											 }.getType())).intValue();
        }
		adjustments2 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json9 = sPreferences.getString("adjustments2", "");
        if (!json9.isEmpty()) {
            aadjustmentstimes2 = ((Integer) gson.fromJson(json9, new TypeToken<Integer>() {
														  }.getType())).intValue();
        }
		adjustments3 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json10 = sPreferences.getString("adjustments3", "");
        if (!json10.isEmpty()) {
            aadjustmentstimes3 = ((Integer) gson.fromJson(json10, new TypeToken<Integer>() {
														  }.getType())).intValue();
        }
		adjustments4 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String json11 = sPreferences.getString("adjustments4", "");
        if (!json11.isEmpty()) {
            aadjustmentstimes4 = ((Integer) gson.fromJson(json11, new TypeToken<Integer>() {
														  }.getType())).intValue();
        }
		//
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		 myDstOpt = preferences.getBoolean("myDstOpt", datedst);
		
	// minputEnabled = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
	//isManualInputEnabled1 = minputEnabled.getBoolean("prefs_is_manual_input", isManualInputEnabled);
	}
	
   
	public CityObj getCityObj() {
		if (myCity == null) {
			return myCity = new CityObj("Cairo",2.0,"Egypt",30.06263 , 31.24967); 

		}
        return myCity;

    }
	

    public double getLat() {
		if (myCity == null) {
			return 30.06263;
			}
        return myCity.latitude();
    }

    public double getLon() {
		if (myCity == null) {
			return 31.24967;
		}
        return myCity.longitude();
    }

    public String getCityName() {
        if (myCity == null) {
            return "Cairo";
        }
        return myCity.city();
    }

    public int[] getOffsets()
	{
        int[] iArr = {
			aadjustmentstimes0,
			0,
			aadjustmentstimes1,aadjustmentstimes2,
		0,
			aadjustmentstimes3,
		aadjustmentstimes4};  
        return iArr;
    }

    public double getTimeZone() {
		if (myCity == null) {
			return 2.0;
		}
		
        return myCity.timeZone();
    }

    public int getCalcMethod() {
		if (myCity == null) {
			return 5;
		}
        return calcMethod;
    }

    public int getAsrMethod() {
		if (myCity == null) {
			return 0;
		}
        return asrMethod;
    }

    public boolean adhanEnabled() {
        return adhanEnabled;
    }

    public boolean notificationsEnabled() {
        return notifsEnabled;
    }
	
	public int timeformat(){
		if (myCity == null) {
			return 1;
		}
		
		return hourformat;
	}
	public int highlatitudes(){
		if (myCity == null) {
			return 3;
		}

		return highLatitudes;
	}
	public int getHijriOffset(){
		
		return hijri;
	}
	public boolean myDst(){
		getOffsets();
		return myDstOpt;
	}
	public int getLanguage() {
        return language;
    }
	
	/*public boolean isManualInputEnabled() {
		
        return isManualInputEnabled;
    }
	public void setManualInputEnabled(boolean manualInputEnabled) {
        isManualInputEnabled = manualInputEnabled;
    }*/
	}
	
	
	 
