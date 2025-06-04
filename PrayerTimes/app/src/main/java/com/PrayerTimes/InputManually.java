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
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.view.View;
import com.google.gson.*;

public class InputManually extends Activity {


	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext4;
	private Button button1;

	private Gson gson;
	String json = null;
	private SharedPreferences f;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manually);
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		button1 = (Button) findViewById(R.id.button1);
		f = getSharedPreferences("input", Activity.MODE_PRIVATE);
		gson = new Gson();
		button1.setOnClickListener(new View.OnClickListener() {
				

				@Override
				public void onClick(View _view) {
					Intent i = new Intent();
					f.edit().putString("k", edittext1.getText().toString()).commit();
					f.edit().putString("k1", edittext2.getText().toString()).commit();
					f.edit().putString("k2", edittext3.getText().toString()).commit();
					f.edit().putString("k3", edittext4.getText().toString()).commit();
					i.setClass(getApplicationContext(), SettingsActivity.class);
					i.putExtra("k", edittext1.getText().toString());
					i.putExtra("k1", edittext2.getText().toString());
					i.putExtra("k2", edittext3.getText().toString());
					i.putExtra("k3", edittext4.getText().toString());
					
					CityObj cityObj = new CityObj(
						edittext1.getText().toString(),
						Double.parseDouble(edittext2.getText().toString()),
						edittext3.getText().toString(),
						Double.parseDouble(edittext4.getText().toString()),
						Double.parseDouble(edittext4.getText().toString())
					);

					SharedPreferences.Editor editor = getPreferences(0).edit();
					editor.putString("cityJson", gson.toJson(cityObj)); // تأكيد المفتاح "cityJson" متسق مع الإسترجاع في onActivityResult
					editor.commit();
					
					
					String selectedCityString = gson.toJson(cityObj);
					
					i.putExtra("cityJson", selectedCityString); // المفتاح "cityJson" يجب أن يكون متطابقًا مع ما تستخدمه في onActivityResult
					setResult(RESULT_OK, i);
					finish();
					
				}
			});
		
	}
	private void initializeLogic() {
		edittext1.setText(f.getString("k", ""));
		edittext2.setText(f.getString("k1", ""));
		edittext3.setText(f.getString("k2", ""));
		edittext4.setText(f.getString("k3", ""));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

			default:
				break;
		}
	}}
	

