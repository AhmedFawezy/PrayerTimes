package com.PrayerTimes;


import java.util.Date;
import java.math.*;

public class DateConversion {
	private static final double DEG2RAD = Math.PI / 180.0;

    public static ModelDate GregToIsl(Date date, int offset) {
        double jd;
        int d = date.getDate();
        int m = date.getMonth() + 1;
        int y = date.getYear() + 1900;
        if (y > 1582 || ((y == 1582 && m > 10) || (y == 1582 && m == 10 && d > 14))) {
            jd = ((((Math.floor((1461.0d * (((double) (y + 4800)) + Math.floor((double) ((m - 14) / 12)))) / 4.0d) + Math.floor((367.0d * (((double) (m - 2)) - (12.0d * Math.floor((double) ((m - 14) / 12))))) / 12.0d)) - Math.floor((3.0d * Math.floor((((double) (y + 4900)) + Math.floor((double) ((m - 14) / 12))) / 100.0d)) / 4.0d)) + ((double) d)) - 32075.0d) + 1.0d;
        } else {
            jd = ((((((double) (y * 367)) - Math.floor((7.0d * (((double) (y + 5001)) + Math.floor((double) ((m - 9) / 7)))) / 4.0d)) + Math.floor((double) ((m * 275) / 9))) + ((double) d)) + 1729777.0d) + 1.0d;
        }
        double l = ((jd + ((double) offset)) - 1948440.0d) + 10632.0d;
        double n = Math.floor((l - 1.0d) / 10631.0d);
        l = (l - (10631.0d * n)) + 354.0d;
        double j = (Math.floor((10985.0d - l) / 5316.0d) * Math.floor((50.0d * l) / 17719.0d)) + (Math.floor(l / 5670.0d) * Math.floor((43.0d * l) / 15238.0d));
        l = ((l - (Math.floor((30.0d - j) / 15.0d) * Math.floor((17719.0d * j) / 50.0d))) - (Math.floor(j / 16.0d) * Math.floor((15238.0d * j) / 43.0d))) + 29.0d;
        m = (int) Math.floor((24.0d * l) / 709.0d);
        return new ModelDate((int) (l - Math.floor((double) ((m * 709) / 24))), m, (int) (((30.0d * n) + j) - 30.0d));
    }

    public static Date IslToGreg(ModelDate date, int offset) {
        int d = date.getDay();
        int m = date.getMonth();
        int y = date.getYear();
        double jd = (((((((Math.floor((double) (((y * 11) + 3) / 30)) + ((double) (y * 354))) + ((double) (m * 30))) - Math.floor((double) ((m - 1) / 2))) + ((double) d)) + 1948440.0d) - 385.0d) - 1.0d) - ((double) offset);
        double l;
        double n;
        double i;
        double j;
        if (jd > 2299160.0d) {
            l = jd + 68569.0d;
            n = Math.floor((4.0d * l) / 146097.0d);
            l -= Math.floor(((146097.0d * n) + 3.0d) / 4.0d);
            i = Math.floor((4000.0d * (1.0d + l)) / 1461001.0d);
            l = (l - Math.floor((1461.0d * i) / 4.0d)) + 31.0d;
            j = Math.floor((80.0d * l) / 2447.0d);
            d = (int) (l - Math.floor((2447.0d * j) / 80.0d));
            l = Math.floor(j / 11.0d);
            m = (int) ((2.0d + j) - (12.0d * l));
            y = (int) (((100.0d * (n - 49.0d)) + i) + l);
        } else {
            j = jd + 1402.0d;
            double k = Math.floor((j - 1.0d) / 1461.0d);
            l = j - (1461.0d * k);
            n = Math.floor((l - 1.0d) / 365.0d) - Math.floor(l / 1461.0d);
            i = (l - (365.0d * n)) + 30.0d;
            j = Math.floor((80.0d * i) / 2447.0d);
            d = (int) (i - Math.floor((2447.0d * j) / 80.0d));
            i = Math.floor(j / 11.0d);
            m = (int) ((2.0d + j) - (12.0d * i));
            y = (int) ((((4.0d * k) + n) + i) - 4716.0d);
        }
        return new Date(y - 1900, m - 1, d);
    }

    public static int moonPhase(Date date) {
        double jd;
        int d = date.getDate();
        int m = date.getMonth() + 1;
        int y = date.getYear() + 1900;
        if (y > 1582 || ((y == 1582 && m > 10) || (y == 1582 && m == 10 && d > 14))) {
            jd = ((((Math.floor((1461.0d * (((double) (y + 4800)) + Math.floor((double) ((m - 14) / 12)))) / 4.0d) + Math.floor((367.0d * (((double) (m - 2)) - (12.0d * Math.floor((double) ((m - 14) / 12))))) / 12.0d)) - Math.floor((3.0d * Math.floor((((double) (y + 4900)) + Math.floor((double) ((m - 14) / 12))) / 100.0d)) / 4.0d)) + ((double) d)) - 32075.0d) + 1.0d;
        } else {
            jd = ((((((double) (y * 367)) - Math.floor((7.0d * (((double) (y + 5001)) + Math.floor((double) ((m - 9) / 7)))) / 4.0d)) + Math.floor((double) ((m * 275) / 9))) + ((double) d)) + 1729777.0d) + 1.0d;
        }
        jd /= 29.53d;
        return (int) ((8.0d * (jd - ((double) ((int) jd)))) + 0.5d);
    }
	//إضاءة القمر
	public static double getIllumination(double jd) {
		double t = (jd - 2451545.0) / 36525.0;
		double d = (((297.8502042 + (445267.11151686 * t)) - ((0.00163 * t) * t)) + (((t * t) * t) / 545868.0))
            - ((((t * t) * t) * t) / 1.13065E8);
		double m1 = (((134.9634114 + (477198.8676313 * t)) + ((0.008997 * t) * t)) + (((t * t) * t) / 69699.0))
            - ((((t * t) * t) * t) / 1.4712E7);
		return ((1.0 + CS(((((((180.0 - d) - (6.289 * SN(m1))) + (2.1 * SN(((357.5291092 + (35999.0502909 * t))
																		   - ((1.536E-4 * t) * t)) + (((t * t) * t) / 2.449E7)))) - (1.274 * SN((2.0 * d) - m1))) - (0.658 * SN(2.0 * d)))
						  - (0.241 * SN(2.0 * m1))) - (0.11 * SN(d)))) / 2.0) * 100.0;
	}

	public static double CS(double x) {
		return Math.cos(DEG2RAD * x);
	}

	public static double SN(double x) {
		return Math.sin(DEG2RAD * x);
	}
	//عمر القمر

    public static double getAge(Date date) {
        double julian = getJulian(date);
        julian -= getNewMoon(julian);
        if (julian < 0.0d) {
            julian += 29.530589d;
        }
        return new BigDecimal(julian).setScale(1, 4).doubleValue();
    }

	private static double getNewMoon(double d)
	{
		d = Math.floor((d - 2451550.09765d) / 29.530589d);
        double d2 = d / 1236.85d;
        return ((((29.530589d * d) + 2451550.09765d) + ((1.337E-4d * d2) * d2)) - (Math.sin(((385.8169d * d) + 201.5643d) * 0.017453292519943d) * 0.4072d)) + (Math.sin(((d * 29.1054d) + 2.5534d) * 0.017453292519943d) * 0.17241d);
    }

	//جوليان

    protected static double getJulian(Date date) {
        double time = (double) date.getTime();
        Double.isNaN(time);
        return (time / 8.64E7d) + 2440587.5d;
    }

    public static void main(String[] args) {
    }

    public static String formatGreg(Date date, String[] monthsName) {
        return date.getDate() + " " + monthsName[(date.getMonth() + 1) - 1] + " " + (date.getYear() + 1900);
    }
}


