package com.engineer4myanmar.ES;

import com.engineer4myanmar.ES.Person;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class AdminActivity extends Activity {
	Person person = null;

	Spinner spCatalog;
	EditText etName;
	EditText etAddress;
	EditText etPhone;
	EditText etUrl;
	EditText etRating;
	EditText etLat;
	EditText etLng;
	EditText etP1;
	EditText etP2;
	EditText etCui;

	String input_catalog = "";
	String input_name= "";
	String input_address = "";
	String input_phone = "";
	String input_url = "";
	String input_rating = "";
	String input_lat = "";
	String input_lng = "";
	String input_p1 = "";
	String input_p2 = "";
	String input_cui = "";

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_add="http://www.mmgreenhackers.com/esdb/add_hotel.php";
	private static String url_add_hospital = "http://www.mmgreenhackers.com/esdb/add_hospital.php";
	private static String url_add_hotel = "http://www.mmgreenhackers.com/esdb/add_hotel.php";
	private static String url_add_bank= "http://www.mmgreenhackers.com/esdb/add_bank.php";
	private static String url_add_restaurant= "http://www.mmgreenhackers.com/esdb/add_restaurant.php";
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		spCatalog = (Spinner)findViewById(R.id.spCatalog);
		etName = (EditText) findViewById(R.id.etName);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etPhone= (EditText) findViewById(R.id.etPhone);
		etUrl = (EditText) findViewById(R.id.etUrl);
		etRating = (EditText) findViewById(R.id.etRating);
		etLat = (EditText) findViewById(R.id.etLat);
		etLng= (EditText) findViewById(R.id.etLng);
		etP1 = (EditText) findViewById(R.id.etP1);
		etP2 = (EditText) findViewById(R.id.etP2);
		etCui = (EditText) findViewById(R.id.etCui);
		funGetLocation();
	}

	private class HttpAsyncTask extends AsyncTask<String, String, String> {
		JSONObject json = null;

		@Override
		protected String doInBackground(String... urls) {

			InputStream is = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			
			input_catalog=String.valueOf(spCatalog.getSelectedItemPosition());			
			input_name=etName .getText().toString();
			input_address=etAddress .getText().toString();
			input_phone=etPhone.getText().toString();
			input_url=etUrl .getText().toString();
			input_rating=etRating.getText().toString();
			input_lat=etLat .getText().toString();
			input_lng=etLng.getText().toString();
			input_p1=etP1 .getText().toString();
			input_p2=etP2 .getText().toString();
			input_cui=etCui .getText().toString();
					
			if (input_catalog.equals("") || input_name.equals("")) {				
				return null;
			} else {
				params.add(new BasicNameValuePair("catalog", input_catalog));
				params.add(new BasicNameValuePair("name", input_name));
				params.add(new BasicNameValuePair("address",input_address));
				params.add(new BasicNameValuePair("phone_no", input_phone));
				params.add(new BasicNameValuePair("url", input_url));
				params.add(new BasicNameValuePair("rating",input_rating));
				params.add(new BasicNameValuePair("lat", input_lat));
				params.add(new BasicNameValuePair("lng", input_lng));
				params.add(new BasicNameValuePair("pl", input_p1));
				params.add(new BasicNameValuePair("ph",input_p2));
				params.add(new BasicNameValuePair("cu", input_cui));

				// getting JSON Object
				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					switch(spCatalog.getSelectedItemPosition())
					{
					case 0:	url_add=url_add_hotel; break;
					case 1: url_add=url_add_bank; break;
					case 2: url_add=url_add_restaurant; break;
					case 3: url_add=url_add_hospital; break;
						default: Log.e("error catalog","no such index");
					}
					HttpPost httpPost = new HttpPost(url_add);
					httpPost.setEntity(new UrlEncodedFormEntity(params));
					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
					Log.d("JSON", is.toString());
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					Log.d("JSON", sb.toString());
					is.close();
					String sjson = sb.toString();

					try {
						jObj = new JSONObject(sjson);
						json = jObj;
					} catch (JSONException e) {
						Log.e("JSON Parser",
								"Error parsing data " + e.toString() + "   "
										+ params.toString() + "+++ " + sjson);
					}

				} catch (Exception e) {
					Log.e("parser error", e.toString());
				}
				Log.d("Create Response", json.toString());

				// check for success tag
				try {
					int success = json.getInt("success");
					if (success == 1) {
						Log.d("Success", "OK");
					} else {
						Log.d("Success", "not OK");
					}
				} catch (Exception e) {
					Log.d("Success", "not OK " + e.toString());
				}

				// return neull;
				return jObj.toString();
				// return POST(urls[0],person);
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
		
			try{
				if(result.equals(null))
				{
					Toast.makeText(getBaseContext(), "'"+ result+"'",Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getBaseContext(), "Data successfully send to server!!!",Toast.LENGTH_LONG).show();
				}
					
			}catch(Exception e)
			{
				Log.e("JSON",e.toString());
				Toast.makeText(getBaseContext(), "Data cannot be send! Please fill the required fields !!!",Toast.LENGTH_LONG).show();
			}
			
			
			
			
			
		}
	}



	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public void funAdd(View v) {
		// new registerJSONdbTask().execute(url_register);
		new HttpAsyncTask().execute(url_add);
		//funClearAll();
	}
	public void funClearAll()
	{
		etName.setText("");
		etAddress.setText("");
		etPhone.setText("");
		etUrl.setText("");
		etRating.setText("");
		etLat.setText("");
		etLng.setText("");
		etP1.setText("");
		etP2.setText("");
		etCui.setText("");
		
	}
	
	public void funGetLocation()
	{
		String lat;
		String lng;
		lat=getLocation("lat");
		lng=getLocation("lng");
		Toast.makeText(getApplicationContext(),"Your current Location is "+lat+" , " +lng, Toast.LENGTH_LONG).show();
		etLat.setText(lat);
		etLng.setText(lng);
	}
	public String getLocation(String key){
		SharedPreferences sharedPref = this.getSharedPreferences("com.engineer4myanmar.json",Context.MODE_PRIVATE);	
		String val = sharedPref.getString(key, "00.000000");
		return val;
	}
	public void setLocation(String key,String val)
	{
		SharedPreferences sharedPref = this.getSharedPreferences("com.engineer4myanmar.json",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key,val);	
		editor.commit();
	}

	
}
