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
import android.R.fraction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class DetailActivity extends Activity {
	public static String ipaddress1 = "mmgreenhackers.com";//"192.168.1.100";

	Person person = null;
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
	


	String input_services = "";
	String input_range = "";
	String input_rating = "";
	String input_min = "";
	String input_max = "";
	String input_cuisine;
	String sjson = "";
	String infoname="";
	JSONObject jObj;
	
	//JSONObject c = resultJsonArray.getJSONObject(i);
	String name ="";
	String address = "";
	String phone_no ="";
	String url = "";
	String rating = "";
	String lat = "";
	String lng = "";
	String price_high = "";
	String price_low = "";
	String cuisine = "";
	
	
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_search = "http://" + ipaddress1
			+ "/esdb/detail_hotel.php";
	private static String url_map = "http://" + ipaddress1
			+ "/esdb/map2.php?";
	
	//ListView lv;
	
	private ProgressDialog pDialog;
	final ArrayList<String> Alist = new ArrayList<String>();
	ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
	String finalResult="";
	
	
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
///////////////////////////////////////////////////////////
	HashMap<String,String> hMap = new HashMap<String,String>();	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        infoname = intent.getStringExtra("infoname");
        setContentView(R.layout.activity_detail);
        etName =(EditText)findViewById(R.id.etName);
    	etAddress=(EditText)findViewById(R.id.etAddress);
    	 etPhone=(EditText)findViewById(R.id.etPhone);
    	 etUrl=(EditText)findViewById(R.id.etUrl);
    	 etRating=(EditText)findViewById(R.id.etRating);
    	 etLat=(EditText)findViewById(R.id.etLat);
    	 etLng=(EditText)findViewById(R.id.etLng);
    	 etP1=(EditText)findViewById(R.id.etP1);
    	 etP2=(EditText)findViewById(R.id.etP2);
    	 etCui=(EditText)findViewById(R.id.etCui);
    	 
    	 new HttpAsyncTaskSearch().execute(url_search);
    }

private class HttpAsyncTaskSearch extends AsyncTask<String, String, String>{
		
		JSONObject json = null;
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();


		@Override
		protected String doInBackground(String... urls) {

			// Building Parameters
			params1.add(new BasicNameValuePair("name", infoname));
			if (!resultList.isEmpty()) {
				resultList.clear();
			}
			return readJSONFeed(urls[0], params1);
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * @return 
		 * @return 
		 * **/
		protected  void onPostExecute(String result) {
					
			JSONArray resultJsonArray = null;
			try {
				JSONObject json = new JSONObject(result);
				int success = json.getInt("success");
				if (success == 1) {
					resultJsonArray = json.getJSONArray("detail");

					for (int i = 0; i < resultJsonArray.length(); i++) {
						JSONObject c = resultJsonArray.getJSONObject(i);
						name = c.getString("name");
						address = c.getString("address");
						phone_no = c.getString("phone_no");
						url = c.getString("url");
						rating = c.getString("rating");
						lat = c.getString("lat");
						lng = c.getString("lng");
						price_high = c.getString("price_high");
						price_low = c.getString("price_low");
						cuisine = c.getString("cuisine");
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						// adding each child node to HashMap key => value

						map.put("name", name);
						map.put("address", address);
						map.put("phone_no", phone_no);
						map.put("url", url);
						map.put("rating", rating);
						map.put("lat", lat);
						map.put("lng", lng);
						map.put("price_high", price_high);
						map.put("price_low", price_low);
						map.put("cuisine", cuisine);
						
						
						try {
							resultList.add(map);
							Log.d("arl error", resultList.toString());
						} catch (Exception e) {
							Log.e("arl error", e.toString());

						}

						// ### user-pass testing purpose
						// Toast.makeText(
						// getBaseContext(),
						// info_name+ " -" + address
						// + "-" + phone_no, Toast.LENGTH_SHORT)
						// .show();
						// ### user-pass testing end
					}

				} else {
					//Toast.makeText(getBaseContext(), "fail", Toast.LENGTH_SHORT)
					//		.show();
					// ### user-pass testing end
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return result;
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					 etName.setText(name);
					 etAddress.setText(address);
					 etPhone.setText(phone_no);
					 etUrl.setText(url);
					 etRating.setText(rating);
					 etLat.setText(lat);
					 etLng.setText(lng);
					 etP1.setText(price_low);
					 etP2.setText(price_high);
					 etCui.setText(cuisine);
				}
			});

		}
		
	}

	@SuppressWarnings("unused")
	private String convertInputStreamToString(InputStream inputStream)
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

	public void funSearchNow() {
		
		// new registerJSONdbTask().execute(url_register);
		
		// Toast.makeText(getApplicationContext(), input_services,
		// Toast.LENGTH_SHORT).toString();
		input_range =hMap.get("range");
		input_rating = hMap.get("rating");
		input_cuisine = hMap.get("cuisine");
		input_min = hMap.get("min");
		input_max =hMap.get("max");
		Log.d("test fun", "fun running");
		new HttpAsyncTaskSearch().execute(url_search);
		
	}
	public void funMapView(View v)
	{
		HashMap<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("name",name);
		hashMap.put("address",address);
		hashMap.put("lat",lat);
		hashMap.put("lng",lng);
		//Toast.makeText(getApplicationContext(), name+address+lat+lng,Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getApplicationContext(),
				Map2Activity.class);			
		intent.putExtra("DetailHashMap",hashMap);		
		startActivity(intent);
	}
   

  

}
