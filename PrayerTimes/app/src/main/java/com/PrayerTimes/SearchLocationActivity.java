package com.PrayerTimes;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.graphics.*;
import android.view.*;
import android.widget.*;

public class SearchLocationActivity extends Activity implements TextWatcher, OnItemClickListener {
    private ArrayAdapter<CityObj> adapter;
    private ArrayList<CityObj> cities;
    private Gson gson;
    private ListView listView1;
    private EditText searchBox;
    private SharedPreferences sharedPref;

    protected void onCreate(Bundle savedInstanceState) {
		ChangeFontDefault.overrideFont(this, "SERIF", "fonts/abbuline.otf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
		
        cities = new ArrayList();
        sharedPref = getPreferences(0);
        gson = new Gson();
        String json = null;
        try {
            json = sharedPref.getString("myJson", "");
        } catch (Exception e) {
            System.out.println("Error retrieving data from memory.");
        }
        if (!json.isEmpty()) {
            cities = (ArrayList) gson.fromJson(json, new TypeToken<ArrayList<CityObj>>() {
														 }.getType());
        }
        if (cities.size() == 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("citydb.csv")));
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                    String name = stringTokenizer.nextToken();
                    String tz = stringTokenizer.nextToken();
                    String country = stringTokenizer.nextToken();
                    String lat = stringTokenizer.nextToken();
                    String lon = stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    cities.add(new CityObj(name, Double.parseDouble(tz), country, Double.parseDouble(lat), Double.parseDouble(lon)));
                }
                bufferedReader.close();
                Editor editor = getPreferences(0).edit();
                gson = new Gson();
                try {
                    editor.putString("myJson", gson.toJson(cities));
                    editor.commit();
                } catch (Exception e2) {
                    System.out.println("An error has occured saving to json.");
                    e2.getStackTrace();
                }
            } catch (IOException e3) {
                System.out.println("An error has occured reading from disk.");
                e3.getMessage();
            }
        }
        searchBox = (EditText) findViewById(R.id.searchTextBox);
        searchBox.addTextChangedListener(this);
        listView1 = (ListView) findViewById(R.id.listView1);
		//اضافة ايقونة برمجيا
		listView1.setVerticalScrollBarEnabled(false);
       searchBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons7,0,0,0);


        listView1.setOnItemClickListener(this);
        try {
           adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, cities);
			
        } catch (Exception e4) {
            System.out.println("Error creating an adapter for list view.");
        }
		
        listView1.setAdapter(adapter);

		int[] colors = {0, 0xFFFF0000, 0};
		listView1.setDivider(new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT, colors));
		listView1.setDividerHeight(1);
		listView1.setSelector(android.R.color.transparent);

		android.graphics.drawable.GradientDrawable style = new android.graphics.drawable.GradientDrawable();

		style.setColor(Color.parseColor("#207010"));

		style.setCornerRadius(10);

		listView1.setSelector(style);
		
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_location, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedCityString = gson.toJson((CityObj) parent.getItemAtPosition(position));
        Intent intent = new Intent();
        intent.putExtra("chosenCity", selectedCityString);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    public void afterTextChanged(Editable s) {
    }
}
