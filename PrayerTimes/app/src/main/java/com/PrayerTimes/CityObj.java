package com.PrayerTimes;


public class CityObj implements Comparable<CityObj> {
    public String city;
    public String country;
    public double latitude;
    public double longitude;
    public double timeZone;
	/*public String city="Cairo";
    public String country="Egypt";
    public double latitude=30.06263;
    public double longitude=31.24967;
    public double timeZone=2.0;*/
	
    public CityObj(String city, double timeZone, String country, double latitude, double longitude) {
        this.city = city;
        this.country = country;
        this.timeZone = timeZone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String city() {
        return this.city;
    }

    public String country() {
        return this.country;
    }

    public double timeZone() {
        return this.timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public double latitude() {
        return this.latitude;
    }

    public double longitude() {
        return this.longitude;
    }

    public int compareTo(CityObj another) {
        return this.city.compareTo(another.city());
    }

    public String toString() {
        if (this.city.equals("") || this.city == null) {
            return "Current Location";
        }
        return this.city;
    }
}
