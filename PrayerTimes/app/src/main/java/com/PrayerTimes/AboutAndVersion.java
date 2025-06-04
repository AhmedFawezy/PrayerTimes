package com.PrayerTimes;

 
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import java.io.*;

public class AboutAndVersion extends Activity {


	private LinearLayout linear1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview1;
	private LinearLayout linear4;
	private TextView textview2;
	private LinearLayout linear5;
	private TextView textview3;
	private LinearLayout linear6;
	private Button button1;

	private String fontName;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about_version);
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview3 = (TextView) findViewById(R.id.textview3);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					shareApplication();
				}
			});
	}
	private void initializeLogic() {
		//textview1.setSelected(true);
		_bglinear(linear4);
		//_bglinear(linear3);
		//_bglinear(linear5);
		setShadow(textview2);
		setShadow(textview1);
		//textview1.setText(Html.fromHtml("<font color =\"#FF000D\"><font size =\"11\">معلومات عن التطبيق  : </font></font><font color =\"#FFFFFF\"><font size =\"27\"> تطبيق </font><font color =\"#FF009404\"><font size =\"27\"> مواقيت الصلاة </font></font><font color =\"#FFFFFF\"><font size =\"27\"> إصدار </font></font><font color =\"#FFED00\"><font size =\"27\">1.2</font></font><font color =\"#FFFFFF\"><font size =\"11\"> تم برمجته </font></font><font color =\"#0E00FF\"><font size =\"11\"> بتاريخ </font></font><font color =\"#FFFFFF\"><font size =\"11\"> 25/3/2024 </font></font><font color =\"#FF7119\"><font size =\"11\"> تصميم وبرمجه </font></font><font color =\"#FFFFFF\"><font size =\"11\"> المطور :</font></font><font color =\"#7C22B9\"><font size =\"11\"> أحمد فوزي جلال </font></font>"));
		textview1.setText(Html.fromHtml(getString(R.string.infoapp)));
		fontName = "fonts/".concat("abbuline".concat(".otf")); 
		overrideFonts(this,getWindow().getDecorView());
		/////
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);

		switch (_requestCode) {

			default:
				break;
		}
	}

	//مشاركة التطبيق

    private void shareApplication() {
        String str = getApplicationContext().getApplicationInfo().sourceDir;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("*/*");
        java.io.File file = new java.io.File(str);
        java.io.File file2 = new java.io.File(getExternalCacheDir() + "/ExtractedApk");
        if (file2.isDirectory() || file2.mkdirs()) {
            java.io.File file3 = new java.io.File(file2.getPath() + "/" + getString(R.string.app_name)+".apk");
            if (!file3.exists()) {
                try {
                    if (!file3.createNewFile()) {
                        return;
                    }
                } catch (Exception e) {
                }
            }
            try {
                InputStream fileInputStream = new FileInputStream(file);
                OutputStream fileOutputStream = new FileOutputStream(file3);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        fileInputStream.close();
                        fileOutputStream.close();
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
                        startActivity(Intent.createChooser(intent, "صل علي النبي محمد ﷺ"));
                        return;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
            } catch (Exception e2) {
				Toast.makeText(getApplicationContext(),""+e2,1).show();
            }
        }
	}
	private void overrideFonts(final android.content.Context context, final View v) {

		try {
			Typeface  
				typeace = Typeface.createFromAsset(getAssets(), fontName);
			if ((v instanceof ViewGroup)) { 
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
					 i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				} 
			} 
			else { 
				if ((v instanceof TextView)) { 
					((TextView) v).setTypeface(typeace); 
				} 
				else { 
					if ((v instanceof EditText )) { 
						((EditText) v).setTypeface(typeace); 
					} 
					else { 
						if ((v instanceof Button)) { 
							((Button) v).setTypeface(typeace);
						} 
					} 
				} 
			} 
		} 
		catch(Exception e)

		{ 
			Toast.makeText(getApplicationContext(),getString(R.string.nofilefont),Toast.LENGTH_LONG).show();
		}

	}
	//ظل النص
	public void setShadow(TextView tview){
		tview.setShadowLayer((float)3,(float)3,(float)3,Color.GRAY);
	}
	
	private void _bglinear (final View _view) {
		int[] colorsCRNKC = { Color.parseColor("#009688"), Color.parseColor("#009688") }; android.graphics.drawable.GradientDrawable CRNKC = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNKC);
		CRNKC.setCornerRadii(new float[]{(int)20,(int)20,(int)20,(int)20,(int)20,(int)20,(int)20,(int)20});
		CRNKC.setStroke((int) 0, Color.parseColor("#8C8700"));
		_view.setElevation((float) 12);
		_view.setBackground(CRNKC);
	}



	 }
