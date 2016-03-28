package com.engineer4myanmar.json;

//called by Search2Activity with parameter HashMap
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

public class SearchList2Activity extends Activity {
	public static String ipaddress1 =  "mmgreenhackers.com";//"192.168.1.100";

	Person person = null;
	Spinner spServices;
	Spinner spRange;
	Spinner spRating;
	EditText etMin;
	EditText etMax;
	Spinner spCuisine;

	String input_services = "";
	String input_range = "";
	String input_rating = "";
	String input_min = "";
	String input_max = "";
	String input_cuisine;
	String x0="";
	String y0="";
	String r="";
	String sjson = "";

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_search ="";
	// = "http://" + ipaddress1+ "/esdb/search3.php";
	
	ListView lv;
	
	private ProgressDialog pDialog;
	final ArrayList<String> Alist = new ArrayList<String>();
	ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
	String finalResult = "";
	// ////////////////////////////////////////////////////////////////////

	HashMap<String, String> hMap = new HashMap<String, String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		hMap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");

		setContentView(R.layout.activity_search_list2);
		lv = (ListView) findViewById(R.id.lvList2);
		// new registerJSONdbTask().execute(url_register);
		input_services = hMap.get("name");
		
		if(input_services.equals("hotel"))
		{
			url_search = "http://" + ipaddress1	+ "/esdb/search32.php";
		}
		else if(input_services.equals("restaurant"))
		{
			url_search = "http://" + ipaddress1	+ "/esdb/search32.php";
		}
		else
		{
			url_search = "http://" + ipaddress1	+ "/esdb/search_bank_hospital2.php";
		}
		input_range = hMap.get("range");
		input_rating = hMap.get("rating");
		input_cuisine = hMap.get("cuisine");
		input_min = hMap.get("min");
		input_max = hMap.get("max");
		x0=hMap.get("x0");
		y0=hMap.get("y0");
		r=hMap.get("r");
		
	

		new HttpAsyncTaskSearch().execute(url_search);

		lv.setOnItemClickListener(new OnItemClickListener() {

			// @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String infoname = ((TextView) view.findViewById(R.id.info_name))
						.getText().toString();
			
				Intent intent = new Intent(getApplicationContext(),
						DetailActivity.class);
				intent.putExtra("infoname", infoname);
				startActivity(intent);
			}
		});
	}

	private class HttpAsyncTaskSearch extends AsyncTask<String, String, String> {

		JSONObject json = null;
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();

		@Override
		protected String doInBackground(String... urls) {

			// Building Parameters
			params1.add(new BasicNameValuePair("catalog", input_services));
			params1.add(new BasicNameValuePair("rating", input_rating));
			params1.add(new BasicNameValuePair("p1", input_min));
			params1.add(new BasicNameValuePair("p2", input_max));
			params1.add(new BasicNameValuePair("cuisine", input_cuisine));
			params1.add(new BasicNameValuePair("x0", x0));
			params1.add(new BasicNameValuePair("y0", y0));
			params1.add(new BasicNameValuePair("r", r));
			if (!resultList.isEmpty()) {
				resultList.clear();
			}
			return readJSONFeed(urls[0], params1);
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * 
		 * @return
		 * @return
		 * **/
		protected void onPostExecute(String result) {
		
			Log.e("!!!!!! ERRor",result);
			JSONArray resultJsonArray = null;
			try {
				JSONObject json = new JSONObject(result);
				int success = json.getInt("success");				
				String execution_time = json.getString("execution_time");
				Toast.makeText(getApplicationContext(), "execution time by PDA: "+execution_time,Toast.LENGTH_LONG).show();
				if (success == 1) {
					//Toast.makeText(getApplicationContext(),"SUCCESS JSON", Toast.LENGTH_SHORT).show();
					resultJsonArray = json.getJSONArray("result");
					//Toast.makeText(getApplicationContext(), "JSON success", Toast.LENGTH_SHORT).show();
					for (int i = 0; i < resultJsonArray.length(); i++) {
						JSONObject c = resultJsonArray.getJSONObject(i);
						String info_name = c.getString("info_name");
						String address = c.getString("address");
						String phone_no = c.getString("phone_no");						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						
						map.put("info_name", info_name);
						map.put("address", address);
						map.put("phone_no", phone_no);
						try {
							resultList.add(map);
							Log.d("arl error", resultList.toString());
						} catch (Exception e) {
							Log.e("arl error", e.toString());

						}

						
					}

				} else {
					
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return result;
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							getApplicationContext(), resultList,
							R.layout.list_item, new String[] { "info_name","address", "phone_no" },
							new int[] { R.id.info_name, R.id.address,R.id.phone_no });
					// updating listview
					 				
					lv.setAdapter(adapter);
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
		input_services = String.valueOf(spServices.getSelectedItem());
		
		input_range = hMap.get("range");
		input_rating = hMap.get("rating");
		input_cuisine = hMap.get("cuisine");
		input_min = hMap.get("min");
		input_max = hMap.get("max");
		Log.d("test fun", "fun running");
		new HttpAsyncTaskSearch().execute(url_search);

	}

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
