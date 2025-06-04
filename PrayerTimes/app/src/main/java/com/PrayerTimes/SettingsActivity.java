package com.PrayerTimes;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.*;
import android.preference.*;
import android.provider.*;
import android.app.*;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.content.*;
import android.os.*;
import android.content.res.*;
import android.view.*;
import android.view.animation.*;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    private static Gson gson;
    private static OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new OnPreferenceChangeListener() {
        public boolean onPreferenceChange(Preference preference, Object value) {
            CharSequence charSequence = null;
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                if (index >= 0) {
                    charSequence = listPreference.getEntries()[index];
                }
                preference.setSummary(charSequence);
            } else if (!(preference instanceof RingtonePreference)) {
                preference.setSummary(stringValue);
            } else if (TextUtils.isEmpty(stringValue)) {
                preference.setSummary(R.string.pref_ringtone_silent);
            } else {
                Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(stringValue));
                if (ringtone == null) {
                    preference.setSummary(null);
                } else {
                    preference.setSummary(ringtone.getTitle(preference.getContext()));
                }
            }
            return true;
        }
    };
    CheckBoxPreference adhanOpt;
    public int asrMethod;
    CheckBoxPreference asrOpt;
    ListPreference asrSelect;
	ListPreference hijriday;
	ListPreference hourfrom;
	private int hijri;
	public int hourformat;
	public int highLatitudes;
	public int adjustmentstimes0,adjustmentstimes1,adjustmentstimes2,adjustmentstimes3,adjustmentstimes4;
	ListPreference highLatitude;
	ListPreference adjustments0,adjustments1,adjustments2,adjustments3,adjustments4;
    private Preference autoLoc;
    public int calcMethod;
    ListPreference calcSelect;
    EditTextPreference cityName;
    CheckBoxPreference dhuhrOpt;
    SwitchPreference dstOpt;
    CheckBoxPreference fajrOpt;
    private ArrayList<String> fragmentsList = new ArrayList();
    CheckBoxPreference ishaOpt;
    EditTextPreference latText;
    EditTextPreference lonText;
    CheckBoxPreference maghribOpt;
    private Preference manSel;
    private CityObj myCity;
    CheckBoxPreference notifsOpt;
    private SharedPreferences radioSelectionPreferences;
    private SharedPreferences sPreferences;
    EditTextPreference tzText;
	ListPreference selectLanguage;
	int language = 0;
	 
	//private boolean isManualInputEnabled;
	//CheckBoxPreference inputEnabled;
	 
    @TargetApi(19)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
			
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
		//setTheme(android.R.style.Theme_Material);
        super.onPostCreate(savedInstanceState);
        setupSimplePreferencesScreen();
        latText = (EditTextPreference) findPreference("lat");
        lonText = (EditTextPreference) findPreference("lon");
        cityName = (EditTextPreference) findPreference("cityname");
        tzText = (EditTextPreference) findPreference("timezone");
        radioSelectionPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        String json = null;
        try {
            json = sPreferences.getString("myJsonCity", "");
        } catch (Exception e) {
            System.out.println("Error retrieving data from memory.");
        }
        if (!json.isEmpty()) {
            myCity = (CityObj) gson.fromJson(json, new TypeToken<CityObj>() {
												  }.getType());
            latText.setSummary(Double.toString(myCity.latitude()));
            lonText.setSummary(Double.toString(myCity.longitude()));
            cityName.setSummary(myCity.city());
            tzText.setSummary(Double.toString(myCity.timeZone()));
			
        }
		adjustments0=(ListPreference)findPreference("offset_fajr");
		
		adjustments1=(ListPreference)findPreference("offset_dohr");
		adjustments2=(ListPreference)findPreference("offset_asr");
		adjustments3=(ListPreference)findPreference("offset_maghrib");
		adjustments4=(ListPreference)findPreference("offset_isha");
		highLatitude=(ListPreference)findPreference("settings_high_lats");
		hourfrom=(ListPreference)findPreference("settings_time_format");
		hijriday=(ListPreference)findPreference("settings_islamic_date");
        calcSelect = (ListPreference) findPreference("calc_method_pref");
        asrSelect = (ListPreference) findPreference("asr_method_pref");
        dstOpt = (SwitchPreference) findPreference("use_dst_option");
        notifsOpt = (CheckBoxPreference) findPreference("enable_notifs");
        adhanOpt = (CheckBoxPreference) findPreference("enable_adhan");
        fajrOpt = (CheckBoxPreference) findPreference("enable_fajrAdhan");
        dhuhrOpt = (CheckBoxPreference) findPreference("enable_dhuhrAdhan");
        asrOpt = (CheckBoxPreference) findPreference("enable_asrAdhan");
        maghribOpt = (CheckBoxPreference) findPreference("enable_maghribAdhan");
        ishaOpt = (CheckBoxPreference) findPreference("enable_ishaAdhan");
		selectLanguage = (ListPreference) findPreference("selectLanguage");
		//inputEnabled=(CheckBoxPreference)findPreference("prefs_is_manual_input");
        radioSelectionPreferences.registerOnSharedPreferenceChangeListener(this);
		//dstOpt.setWidgetLayoutResource(R.layout.custom_switch);
    }
	
	
	
	
    private void setupSimplePreferencesScreen() {
        if (isSimplePreferences(this)) {
            addPreferencesFromResource(R.xml.pref_location);
            manSel = findPreference("manual_select");
            manSel.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						startActivityForResult(new Intent(SettingsActivity.this, SearchLocationActivity.class), 1);
						return true;
					}
				});
            autoLoc = findPreference("auto_detect");
            autoLoc.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						getLocAuto();
						return false;
					}
				});
            addPreferencesFromResource(R.xml.pref_calc_method);
            addPreferencesFromResource(R.xml.pref_notification);
			addPreferencesFromResource(R.xml.preferences_prayer_time_offset);
			addPreferencesFromResource(R.xml.pref_misc);
			 
			Preference preference = findPreference("settings_About");
			preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						// قم بتنفيذ الإجراءات التي ترغب فيها عند النقر على البند

						// انتقل إلى النشاط الجديد هنا
						Intent intent = new Intent(SettingsActivity.this, AboutAndVersion.class);
						startActivity(intent);

						return true;
					}
				});
			/*Preference prefinput = findPreference("manual_input");
			prefinput.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference prefinput) {
						// قم بتنفيذ الإجراءات التي ترغب فيها عند النقر على البند

						// انتقل إلى النشاط الجديد هنا
						Intent intent = new Intent(SettingsActivity.this, InputManually.class);
						startActivity(intent);

						return true;
					}
				});*/
			Preference battery = findPreference("settings_Battery");
			 battery.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			 @Override
			 public boolean onPreferenceClick(Preference battery) {
				 // Check if app is whitelisted
				 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					 PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
					 if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
						 // If app is not whitelisted, display settings screen to add it to whitelist
						 Toast.makeText(SettingsActivity.this, getString(R.string.toastbattery), Toast.LENGTH_LONG).show();
						 Intent intent = new Intent();
						 intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
						 intent.setData(Uri.parse("package:" + getPackageName()));
						 startActivity(intent);
					 } else {
						 // App is already whitelisted
						 Toast.makeText(SettingsActivity.this, getString(R.string.toastbattery1), Toast.LENGTH_SHORT).show();
					 }}

			 return true;
			 }
			 });
			Preference notificationsapp = findPreference("settings_notifications_app");
			 notificationsapp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			 @Override
			 public boolean onPreferenceClick(Preference notificationsapp) {
				
				 
				 Intent intent = new Intent();

				 int sdkVersion = android.os.Build.VERSION.SDK_INT;
				 String settingsAction;

				 if (sdkVersion >= 26) {
					 settingsAction = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS;
					 intent.setAction(settingsAction);
					 intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
				 } else if (sdkVersion >= 21) {
					 settingsAction = "android.settings.APP_NOTIFICATION_SETTINGS";
					 intent.setAction(settingsAction);
					 intent.putExtra("app_package", getPackageName());
					 intent.putExtra("app_uid", getApplicationInfo().uid);
				 } else {
					 settingsAction = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
					 intent.setAction(settingsAction);
					 intent.addCategory(Intent.CATEGORY_DEFAULT);
					 Uri uri = Uri.fromParts("package", getPackageName(), null);
					 intent.setData(uri);
				 }

				 startActivity(intent);
			 return true;
			 }
			 });
        }
		Preference exit = findPreference("Exit");
		exit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference exit) {
					android.widget.Toast toast=Toast.makeText(SettingsActivity.this,"",Toast.LENGTH_SHORT); 
					
					// toast.setGravity(android.view.Gravity.CENTER,0,0); 
					View myview = getLayoutInflater().inflate(R.layout.custom_toast, 
					(android.view.ViewGroup)findViewById(R.id.custom_layout));
					myview.startAnimation(AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.bounce));
					
					toast.setView(myview); 
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.show();
					finishAffinity();
					return true;
				}
			});
    }

    protected void onResume() {
        super.onResume();
		
    }

   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
	   if (requestCode == 1 && resultCode ==RESULT_OK ) {
            myCity = (CityObj) gson.fromJson(data.getStringExtra("chosenCity"), new TypeToken<CityObj>() {
												  }.getType());
            updateSelection("use_dst_option");
            latText.setSummary(Double.toString(myCity.latitude()));
            lonText.setSummary(Double.toString(myCity.longitude()));
            cityName.setSummary(myCity.city());
            tzText.setSummary(Double.toString(myCity.timeZone()));
            latText.setText(Double.toString(myCity.latitude()));
            lonText.setText(Double.toString(myCity.longitude()));
            cityName.setText(myCity.city());
            tzText.setText(Double.toString(myCity.timeZone()));
            Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            gson = new Gson();
            try {
                editor.putString("myJsonCity", gson.toJson(myCity));
                editor.commit();
            } catch (Exception e) {
                System.out.println("An error has occured saving to json.");
                e.getStackTrace();
            }
        }
	   
    }
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					myCity = (CityObj) gson.fromJson(data.getStringExtra("chosenCity"), new TypeToken<CityObj>() {
													 }.getType());
					updateSelection("use_dst_option");
					latText.setSummary(Double.toString(myCity.latitude()));
					lonText.setSummary(Double.toString(myCity.longitude()));
					cityName.setSummary(myCity.city());
					tzText.setSummary(Double.toString(myCity.timeZone()));
					latText.setText(Double.toString(myCity.latitude()));
					lonText.setText(Double.toString(myCity.longitude()));
					cityName.setText(myCity.city());
					tzText.setText(Double.toString(myCity.timeZone()));
					Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
					gson = new Gson();
					try {
						editor.putString("myJsonCity", gson.toJson(myCity));
						editor.commit();
					} catch (Exception e) {
						System.out.println("An error has occurred saving to json.");
						e.getStackTrace();
					}
				}
				break;
			case 2:
				if (resultCode == RESULT_OK) {
					String cityJson = PreferenceManager.getDefaultSharedPreferences(this).getString("cityJson", "");
					Gson gson = new Gson();
					String chosenCityJson = data.getStringExtra("cityJson");
					myCity = gson.fromJson(cityJson, CityObj.class);
					
					updateSelection("use_dst_option");
					latText.setSummary(Double.toString(myCity.latitude()));
					lonText.setSummary(Double.toString(myCity.longitude()));
					cityName.setSummary(myCity.city());
					tzText.setSummary(Double.toString(myCity.timeZone()));
					latText.setText(Double.toString(myCity.latitude()));
					lonText.setText(Double.toString(myCity.longitude()));
					cityName.setText(myCity.city());
					tzText.setText(Double.toString(myCity.timeZone()));
					Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
					gson = new Gson();
					try {
						editor.putString("myJsonCity", gson.toJson(myCity));
						editor.commit();
					} catch (Exception e) {
						System.out.println("An error has occurred saving to json.");
						e.getStackTrace();
					}
				}
				break;
			default:
				break;
		}
	}*/
	 
	
	

    public void onSharedPreferenceChanged(SharedPreferences spf, String key) {
        updateSelection(key);
    }

    private void updateSelection(String key) {
        if (key != null) {
			//if (key.contentEquals("prefs_is_manual_input")) {
                //isManualInputEnabled();
           // }
            if (key.contentEquals("calc_method_pref")) {
                updateCalcMethod();
            }
            if (key.contentEquals("asr_method_pref")) {
                updateAsrMethod();
            }
            if (key.contentEquals("use_dst_option")) {
                updateDst();
            }
            if (key.contentEquals("enable_notifs")) {
                updateNotifs();
            }
            if (key.contentEquals("enable_adhan")) {
                updateAdhan();
            }
            if (key.contentEquals("enable_fajrAdhan")) {
                updateFajrAdhan();
            }
            if (key.contentEquals("enable_dhuhrAdhan")) {
                updateDhuhrAdhan();
            }
            if (key.contentEquals("enable_asrAdhan")) {
                updateAsrAdhan();
            }
            if (key.contentEquals("enable_maghribAdhan")) {
                updateMaghribAdhan();
            }
            if (key.contentEquals("enable_ishaAdhan")) {
                updateIshaAdhan();
            }
			if (key.contentEquals("settings_time_format")) {
                updatehourformat();
            }
			if (key.contentEquals("settings_high_lats")) {
				updatehighLatitudes();
            }
			if (key.contentEquals("settings_islamic_date")) {
				updateislamicdate() ;
				 
            }
			if (key.contentEquals("offset_fajr")) {
				updateadjustmentsTimes(adjustments0,adjustmentstimes0,"adjustments0");
            }
			if (key.contentEquals("offset_dohr")) {
				updateadjustmentsTimes(adjustments1,adjustmentstimes1,"adjustments1");
            }
			if (key.contentEquals("offset_asr")) {
				updateadjustmentsTimes(adjustments2,adjustmentstimes2,"adjustments2");
            }
			if (key.contentEquals("offset_maghrib")) {
				updateadjustmentsTimes(adjustments3,adjustmentstimes3,"adjustments3");
            }
			if (key.contentEquals("offset_isha")) {
				updateadjustmentsTimes(adjustments4,adjustmentstimes4,"adjustments4");
            }
            if (key.contentEquals("selectLanguage")) {
                updateLanguage();
            }
        
        }
    }
	//تمكين المدخلات اليدوية
	/*public void isManualInputEnabled() {

		// Load the values entered by the user
		SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
		String city1 = sharedPreferences.getString("cityname1", "DefCustomCity");
		double latitude1 = Double.parseDouble((String) sharedPreferences.getString("lat1", "0"));
		double longitude1 = Double.parseDouble((String) sharedPreferences.getString("lon1", "0"));
		double timezone1 = Double.parseDouble((String) sharedPreferences.getString("timezone1", "0"));

		// Read the checkbox state directly from the preference
		CheckBoxPreference manualInputPreference = (CheckBoxPreference) findPreference("prefs_is_manual_input");
		boolean isManualInput = manualInputPreference.isChecked();

		EditTextPreference customCityPreference = (EditTextPreference) findPreference("cityname1");
		String customCity = sharedPreferences.getString("cityname1", "CustomCity");
		customCityPreference.setText(customCity);

		EditTextPreference latitudePreference = (EditTextPreference) findPreference("lat1");
		String latitude = sharedPreferences.getString("lat1", "0");
		latitudePreference.setText(latitude);

		EditTextPreference longitudePreference = (EditTextPreference) findPreference("lon1");
		String longitude = sharedPreferences.getString("lon1", "0");
		longitudePreference.setText(longitude);

		EditTextPreference timezonePreference = (EditTextPreference) findPreference("timezone1");
		String timezone = sharedPreferences.getString("timezone1", "2");
		timezonePreference.setText(timezone);

		myCity = new CityObj(city1, timezone1, city1, latitude1, longitude1);
		latText.setSummary(Double.toString(myCity.latitude()));
		latText.setText(Double.toString(myCity.latitude()));
		lonText.setSummary(Double.toString(myCity.longitude()));
		lonText.setText(Double.toString(myCity.longitude()));
		cityName.setSummary(myCity.city());
		cityName.setText(myCity.city());
		tzText.setSummary(Double.toString(myCity.timeZone()));
		tzText.setText(Double.toString(myCity.timeZone()));

		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		gson = new Gson();
		try {
			editor.putString("myJsonCity", gson.toJson(myCity));
			editor.commit();
		} catch (Exception e3) {
			System.out.println("An error has occured saving to json.");
			e3.getStackTrace();
		}

		// Save the checkbox state
		SharedPreferences.Editor editor1 = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor1.putBoolean("prefs_is_manual_input", isManualInput);
		editor1.commit();



	
	
    }*/
    
	private void updateDst()
	{
		
        boolean dstCheck = dstOpt.isChecked();
		/* if (dstCheck && timezone>0)
		 {
		 myCity.setTimeZone(myCity.timeZone() + java.util.concurrent.TimeUnit.HOURS.toMillis(1));
		 myCity.setTimeZone(timezone+java.util.concurrent.TimeUnit.HOURS.toMillis(1));
		 }
		 else
		 {
		 myCity.setTimeZone(myCity.timeZone() - java.util.concurrent.TimeUnit.HOURS.toMillis(0));
		 }*/
		if (dstCheck && Calendar.getInstance().get(16) > 0) {
            myCity.setTimeZone(myCity.timeZone() + 1.0d);

        }

        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myDstOpt", dstCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving to json.");
            e.getStackTrace();
        }
    }
	
	/*private void getLocAuto() {
	 try {
	 LocationManager localizer = (LocationManager) getSystemService("location");
	 List<String> providers = localizer.getProviders(true);
	 Location bestPosition = null;
	 if (!localizer.isProviderEnabled("network")) {
	 Toast.makeText(getApplicationContext(), "Enable location services!", 0).show();
	 startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 10);
	 }
	 for (String s : providers) {
	 Location temp = localizer.getLastKnownLocation(s);
	 if (temp != null && (bestPosition == null || temp.getAccuracy() < bestPosition.getAccuracy())) {
	 bestPosition = temp;
	 }
	 }
	 if (bestPosition != null) {
	 double latitude = bestPosition.getLatitude();
	 double longitude = bestPosition.getLongitude();
	 Geocoder geo = new Geocoder(this, Locale.getDefault());
	 StringBuilder builder = new StringBuilder();
	 String city = null;
	 try {
	 List<Address> address = geo.getFromLocation(latitude, longitude, 1);
	 int maxLines = ((Address) address.get(0)).getMaxAddressLineIndex();
	 if (address.size() <1) {
	 city = ((Address) address.get(0)).getLocality();
	 }
	 } catch (IOException e) {
	 } catch (NullPointerException e2) {
	 }
	 double offset = (double) ((Calendar.getInstance().getTimeZone().getRawOffset() / 60000) / 60);
	 if (Calendar.getInstance().get(16) > 0) {
	 offset += 1.0d;
	 }
	 myCity = new CityObj(city, offset, city, latitude, longitude);
	 latText.setSummary(Double.toString(myCity.latitude()));
	 latText.setText(Double.toString(myCity.latitude()));
	 lonText.setSummary(Double.toString(myCity.longitude()));
	 lonText.setText(Double.toString(myCity.longitude()));
	 cityName.setSummary(myCity.city());
	 cityName.setText(myCity.city());
	 tzText.setSummary(Double.toString(myCity.timeZone()));
	 tzText.setText(Double.toString(myCity.timeZone()));
	 Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	 gson = new Gson();
	 try {
	 Editor editor2 = editor;
	 editor2.putString("myJsonCity", gson.toJson(myCity));
	 editor.commit();
	 } catch (Exception e3) {
	 System.out.println("An error has occured saving to json.");
	 e3.getStackTrace();
	 }
	 } else {
	 Toast.makeText(getApplicationContext(), "Unable to get location. Please try again.", 0).show();
	 }
	 } catch (Exception afg) {
	 afg.printStackTrace();
	 }
	 }*/
   private void getLocAuto() {
		try{
			
		 //  LocationManager localizer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationManager localizer = (LocationManager) getSystemService("location");
        List<String> providers = localizer.getProviders(true);
        Location bestPosition = null;
        if (!localizer.isProviderEnabled("network")) {
            Toast.makeText(this, getString(R.string.Enablelocation), 0).show();
            startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 10);
        }
        for (String s : providers) {
            Location temp = localizer.getLastKnownLocation(s);
            if (temp != null && (bestPosition == null || temp.getAccuracy() < bestPosition.getAccuracy())) {
                bestPosition = temp;
            }
        }
        double latitude = bestPosition.getLatitude();
        double longitude = bestPosition.getLongitude();
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        String city = null;
        try {
            List<Address> address = geo.getFromLocation(latitude, longitude, 1);
            int maxLines = ((Address) address.get(0)).getMaxAddressLineIndex();
            if (address.size() > 0) {
                city = ((Address) address.get(0)).getLocality();
            }
        } catch (IOException e) {
        } catch (NullPointerException e2) {
        }
        double offset = (double) ((Calendar.getInstance().getTimeZone().getRawOffset() / 60000) / 60);
        if (Calendar.getInstance().get(16) > 0) {
            offset += 1.0d;
        }
        myCity = new CityObj(city, offset, city, latitude, longitude);
        latText.setSummary(Double.toString(myCity.latitude()));
        latText.setText(Double.toString(myCity.latitude()));
        lonText.setSummary(Double.toString(myCity.longitude()));
        lonText.setText(Double.toString(myCity.longitude()));
        cityName.setSummary(myCity.city());
        cityName.setText(myCity.city());
        tzText.setSummary(Double.toString(myCity.timeZone()));
        tzText.setText(Double.toString(myCity.timeZone()));
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            Editor editor2 = editor;
            editor2.putString("myJsonCity", gson.toJson(myCity));
            editor.commit();
        } catch (Exception e3) {
            System.out.println("An error has occured saving to json.");
            e3.getStackTrace();
        }
    }
	catch(Exception afg){
		afg.printStackTrace();
	}}
	 
//	
	
	
	 
	
	
	
    private void updateLanguage() {
		switch (selectLanguage.findIndexOfValue(selectLanguage.getValue())) {
            case 0:
				language = 0;
                Locale englishLocale = new Locale("en");
				Locale.setDefault(englishLocale);
				Configuration config = new Configuration();
				config.locale = englishLocale;
				getResources().updateConfiguration(config, getResources().getDisplayMetrics());
				
                recreate();
                break;
            case 1:
                language = 1;
                 englishLocale = new Locale("ar");
				Locale.setDefault(englishLocale);
				config = new Configuration();
				config.locale = englishLocale;
				getResources().updateConfiguration(config, getResources().getDisplayMetrics());
				
                recreate();
                break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("selectlanguage", gson.toJson(Integer.valueOf(language)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving select language to json.");
            e.getStackTrace();
        }
    }
	
    

    private void updateCalcMethod() {
        switch (calcSelect.findIndexOfValue(calcSelect.getValue())) {
            case 0:
                calcMethod = 0;
                break;
            case  1:
                calcMethod = 1;
                break;
            case 2:
                calcMethod = 2;
                break;
            case 3:
                calcMethod = 3;
                break;
            case 4:
                calcMethod = 4;
                break;
            case 5:
                calcMethod = 5;
                break;
            case 6:
                calcMethod = 6;
                break;
            default:
                calcMethod = 2;
                break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("myCalcMethod", gson.toJson(Integer.valueOf(calcMethod)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving calc method to json.");
            e.getStackTrace();
        }
    }

    private void updateAsrMethod() {
        switch (asrSelect.findIndexOfValue(asrSelect.getValue())) {
            case 0:
                asrMethod = 0;
                break;
            case 1:
                asrMethod = 1;
                break;
            default:
                asrMethod = 0;
                break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("myAsrMethod", gson.toJson(Integer.valueOf(asrMethod)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving asr method to json.");
            e.getStackTrace();
        }}
	private void updatehourformat() {
        switch (hourfrom.findIndexOfValue(hourfrom.getValue())) {
            case 0:
                hourformat = 0;
                break;
            case 1:
                hourformat = 1;
                break;
			/*case 2:
                hourformat = 2;
                break;
            default:
                hourformat = 0;
                break;*/
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("timeformathour", gson.toJson(Integer.valueOf(hourformat)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving hour format to json.");
            e.getStackTrace();
        }}
    private void updatehighLatitudes() {
        switch (highLatitude.findIndexOfValue(highLatitude.getValue())) {
            case 0:
                highLatitudes = 0;
                break;
            case 1:
                highLatitudes = 1;
                break;
				case 2:
				 highLatitudes = 2;
				 break;
			case 3:
				highLatitudes = 3;
				break;
				 default:
				 highLatitudes = 0;
				 break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("highlatitudes", gson.toJson(Integer.valueOf(highLatitudes)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving highlatitudes to json.");
            e.getStackTrace();
        }}
	private void updateislamicdate() {
        switch (hijriday.findIndexOfValue(hijriday.getValue())) {
            case 0:
                hijri = -2;
                break;
            case 1:
                hijri = -1;
                break;
			case 2:
				hijri = 0;
				break;
			case 3:
				hijri = 1;
				break;
			case 4:
				hijri = 2;
				break;
			default:
				hijri = 0;
				break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString("hijriday", gson.toJson(Integer.valueOf(hijri)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving hijri to json.");
            e.getStackTrace();
        }
    
    }
	private void updateadjustmentsTimes(ListPreference lf,int adjustmentstimes,String ktime) {
        switch (lf.findIndexOfValue(lf.getValue())) {
            case 0:
                adjustmentstimes = -60;
                break;
			case 1:
                adjustmentstimes = -59;
                break;
			case 2:
                adjustmentstimes = -58;
                break;
			case 3:
                adjustmentstimes = -57;
                break;
			case 4:
                adjustmentstimes = -56;
                break;
			case 5:
                adjustmentstimes = -55;
                break;
			case 6:
                adjustmentstimes = -54;
                break;
			case 7:
                adjustmentstimes = -53;
                break;
			case 8:
                adjustmentstimes = -52;
                break;
			case 9:
                adjustmentstimes = -51;
                break;
			case 10:
                adjustmentstimes = -50;
                break;
			case 11:
                adjustmentstimes = -49;
                break;
			case 12:
                adjustmentstimes = -48;
                break;
			case 13:
                adjustmentstimes = -47;
                break;
			case 14:
                adjustmentstimes = -46;
                break;
			case 15:
                adjustmentstimes = -45;
                break;
			case 16:
                adjustmentstimes = -44;
                break;
			case 17:
                adjustmentstimes = -43;
                break;
			case 18:
                adjustmentstimes = -42;
                break;
			case 19:
                adjustmentstimes = -41;
                break;
			case 20:
                adjustmentstimes = -40;
                break;
			case 21:
                adjustmentstimes = -39;
                break;
			case 22:
                adjustmentstimes = -38;
                break;
			case 23:
                adjustmentstimes = -37;
                break;
			case 24:
                adjustmentstimes = -36;
                break;
			case 25:
                adjustmentstimes = -35;
                break;
			case 26:
                adjustmentstimes = -34;
                break;
			case 27:
                adjustmentstimes = -33;
                break;
			case 28:
                adjustmentstimes = -32;
                break;
			case 29:
                adjustmentstimes = -31;
                break;
			case 30:
                adjustmentstimes = -30;
                break;
			case 31:
                adjustmentstimes = -29;
                break;
			case 32:
                adjustmentstimes = -28;
                break;
			case 33:
                adjustmentstimes = -27;
                break;
			case 34:
                adjustmentstimes = -26;
                break;
			case 35:
                adjustmentstimes = -25;
                break;
			case 36:
                adjustmentstimes = -24;
                break;
			case 37:
                adjustmentstimes = -23;
                break;
			case 38:
                adjustmentstimes = -22;
                break;
			case 39:
                adjustmentstimes = -21;
                break;
			case 40:
                adjustmentstimes = -20;
                break;
			case 41:
                adjustmentstimes = -19;
                break;
			case 42:
                adjustmentstimes = -18;
                break;
			case 43:
                adjustmentstimes = -17;
                break;
			case 44:
                adjustmentstimes = -16;
                break;
			case 45:
                adjustmentstimes = -15;
                break;
			case 46:
                adjustmentstimes = -14;
                break;
			case 47:
                adjustmentstimes = -13;
                break;
			case 48:
                adjustmentstimes = -12;
                break;
			case 49:
                adjustmentstimes = -11;
                break;
			case 50:
                adjustmentstimes = -10;
                break;
			case 51:
                adjustmentstimes = -9;
                break;
			case 52:
                adjustmentstimes = -8;
                break;
			case 53:
                adjustmentstimes = -07;
                break;
			case 54:
                adjustmentstimes = -06;
                break;
			case 55:
                adjustmentstimes = -05;
                break;
			case 56:
                adjustmentstimes = -04;
                break;
			case 57:
                adjustmentstimes = -03;
                break;
			case 58:
                adjustmentstimes = -02;
                break;
			case 59:
                adjustmentstimes = -01;
                break; 
            case 60:
                adjustmentstimes = 00;
                break;

			case 61:
                adjustmentstimes = 01;
                break;
			case 62:
                adjustmentstimes = 02;
                break;
			case 63:
                adjustmentstimes = 03;
                break;
			case 64:
                adjustmentstimes = 04;
                break;
			case 65:
                adjustmentstimes = 05;
                break;
			case 66:
                adjustmentstimes = 06;
                break;
			case 67:
                adjustmentstimes = 07;
                break;
			case 68:
                adjustmentstimes = 8;
                break;
			case 69:
                adjustmentstimes = 9;
                break;
			case 70:
                adjustmentstimes = 10;
                break;
			case 71:
                adjustmentstimes = 11;
                break;
			case 72:
                adjustmentstimes = 12;
                break;
			case 73:
                adjustmentstimes = 13;
                break;
			case 74:
                adjustmentstimes = 14;
                break;
			case 75:
                adjustmentstimes = 15;
                break;
			case 76:
                adjustmentstimes = 16;
                break;
			case 77:
                adjustmentstimes = 17;
                break;
			case 78:
                adjustmentstimes = 18;
                break;
			case 79:
                adjustmentstimes = 19;
                break;
			case 80:
                adjustmentstimes = 20;
                break;
			case 81:
                adjustmentstimes = 21;
                break;
			case 82:
                adjustmentstimes = 22;
                break;
			case 83:
                adjustmentstimes = 23;
                break;
			case 84:
                adjustmentstimes = 24;
                break;
			case 85:
                adjustmentstimes = 25;
                break;
			case 86:
                adjustmentstimes = 26;
                break;
			case 87:
                adjustmentstimes = 27;
                break;
			case 88:
                adjustmentstimes = 28;
                break;
			case 89:
                adjustmentstimes = 29;
                break;
			case 90:
                adjustmentstimes = 30;
                break;
			case 91:
                adjustmentstimes = 31;
                break;
			case 92:
                adjustmentstimes = 32;
                break;
			case 93:
                adjustmentstimes = 33;
                break;
			case 94:
                adjustmentstimes = 34;
                break;
			case 95:
                adjustmentstimes = 35;
                break;
			case 96:
                adjustmentstimes = 36;
                break;
			case 97:
                adjustmentstimes = 37;
                break;
			case 98:
                adjustmentstimes = 38;
                break;
			case 99:
                adjustmentstimes = 39;
                break;
			case 100:
                adjustmentstimes = 40;
                break;
			case 101:
                adjustmentstimes = 41;
                break;
			case 102:
                adjustmentstimes = 42;
                break;
			case 103:
                adjustmentstimes = 43;
                break;
			case 104:
                adjustmentstimes = 44;
                break;
			case 105:
                adjustmentstimes = 45;
                break;
			case 106:
                adjustmentstimes = 46;
                break;
			case 107:
                adjustmentstimes = 47;
                break;
			case 108:
                adjustmentstimes = 48;
                break;
			case 109:
                adjustmentstimes = 49;
                break;
			case 110:
                adjustmentstimes = 50;
                break;
			case 111:
                adjustmentstimes = 51;
                break;
			case 112:
                adjustmentstimes = 52;
                break;
			case 113:
                adjustmentstimes = 53;
                break;
			case 114:
                adjustmentstimes = 54;
                break;
			case 115:
                adjustmentstimes = 55;
                break;
			case 116:
                adjustmentstimes = 56;
                break;
			case 117:
                adjustmentstimes = 57;
                break;
			case 118:
                adjustmentstimes = 58;
                break;
			case 119:
                adjustmentstimes = 59;
                break;
			case 120:
                adjustmentstimes = 60;
                break;

            default:
                adjustmentstimes = 0;
                break;
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        gson = new Gson();
        try {
            editor.putString(ktime, gson.toJson(Integer.valueOf(adjustmentstimes)));
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error adjustments saving asr method to json.");
            e.getStackTrace();
        }}
		
		//
    private void updateNotifs() {
        boolean notifsCheck = notifsOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myNotifsOpt", notifsCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving notifs option.");
            e.getStackTrace();
        }
    }

    private void updateAdhan() {
        boolean adhanCheck = adhanOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myAdhanOpt", adhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }
    }

    private void updateFajrAdhan() {
        boolean fajrAdhanCheck = fajrOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myFajrAdhanOpt", fajrAdhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }
    }

    private void updateDhuhrAdhan() {
        boolean dhuhrAdhanCheck = dhuhrOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myDhuhrAdhanOpt", dhuhrAdhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }
    }

    private void updateAsrAdhan() {
        boolean asrAdhanCheck = asrOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myAsrAdhanOpt", asrAdhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }
    }

    private void updateMaghribAdhan() {
        boolean maghribAdhanCheck = maghribOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myMaghribAdhanOpt", maghribAdhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }
    }

    private void updateIshaAdhan() {
        boolean ishaAdhanCheck = ishaOpt.isChecked();
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            editor.putBoolean("myIshaAdhanOpt", ishaAdhanCheck);
            editor.commit();
        } catch (Exception e) {
            System.out.println("An error has occured saving adhan option");
            e.getStackTrace();
        }}
		
    

    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    protected boolean isValidFragment(String fragmentName) {
        return fragmentsList.contains(fragmentName);
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 4;
    }

    private static boolean isSimplePreferences(Context context) {
        return VERSION.SDK_INT < 11 || !isXLargeTablet(context);
    }

    @TargetApi(11)
    public void onBuildHeaders(List<Header> target) {
        if (!isSimplePreferences(this)) {
            loadHeadersFromResource(R.xml.pref_headers, target);
        }
        for (Header h : target) {
            fragmentsList.add(h.fragment);
        }
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }
	

	
	
}
