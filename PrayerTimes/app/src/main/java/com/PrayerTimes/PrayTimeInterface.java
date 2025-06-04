package com.PrayerTimes;


import android.content.Context;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.*;
import java.util.*;

public class PrayTimeInterface {
    PrayTime p = new PrayTime();
    ArrayList<String> prayerTimes;
    PrefsInterface ps;

    public PrayTimeInterface(Context ctx) {
        ps = new PrefsInterface(ctx);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int[] offsetsdst = {ps.getOffsets()[0]+60,ps.getOffsets()[1]+60,ps.getOffsets()[2]+60,ps.getOffsets()[3]+60,ps.getOffsets()[4]+60,ps.getOffsets()[5]+60,ps.getOffsets()[6]+60};
		int[] offsetsdst1 = {+60,60,60,60,60,60,60};
		if(ps.myDst()==true){
			p.tune(offsetsdst);
			
		}
		else {
			p.tune(ps.getOffsets());
		}
        
        p.setTimeFormat(ps.timeformat());
        p.setCalcMethod(ps.getCalcMethod());
        p.setAsrJuristic(ps.getAsrMethod());
        p.setAdjustHighLats(ps.highlatitudes());
        if (ps.getCityObj() != null) {
            prayerTimes = p.getPrayerTimes(cal, getLat(), getLon(), getTimeZone());
        }
    }

    public CityObj getCityObj() {
        return ps.getCityObj();
    }

    public String getFajr() {
        if (ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) prayerTimes.get(0);
    }

    public String getSunrise() {
        if (ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) prayerTimes.get(1);
    }

   /* public String getDhuhr() {
		if (ps.getCityObj() == null) {
			return "--:--";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		Date d = new Date();
		String dayOfTheWeek = sdf.format(d);

		if (dayOfTheWeek.equals("Friday")) {
			return (String) prayerTimes.get(2).replace("Dhuhr", dayOfTheWeek);
		} else {
			return (String) prayerTimes.get(2);
		}
		
	}*/
	public String getDhuhr() {
        if (this.ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) this.prayerTimes.get(2);
    }
	

    public String getAsr() {
        if (this.ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) prayerTimes.get(3);
    }

    public String getMaghrib() {
        if (ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) prayerTimes.get(5);
    }

    public String getIsha() {
        if (ps.getCityObj() == null) {
            return "--:--";
        }
        return (String) prayerTimes.get(6);
    }

    public String getCity() {
        if (ps.getCityObj() == null) {
            return "--:--";
        }
        return ps.getCityName();
    }

    public double getLat() {
        return ps.getLat();
    }

    public double getLon() {
        return ps.getLon();
    }

    public double getTimeZone() {
		
        return ps.getTimeZone();
    }
	 
}
