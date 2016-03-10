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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

public class SearchActivity extends Activity {
	public static String ipaddress1 = "192.168.1.100";

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

	// /////////////////////////////////////////////////////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		spServices = (Spinner) findViewById(R.id.spServices);
		spRange = (Spinner) findViewById(R.id.spRange);
		spRating = (Spinner) findViewById(R.id.spRating);
		etMin = (EditText) findViewById(R.id.etMin);
		etMax = (EditText) findViewById(R.id.etMax);
		spCuisine = (Spinner) findViewById(R.id.spCuisine);
	}

	private class HttpAsyncTaskSearch extends AsyncTask<String, String, String>{
		public AsyncResponse delegate=null;
		JSONObject json = null;
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... urls) {

			// Building Parameters
			params1.add(new BasicNameValuePair("catalog", input_services));
			params1.add(new BasicNameValuePair("rating", input_rating));
			params1.add(new BasicNameValuePair("p1", input_min));
			params1.add(new BasicNameValuePair("p2", input_max));
			params1.add(new BasicNameValuePair("cuisine", input_cuisine));
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
			
			if(!finalResult.isEmpty())
			{
				//finalResult="";
			}
			finalResult.concat(result);
			
			
			JSONArray resultJsonArray = null;
			try {
				JSONObject json = new JSONObject(result);
				int success = json.getInt("success");
				if (success == 1) {
					resultJsonArray = json.getJSONArray("result");

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

	public void funSearchNow(View v) {
		
		// new registerJSONdbTask().execute(url_register);
		input_services = String.valueOf(spServices.getSelectedItem());
		// Toast.makeText(getApplicationContext(), input_services,
		// Toast.LENGTH_SHORT).toString();
		input_range = String.valueOf(spRange.getSelectedItem());
		input_rating = String.valueOf(spRating.getSelectedItem());
		input_cuisine = String.valueOf(spCuisine.getSelectedItem());
		input_min = etMin.getText().toString();
		input_max = etMax.getText().toString();
		Log.d("test fun", "fun running");
		new HttpAsyncTaskSearch().execute(url_search);
		Intent intent = new Intent(getApplicationContext(),
				SearchListActivity.class);
		//for (int i = 0; i < resultList.size(); i++) {
		//	Toast.makeText(getApplicationContext(),
		//			resultList.get(i).get("info_name"), Toast.LENGTH_SHORT)
		//			.show();
		//}
		if(resultList.isEmpty())
		{
		Toast.makeText(getApplicationContext(),
					"no result", Toast.LENGTH_SHORT)
					.show();
		}
		//intent.putExtra("arraylist", resultList);
		intent.putExtra("string", finalResult);
		// resultList.clear();
		startActivity(intent);
	}

	

}
