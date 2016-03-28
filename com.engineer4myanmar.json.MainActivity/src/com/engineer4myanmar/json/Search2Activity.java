package com.engineer4myanmar.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Search2Activity extends Activity implements OnItemSelectedListener {
	public static String ipaddress1 =  "mmgreenhackers.com";//"192.168.1.100";

	Person person = null;
	Spinner spServices;
	Spinner spRange;
	Spinner spRating;
	EditText etMin;
	EditText etMax;
	Spinner spCuisine;
	LinearLayout llRating;
	LinearLayout llPrice;
	LinearLayout llMaxMin;
	LinearLayout llCuisine;

	String input_services = "";
	String input_range = "";
	String input_rating = "";
	String input_min = "";
	String input_max = "";
	String input_cuisine;
	String sjson = "";

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_search = "http://" + ipaddress1
			+ "/esdb/search3.php";

	private ProgressDialog pDialog;
	final ArrayList<String> Alist = new ArrayList<String>();
	ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
	String finalResult="";

	// /////////////////////////////////////////////////////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search2);
		llRating = (LinearLayout)findViewById(R.id.llRating );
		llPrice = (LinearLayout)findViewById(R.id.llPrice );
		llMaxMin = (LinearLayout)findViewById(R.id.llMaxMin);
		llCuisine = (LinearLayout)findViewById(R.id.llCuisine);
		
		spServices = (Spinner) findViewById(R.id.spServices);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.services_arrays, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spServices.setAdapter(adapter);

		spRange = (Spinner) findViewById(R.id.spRange);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.range_arrays, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRange.setAdapter(adapter1);
		
		spRating = (Spinner) findViewById(R.id.spRating);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.ratings_arrays, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRating.setAdapter(adapter2);
		
		etMin = (EditText) findViewById(R.id.etMin);
		etMax = (EditText) findViewById(R.id.etMax);
		
		spCuisine = (Spinner) findViewById(R.id.spCuisine);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.cuisine_arrays, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCuisine.setAdapter(adapter3);
		
		spServices.setOnItemSelectedListener(this);
		
	}


	public void funSearchNow(View v) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		// new registerJSONdbTask().execute(url_register);
		input_services = String.valueOf(spServices.getSelectedItem());
		
		// TO DO hospital and bank data omit
		input_range = String.valueOf(spRange.getSelectedItem());
		input_rating = String.valueOf(spRating.getSelectedItem());
		input_cuisine = String.valueOf(spCuisine.getSelectedItem());
		input_min = etMin.getText().toString();
		input_max = etMax.getText().toString();
		//Bundle with hashMap
		hashMap.put("name",input_services);
		hashMap.put("range",input_range);
		hashMap.put("rating",input_rating);
		hashMap.put("cuisine",input_cuisine);
		hashMap.put("min",input_min);
		hashMap.put("max",input_max);
		/////////////////////////////////////////	
		
		String lat;
		String lng;
		lat = getLocation("lat", "22.024104");
		lng = getLocation("lng", "96.447339");
	
		hashMap.put("x0",lat);
		hashMap.put("y0",lng);
		hashMap.put("r",input_range);
		
		
		
		Intent intent = new Intent(getApplicationContext(),
				SearchListActivity.class);			
		intent.putExtra("hashMap",hashMap);		
		startActivity(intent);
	}
	public void funSearchPDA(View v) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		// new registerJSONdbTask().execute(url_register);
		input_services = String.valueOf(spServices.getSelectedItem());
	
		// TO DO hospital and bank data omit
		input_range = String.valueOf(spRange.getSelectedItem());
		input_rating = String.valueOf(spRating.getSelectedItem());
		input_cuisine = String.valueOf(spCuisine.getSelectedItem());
		input_min = etMin.getText().toString();
		input_max = etMax.getText().toString();
		//Bundle with hashMap
		hashMap.put("name",input_services);
		hashMap.put("range",input_range);
		hashMap.put("rating",input_rating);
		hashMap.put("cuisine",input_cuisine);
		hashMap.put("min",input_min);
		hashMap.put("max",input_max);
		/////////////////////////////////////////	
		
		String lat;
		String lng;
		lat = getLocation("lat", "22.024104");
		lng = getLocation("lng", "96.447339");
	
		hashMap.put("x0",lat);
		hashMap.put("y0",lng);
		hashMap.put("r",input_range);
		
		
	
		Intent intent = new Intent(getApplicationContext(),
				SearchList2Activity.class);			
		intent.putExtra("hashMap",hashMap);		
		startActivity(intent);
	}
	public String getLocation(String key, String default_value) {
		SharedPreferences sharedPref = this.getSharedPreferences(
				"com.engineer4myanmar.json", Context.MODE_PRIVATE);
		String val = sharedPref.getString(key, default_value);
		return val;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {		
		switch(pos)
		{
		case 0:
			showViews();			
			break;
		case 1:
			hideViews();		
			break;
		case 2:
			showViews();				
			break;
		case 3:
			hideViews();			
			break;
		}
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {	
		
	}

	public void hideViews()
	{
		llRating.setVisibility(4);
		llPrice.setVisibility(4);
		llMaxMin.setVisibility(4);
		llCuisine.setVisibility(4);
	}
	public void showViews()
	{
		llRating.setVisibility(0);
		llPrice.setVisibility(0);
		llMaxMin.setVisibility(0);
		llCuisine.setVisibility(0);
	}
	
	// ////////////////////////////////////////////////////////////////////
	@SuppressWarnings("null")
	public String readJSONFeed(String URL, List<NameValuePair> params) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		String paramString = URLEncodedUtils.format(params, "utf-8");
		URL += "?" + paramString;
		HttpGet httpGet = new HttpGet(URL);
		try {
			HttpResponse response = client.execute(httpGet);
			if (response != null) {
				// Log.e("JSON", String.valueOf(response.toString()));
			} else {
				Log.e("JSON", String.valueOf(response.toString()));
				Log.e("JSON", "HTTPGET ERROR");
			}

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			// Log.e("JSON", String.valueOf(statusCode));
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				// Log.e("JSON ! GOT IT   **** ", stringBuilder.toString());
			} else {
				Log.e("JSON", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();

	}	
	


}