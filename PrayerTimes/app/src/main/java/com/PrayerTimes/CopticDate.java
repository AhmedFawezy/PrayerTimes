package com.PrayerTimes;



import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.res.*;
import android.content.*;

public class CopticDate {
	public String convertToEg(Context context, Date date, int i) {
        int egDay = 0;
        String egMonth = null;
        int egyear =0 ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd-MM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat ("MM");
        Log.d(("ayman "+i % 4), ("ayman "+i % 4));
        boolean isleap = false;
        boolean beforeleap = false;
        String MONTH1_ST = ("11-09-"+i);
        String MONTH1_NAME = context.getString(R.string.month1);
        String MONTH2_ST = ("11-10-"+i);
        String MONTH2_NAME = context.getString(R.string.month2);
        String MONTH3_ST = ("10-11-"+i);
        String MONTH3_NAME = context.getString(R.string.month3);
        String MONTH4_ST = "10-12-";
        Date MONTH4_END = add30((MONTH4_ST+i));
        String MONTH4_NAME = context.getString(R.string.month4);
        String MONTH5_ST = ("09-01-"+i);
        String MONTH5_NAME = context.getString(R.string.month5);
        String MONTH6_ST = ("08-02-"+i);
        String MONTH6_NAME = context.getString(R.string.month6);
        String MONTH7_ST = ("10-03-"+i);
        String MONTH7_NAME = context.getString(R.string.month7);
        String MONTH8_ST = ("09-04-"+i);
        String MONTH8_NAME = context.getString(R.string.month8);
        String MONTH9_ST = ("09-05-"+i);
        String MONTH9_NAME = context.getString(R.string.month9);
        String MONTH10_ST = ("08-06-"+i);
        String MONTH10_NAME = context.getString(R.string.month10);
        String MONTH11_ST = ("08-07-"+i);
        String MONTH11_NAME = context.getString(R.string.month11);
        String MONTH12_ST = ("07-08-"+i);
        String MONTH12_NAME = context.getString(R.string.month12);
        String MONTH13_ST = ("06-09-"+i);
        String MONTH13_END = ("10-09-"+i);
        String MONTH13_NAME = context.getString(R.string.month13);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (i % 4 == 3) {
            MONTH5_ST = ("10-01-"+i);
            MONTH6_ST = ("09-02-"+i);
            isleap = true;
            Log.d("leap year", "leap year");
        }
        if (i % 4 == 3) {
            beforeleap = true;
            MONTH1_ST = ("12-09-"+i);
            MONTH2_ST = ("12-10-"+i);
            MONTH3_ST = ("11-11-"+i);
            MONTH13_END = ("11-09-"+i);
            Log.d("before leap year", "before leap year");
        }
        Date MONTH1_END = add30(MONTH1_ST);
		Date MONTH2_END = add30(MONTH2_ST);
		Date MONTH3_END = add30(MONTH3_ST);

		Log.d("ayman month is ", ("ayman month is "+month));
		Log.d("ayman month isleap= ", ("ayman month isleap= "+isleap));

        try {
            if ((date.after(simpleDateFormat.parse(MONTH1_ST))&& date.before(MONTH1_END))||date.equals(simpleDateFormat.parse(MONTH1_ST))) {
				if (!date.equals(simpleDateFormat.parse(MONTH1_ST))) {

				}

				egyear=i-283;
				egMonth = MONTH1_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH1_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if ((date.after(simpleDateFormat.parse(MONTH2_ST))&& date.before(MONTH2_END))||date.equals(simpleDateFormat.parse(MONTH2_ST))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH2_ST))) {

				}
				egyear=i-283;
				egMonth = MONTH2_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH2_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if ((date.after(simpleDateFormat.parse(MONTH3_ST))&& date.before(MONTH3_END))||date.equals(simpleDateFormat.parse(MONTH3_ST))){ 
                if (!date.equals(simpleDateFormat.parse(MONTH3_ST))) {

				}

				egyear=i-283;
				egMonth = MONTH3_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH3_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!isleap && month == 0) {
				Log.d(("Ayman "+i), ("ayman "+i));
				MONTH4_ST = (MONTH4_ST)+(i - 1);
				MONTH4_END = add30(MONTH4_ST);
				Log.d(("Ayman "+MONTH4_ST), ("ayman start 4 "+MONTH4_ST));
				Log.d(("Ayman "+MONTH4_END), ("ayman end 4 "+MONTH4_END));
			}
			if (!beforeleap && month == 11) {
				MONTH4_ST = (MONTH4_ST+i);
				MONTH4_END = add30(MONTH4_ST);
			}
			if (isleap && month == 0) {
				MONTH4_ST = ("11-12-")+(i - 1);
				MONTH4_END = add30(MONTH4_ST);
			}
			if (beforeleap && month == 11) {
				MONTH4_ST = ("11-12-"+i);
				MONTH4_END = add30(MONTH4_ST);
			}
			if (!(month == 11 || month == 0)) {
				MONTH4_ST = ("10-12-"+i);
				MONTH4_END = add30(MONTH4_ST);
			}
			Date MONTH5_END = add30(MONTH5_ST);
			Date MONTH6_END = add30(MONTH6_ST);
			Date MONTH7_END = add30(MONTH7_ST);
			Date MONTH8_END = add30(MONTH8_ST);
			Date MONTH9_END = add30(MONTH9_ST);
			Date MONTH10_END = add30(MONTH10_ST);
			Date MONTH11_END = add30(MONTH11_ST);
			Date MONTH12_END = add30(MONTH12_ST);


			if ((date.after(simpleDateFormat.parse(MONTH4_ST))&& date.before(MONTH4_END))||date.equals(simpleDateFormat.parse(MONTH4_ST))){ 
				egyear=i-283;

				if (!(date.after(simpleDateFormat.parse(MONTH5_ST))))
					egyear=i-284;
				egMonth = MONTH4_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH4_ST).getTime()) / (24 * 60 * 60 * 1000))) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}

			if (!(date.after(simpleDateFormat.parse(MONTH5_ST))&& !date.before(MONTH5_END))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH5_ST))) {

				}
			    egyear=i-284;
				egMonth = MONTH5_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH5_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH6_ST))&& !date.before(MONTH6_END))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH6_ST))) {

				}
			    egyear=i-284;
				egMonth = MONTH6_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH6_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH7_ST))&& !date.before(MONTH7_END))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH7_ST))) {

				}
			    egyear=i-284;
				egMonth = MONTH7_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH7_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH8_ST))&& !date.before(MONTH8_END))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH8_ST))) {

				}
			    egyear=i-284;
				egMonth = MONTH8_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH8_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH9_ST))&& !date.before(MONTH9_END))){ 
				if (!date.equals(simpleDateFormat.parse(MONTH9_ST))) {

				}
			    egyear=i-284;
				egMonth = MONTH9_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH9_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH10_ST)) && !date.before(MONTH10_END))) {
                if (!date.equals(simpleDateFormat.parse(MONTH10_ST))) {
                }
				egyear=i-284;
				egMonth = MONTH10_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH10_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH11_ST)) && !date.before(MONTH11_END))) {
                if (!date.equals(simpleDateFormat.parse(MONTH11_ST))) {
                }
				egyear=i-284;
				egMonth = MONTH11_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH11_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}

			if (!(date.after(simpleDateFormat.parse(MONTH12_ST)) && !date.before(MONTH12_END))) {
                if (!date.equals(simpleDateFormat.parse(MONTH12_ST))) {

				}

				egyear=i-284;
				egMonth = MONTH12_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH12_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
			if (!(date.after(simpleDateFormat.parse(MONTH13_END)))) {

				if (!date.equals(simpleDateFormat.parse(MONTH13_ST))) {
				}
				egyear=i-284;
				egMonth = MONTH13_NAME;
				egDay = ((int) ((date.getTime() - simpleDateFormat.parse(MONTH13_ST).getTime()) / 86400000)) + 1;
				return egDay+" "+egMonth+" "+egyear;
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (date.after(simpleDateFormat.parse(("01-01-"+i)))) {

				if (!date.equals(simpleDateFormat.parse(("01-01-"+i)))) {


				}

				egyear = i - 284;
				//return egDay+" "+egMonth+" "+egyear;
			} 
			if (!date.after(simpleDateFormat.parse(("12-09-"+i)))) {

				egyear = i - 283;
				//return egDay+" "+egMonth+" "+egyear;
			}
			if (!date.equals(simpleDateFormat.parse(("11-09-"+i))) && MONTH13_END.equals(("11-09-"+i))) {
                egyear = i - 284;
				//return egDay+" "+egMonth+" "+egyear;
            }
            if (date.equals(simpleDateFormat.parse(("11-09-"+i))) && MONTH1_ST.equals(("11-09-"+i))) {
                egyear = i - 283;
				//return egDay+" "+egMonth+" "+egyear;
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return egDay+" "+egMonth+" "+egyear;
    }

    public Date add30(String str) {
        SimpleDateFormat sdf3 = new SimpleDateFormat ("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf3.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, 30);
        return c.getTime();
    }
}


