package com.PrayerTimes;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class PrayTime {
    public int AngleBased;
    public int Custom;
    public int Egypt;
    public int Floating;
    public int Hanafi;
    public int ISNA;
    public String InvalidTime;
    public double JDate;
    int Jafari;
    public int Karachi;
    int MWL;
    public int Makkah;
    public int MidNight;
    public int None;
    public int OneSeventh;
    public int Shafii;
    public int Tehran;
    public int Time12;
    public int Time12NS;
    public int Time24;
    public int adjustHighLats;
    public int asrJuristic;
    public int calcMethod;
    public int dhuhrMinutes;
    public double lat;
    public double lng;
    public HashMap<Integer, double[]> methodParams;
    public int numIterations;
    public int[] offsets;
    public double[] prayerTimesCurrent;
    public int timeFormat;
    public ArrayList<String> timeNames = new ArrayList();
    public double timeZone;
    public int uoif;

    public PrayTime() {
        setCalcMethod(0);
        setAsrJuristic(0);
        setDhuhrMinutes(0);
        setAdjustHighLats(1);
        setTimeFormat(0);
        setJafari(0);
        setKarachi(1);
        setISNA(2);
        setMWL(3);
        setMakkah(4);
        setEgypt(5);
        setTehran(6);
        setCustom(7);
        setUoif(8);
        setShafii(0);
        setHanafi(1);
        setNone(0);
        setMidNight(1);
        setOneSeventh(2);
        setAngleBased(3);
        setTime24(0);
        setTime12(1);
        setTime12NS(2);
        setFloating(3);
        this.timeNames.add("Fajr");
        this.timeNames.add("Sunrise");
        this.timeNames.add("Dhuhr");
        this.timeNames.add("Asr");
        this.timeNames.add("Sunset");
        this.timeNames.add("Maghrib");
        this.timeNames.add("Isha");
        this.InvalidTime = "-----";
        setNumIterations(1);
        this.offsets = new int[7];
        this.offsets[0] = 0;
        this.offsets[1] = 0;
        this.offsets[2] = 0;
        this.offsets[3] = 0;
        this.offsets[4] = 0;
        this.offsets[5] = 0;
        this.offsets[6] = 0;
        this.methodParams = new HashMap();
        this.methodParams.put(Integer.valueOf(getJafari()), new double[]{16.0d, 0.0d, 4.0d, 0.0d, 14.0d});
        this.methodParams.put(Integer.valueOf(getKarachi()), new double[]{18.0d, 1.0d, 0.0d, 0.0d, 18.0d});
        this.methodParams.put(Integer.valueOf(getISNA()), new double[]{15.0d, 1.0d, 0.0d, 0.0d, 15.0d});
        this.methodParams.put(Integer.valueOf(getMWL()), new double[]{18.0d, 1.0d, 0.0d, 0.0d, 17.0d});
        this.methodParams.put(Integer.valueOf(getMakkah()), new double[]{18.5d, 1.0d, 0.0d, 1.0d, 90.0d});
        this.methodParams.put(Integer.valueOf(getEgypt()), new double[]{19.5d, 1.0d, 0.0d, 0.0d, 17.5d});
        this.methodParams.put(Integer.valueOf(getTehran()), new double[]{17.7d, 0.0d, 4.5d, 0.0d, 14.0d});
        this.methodParams.put(Integer.valueOf(getCustom()), new double[]{18.0d, 1.0d, 0.0d, 0.0d, 17.0d});
        this.methodParams.put(Integer.valueOf(getUoif()), new double[]{12.0d, 0.0d, 0.0d, 0.0d, 12.0d});
    }

    public void addNewMethod(int method, double[] values) {
        this.methodParams.put(Integer.valueOf(method), values);
    }

    public double fixangle(double a) {
        a -= Math.floor(a / 360.0d) * 360.0d;
        if (a < 0.0d) {
            return a + 360.0d;
        }
        return a;
    }

    public double fixhour(double a) {
        a -= Math.floor(a / 24.0d) * 24.0d;
        if (a < 0.0d) {
            return a + 24.0d;
        }
        return a;
    }

    public double radiansToDegrees(double alpha) {
        return (180.0d * alpha) / 3.141592653589793d;
    }

    public double DegreesToRadians(double alpha) {
        return (3.141592653589793d * alpha) / 180.0d;
    }

    public double dsin(double d) {
        return Math.sin(DegreesToRadians(d));
    }

    public double dcos(double d) {
        return Math.cos(DegreesToRadians(d));
    }

    public double dtan(double d) {
        return Math.tan(DegreesToRadians(d));
    }

    public double darcsin(double x) {
        return radiansToDegrees(Math.asin(x));
    }

    public double darccos(double x) {
        return radiansToDegrees(Math.acos(x));
    }

    public double darctan(double x) {
        return radiansToDegrees(Math.atan(x));
    }

    public double darctan2(double y, double x) {
        return radiansToDegrees(Math.atan2(y, x));
    }

    public double darccot(double x) {
        return radiansToDegrees(Math.atan2(1.0d, x));
    }

    public double getTimeZone1() {
        return (((double) TimeZone.getDefault().getRawOffset()) / 1000.0d) / 3600.0d;
    }

    public double getBaseTimeZone() {
        return (((double) TimeZone.getDefault().getRawOffset()) / 1000.0d) / 3600.0d;
    }

    public double detectDaylightSaving() {
        return (double) TimeZone.getDefault().getDSTSavings();
    }

    public double julianDate(int year, int month, int day) {
        if (month <= 2) {
            year--;
            month += 12;
        }
        double A = Math.floor(((double) year) / 100.0d);
        return (((Math.floor(365.25d * ((double) (year + 4716))) + Math.floor(30.6001d * ((double) (month + 1)))) + ((double) day)) + ((2.0d - A) + Math.floor(A / 4.0d))) - 1524.5d;
    }

    public double calcJD(int year, int month, int day) {
        return (2440588.0d + Math.floor(((double) new Date(year, month - 1, day).getTime()) / 8.64E7d)) - 0.5d;
    }

    public double[] sunPosition(double jd) {
        double D = jd - 2451545.0d;
        double g = fixangle(357.529d + (0.98560028d * D));
        double q = fixangle(280.459d + (0.98564736d * D));
        double L = fixangle(((1.915d * dsin(g)) + q) + (0.02d * dsin(2.0d * g)));
        double e = 23.439d - (3.6E-7d * D);
        double d = darcsin(dsin(e) * dsin(L));
        double d2 = q / 15.0d;
        double EqT = d2 - fixhour(darctan2(dcos(e) * dsin(L), dcos(L)) / 15.0d);
        return new double[]{d, EqT};
    }

    public double equationOfTime(double jd) {
        return sunPosition(jd)[1];
    }

    public double sunDeclination(double jd) {
        return sunPosition(jd)[0];
    }

    public double computeMidDay(double t) {
        return fixhour(12.0d - equationOfTime(getJDate() + t));
    }

    public double computeTime(double G, double t) {
        double D = sunDeclination(getJDate() + t);
        double Z = computeMidDay(t);
        double V = darccos(((-dsin(G)) - (dsin(D) * dsin(getLat()))) / (dcos(D) * dcos(getLat()))) / 15.0d;
        if (G > 90.0d) {
            V = -V;
        }
        return Z + V;
    }

    public double computeAsr(double step, double t) {
        return computeTime(-darccot(dtan(Math.abs(getLat() - sunDeclination(getJDate() + t))) + step), t);
    }

    public double timeDiff(double time1, double time2) {
        return fixhour(time2 - time1);
    }

    public ArrayList<String> getDatePrayerTimes(int year, int month, int day, double latitude, double longitude, double tZone) {
        setLat(latitude);
        setLng(longitude);
        setTimeZone(tZone);
        setJDate(julianDate(year, month, day));
        setJDate(getJDate() - (longitude / 360.0d));
        return computeDayTimes();
    }

    public ArrayList<String> getPrayerTimes(Calendar date, double latitude, double longitude, double tZone) {
        return getDatePrayerTimes(date.get(1), date.get(2) + 1, date.get(5), latitude, longitude, tZone);
    }

    public void setCustomParams(double[] params) {
        for (int i = 0; i < 5; i++) {
            if (params[i] == -1.0d) {
                params[i] = ((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[i];
                this.methodParams.put(Integer.valueOf(getCustom()), params);
            } else {
                ((double[]) this.methodParams.get(Integer.valueOf(getCustom())))[i] = params[i];
            }
        }
        setCalcMethod(getCustom());
    }

    public void setFajrAngle(double angle) {
        setCustomParams(new double[]{angle, -1.0d, -1.0d, -1.0d, -1.0d});
    }

    public void setMaghribAngle(double angle) {
        setCustomParams(new double[]{-1.0d, 0.0d, angle, -1.0d, -1.0d});
    }

    public void setIshaAngle(double angle) {
        setCustomParams(new double[]{-1.0d, -1.0d, -1.0d, 0.0d, angle});
    }

    public void setMaghribMinutes(double minutes) {
        setCustomParams(new double[]{-1.0d, 1.0d, minutes, -1.0d, -1.0d});
    }

    public void setIshaMinutes(double minutes) {
        setCustomParams(new double[]{-1.0d, -1.0d, -1.0d, 1.0d, minutes});
    }

    public String floatToTime24(double time) {
        if (Double.isNaN(time)) {
            return this.InvalidTime;
        }
        time = fixhour(0.008333333333333333d + time);
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - ((double) hours)) * 60.0d);
        if (hours >= 0 && hours <= 9 && minutes >= 0.0d && minutes <= 9.0d) {
            return "0" + hours + ":0" + Math.round(minutes);
        }
        if (hours >= 0 && hours <= 9) {
            return "0" + hours + ":" + Math.round(minutes);
        }
        if (minutes < 0.0d || minutes > 9.0d) {
            return hours + ":" + Math.round(minutes);
        }
        return hours + ":0" + Math.round(minutes);
    }

    public String floatToTime12(double time, boolean noSuffix) {
        if (Double.isNaN(time)) {
            return this.InvalidTime;
        }
        String suffix;
        time = fixhour(0.008333333333333333d + time);
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - ((double) hours)) * 60.0d);
        if (hours >= 12) {
            suffix = "PM";
        } else {
            suffix = "AM";
        }
        hours = (((hours + 12) - 1) % 12) + 1;
        if (noSuffix) {
            if (hours >= 0 && hours <= 9 && minutes >= 0.0d && minutes <= 9.0d) {
                return "0" + hours + ":0" + Math.round(minutes);
            }
            if (hours >= 0 && hours <= 9) {
                return "0" + hours + ":" + Math.round(minutes);
            }
            if (minutes < 0.0d || minutes > 9.0d) {
                return hours + ":" + Math.round(minutes);
            }
            return hours + ":0" + Math.round(minutes);
        } else if (hours >= 0 && hours <= 9 && minutes >= 0.0d && minutes <= 9.0d) {
            return "0" + hours + ":0" + Math.round(minutes) + " " + suffix;
        } else {
            if (hours >= 0 && hours <= 9) {
                return "0" + hours + ":" + Math.round(minutes) + " " + suffix;
            }
            if (minutes < 0.0d || minutes > 9.0d) {
                return hours + ":" + Math.round(minutes) + " " + suffix;
            }
            return hours + ":0" + Math.round(minutes) + " " + suffix;
        }
    }

    public String floatToTime12NS(double time) {
        return floatToTime12(time, true);
    }

    public double[] computeTimes(double[] times) {
        double[] t = dayPortion(times);
        double Fajr = computeTime(180.0d - ((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[0], t[0]);
        double Sunrise = computeTime(179.167d, t[1]);
        double Dhuhr = computeMidDay(t[2]);
        double Asr = computeAsr((double) (getAsrJuristic() + 1), t[3]);
        double Sunset = computeTime(0.833d, t[4]);
        double Maghrib = computeTime(((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[2], t[5]);
        double Isha = computeTime(((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[4], t[6]);
        return new double[]{Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha};
    }

    public ArrayList<String> computeDayTimes() {
        double[] times = new double[]{5.0d, 6.0d, 12.0d, 13.0d, 18.0d, 18.0d, 18.0d};
        for (int i = 1; i <= getNumIterations(); i++) {
            times = computeTimes(times);
        }
        return adjustTimesFormat(tuneTimes(adjustTimes(times)));
    }

    public double[] adjustTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + (getTimeZone() - (getLng() / 15.0d));
        }
        times[2] = times[2] + ((double) (getDhuhrMinutes() / 60));
        if (((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[1] == 1.0d) {
            times[5] = times[4] + (((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[2] / 60.0d);
        }
        if (((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[3] == 1.0d) {
            times[6] = times[5] + (((double[]) this.methodParams.get(Integer.valueOf(getCalcMethod())))[4] / 60.0d);
        }
        if (getAdjustHighLats() != getNone()) {
            return adjustHighLatTimes(times);
        }
        return times;
    }

    public ArrayList<String> adjustTimesFormat(double[] times) {
        ArrayList<String> result = new ArrayList();
        if (getTimeFormat() == getFloating()) {
            for (double time : times) {
                result.add(String.valueOf(time));
            }
        } else {
            for (int i = 0; i < 7; i++) {
                if (getTimeFormat() == getTime12()) {
                    result.add(floatToTime12(times[i], false));
                } else if (getTimeFormat() == getTime12NS()) {
                    result.add(floatToTime12(times[i], true));
                } else {
                    result.add(floatToTime24(times[i]));
                }
            }
        }
        return result;
    }
	public double[] adjustHighLatTimes(double[] times) {
        double nightTime = timeDiff(times[4], times[1]); // sunset to sunrise

        // Adjust Fajr
        double FajrDiff = nightPortion(methodParams.get(this.getCalcMethod())[0]) * nightTime;

        if (Double.isNaN(times[0]) || timeDiff(times[0], times[1]) > FajrDiff) {
            times[0] = times[1] - FajrDiff;
        }

        // Adjust Isha
        double IshaAngle = (methodParams.get(this.getCalcMethod())[3] == 0) ? methodParams.get(this.getCalcMethod())[4] : 18;
        double IshaDiff = this.nightPortion(IshaAngle) * nightTime;
        if (Double.isNaN(times[6]) || this.timeDiff(times[4], times[6]) > IshaDiff) {
            times[6] = times[4] + IshaDiff;
        }

        // Adjust Maghrib
        double MaghribAngle = (methodParams.get(this.getCalcMethod())[1] == 0) ? methodParams.get(this.getCalcMethod())[2] : 4;
        double MaghribDiff = nightPortion(MaghribAngle) * nightTime;
        if (Double.isNaN(times[5]) || this.timeDiff(times[4], times[5]) > MaghribDiff) {
            times[5] = times[4] + MaghribDiff;
        }

        return times;

	}

    public double nightPortion(double angle) {
        if (this.adjustHighLats == this.AngleBased) {
            return angle / 60.0d;
        }
        if (this.adjustHighLats == this.MidNight) {
            return 0.5d;
        }
        if (this.adjustHighLats == this.OneSeventh) {
            return 0.14286d;
        }
        return 0.0d;
    }

    public double[] dayPortion(double[] times) {
        for (int i = 0; i < 7; i++) {
            times[i] = times[i] / 24.0d;
        }
        return times;
    }

    public void tune(int[] offsetTimes) {
        for (int i = 0; i < offsetTimes.length; i++) {
            this.offsets[i] = offsetTimes[i];
        }
    }

    public double[] tuneTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + (((double) this.offsets[i]) / 60.0d);
        }
        return times;
    }

    public static void main(String[] args) {
        PrayTime prayers = new PrayTime();
        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Egypt);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        prayers.tune(new int[]{0, 0, 0, 0, 0, 0, 0});
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal, -37.823689d, 145.121597d, 10.0d);
        ArrayList<String> prayerNames = prayers.getTimeNames();
        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(((String) prayerNames.get(i)) + " - " + ((String) prayerTimes.get(i)));
        }
    }

    public int getCalcMethod() {
        return this.calcMethod;
    }

    public void setCalcMethod(int calcMethod) {
        this.calcMethod = calcMethod;
    }

    public int getAsrJuristic() {
        return this.asrJuristic;
    }

    public void setAsrJuristic(int asrJuristic) {
        this.asrJuristic = asrJuristic;
    }

    public int getDhuhrMinutes() {
        return this.dhuhrMinutes;
    }

    public void setDhuhrMinutes(int dhuhrMinutes) {
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public int getAdjustHighLats() {
        return this.adjustHighLats;
    }

    public void setAdjustHighLats(int adjustHighLats) {
        this.adjustHighLats = adjustHighLats;
    }

    public int getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public double getJDate() {
        return this.JDate;
    }

    public void setJDate(double jDate) {
        this.JDate = jDate;
    }

    public int getJafari() {
        return this.Jafari;
    }

    public void setJafari(int jafari) {
        this.Jafari = jafari;
    }

    public int getKarachi() {
        return this.Karachi;
    }

    public void setKarachi(int karachi) {
        this.Karachi = karachi;
    }

    public int getISNA() {
        return this.ISNA;
    }

    public void setISNA(int iSNA) {
        this.ISNA = iSNA;
    }

    public int getMWL() {
        return this.MWL;
    }

    public void setMWL(int mWL) {
        this.MWL = mWL;
    }

    public int getMakkah() {
        return this.Makkah;
    }

    public void setMakkah(int makkah) {
        this.Makkah = makkah;
    }

    public int getEgypt() {
        return this.Egypt;
    }

    public void setEgypt(int egypt) {
        this.Egypt = egypt;
    }

    public int getCustom() {
        return this.Custom;
    }

    public void setCustom(int custom) {
        this.Custom = custom;
    }

    public int getTehran() {
        return this.Tehran;
    }

    public void setTehran(int tehran) {
        this.Tehran = tehran;
    }

    public int getUoif() {
        return this.uoif;
    }

    public void setUoif(int uoif) {
        this.uoif = uoif;
    }

    public int getShafii() {
        return this.Shafii;
    }

    public void setShafii(int shafii) {
        this.Shafii = shafii;
    }

    public int getHanafi() {
        return this.Hanafi;
    }

    public void setHanafi(int hanafi) {
        this.Hanafi = hanafi;
    }

    public int getNone() {
        return this.None;
    }

    public void setNone(int none) {
        this.None = none;
    }

    public int getMidNight() {
        return this.MidNight;
    }

    public void setMidNight(int midNight) {
        this.MidNight = midNight;
    }

    public int getOneSeventh() {
        return this.OneSeventh;
    }

    public void setOneSeventh(int oneSeventh) {
        this.OneSeventh = oneSeventh;
    }

    public int getAngleBased() {
        return this.AngleBased;
    }

    public void setAngleBased(int angleBased) {
        this.AngleBased = angleBased;
    }

    public int getTime24() {
        return this.Time24;
    }

    public void setTime24(int time24) {
        this.Time24 = time24;
    }

    public int getTime12() {
        return this.Time12;
    }

    public void setTime12(int time12) {
        this.Time12 = time12;
    }

    public int getTime12NS() {
        return this.Time12NS;
    }

    public void setTime12NS(int time12ns) {
        this.Time12NS = time12ns;
    }

    public int getFloating() {
        return this.Floating;
    }

    public void setFloating(int floating) {
        this.Floating = floating;
    }

    public int getNumIterations() {
        return this.numIterations;
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public ArrayList<String> getTimeNames() {
        return this.timeNames;
    }
}



