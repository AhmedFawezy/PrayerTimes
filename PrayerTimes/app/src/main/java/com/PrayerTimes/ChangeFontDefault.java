package com.PrayerTimes;

import android.graphics.*;
import java.lang.reflect.*;
import android.content.*;
import android.graphics.Typeface;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
public class ChangeFontDefault extends AbstractContentProvider 
{
	private static final String TAG = ChangeFontDefault.class.getSimpleName();

    public boolean onCreate() {
        try {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/abbuline.otf");
            Map<String, Typeface> typefaces = new HashMap();
            typefaces.put("sans-serif", tf);
            typefaces.put("sans-serif-light", tf);
            typefaces.put("sans-serif-condensed", tf);
            typefaces.put("sans-serif-thin", tf);
            typefaces.put("sans-serif-medium", tf);
            Field field = Typeface.class.getDeclaredField("sSystemFontMap");
            field.setAccessible(true);
            Map<String, Typeface> oldFonts = (Map) field.get(null);
            if (oldFonts != null) {
                oldFonts.putAll(typefaces);
            } else {
                oldFonts = typefaces;
            }
            field.set(null, oldFonts);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return true;
    }
	

	
	public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
		try {
			final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

			final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
			defaultFontTypefaceField.setAccessible(true);
			defaultFontTypefaceField.set(null, customFontTypeface);
		} catch (Exception e) {

		}
	}



}
