package com.PrayerTimes;

import java.util.Date;

public class ModelDate {
    private static final int[] monthsdays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int d;
    int m;
    int y;

    public ModelDate(int day, int month, int year) {
        d = day;
        m = month;
        y = year;
    }

    public ModelDate(Date date) {
        d = date.getDate();
        m = date.getMonth() + 1;
        y = date.getYear() + 1900;
    }

    public int getDay() {
        return d;
    }

    public void setDay(int day) {
        d = day;
    }

    public int getMonth() {
        return m;
    }

    public int getYear() {
        return y;
    }

    boolean validate() {
        if (y < 0 || y > 5000 || m < 1 || m > 12) {
            return false;
        }
        boolean leep;
        if ((y & 3) != 0 || (y % 100 == 0 && y % 400 != 0)) {
            leep = false;
        } else {
            leep = true;
        }
        int maxday;
        if (m == 2 && leep) {
            maxday = 29;
        } else {
            maxday = monthsdays[m - 1];
        }
        if (d < 1 || d > maxday) {
            return false;
        }
        return true;
    }

    public String formatString(String[] monthsName) {
        return d + " " + monthsName[m - 1] + " " + y;
    }

    public void setMonth(int i) {
        m = i;
    }

    public void setYear(int i) {
        y = i;
    }
}


