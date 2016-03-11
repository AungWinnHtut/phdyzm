package com.engineer4myanmar.json;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private LocationManager locationManager;
	private String provider;

	EditText etUsername;
	EditText etPassword;
	EditText etIpaddress;
	int user_status = 0;
	int password_status = 0;
	int login_status = 0;
	String inputUsername = "";
	String inputPassword = "";
	public static String ipaddress = "192.168.1.100";

	@SuppressWarnings("null")
	public String readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
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

	private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			return readJSONFeed(urls[0]);
		}

		protected void onPostExecute(String result) {
			etUsername = (EditText) findViewById(R.id.etUsername);
			etPassword = (EditText) findViewById(R.id.etPassword);
			// etIpaddress = (EditText)findViewById(R.id.etIpaddress);
			// ipaddress = etIpaddress.getText().toString();
			inputUsername = etUsername.getText().toString();
			inputPassword = etPassword.getText().toString();

			user_status = 0;
			password_status = 0;
			login_status = 0;

			JSONArray logins = null;
			try {
				JSONObject json = new JSONObject(result);
				int success = json.getInt("success");
				if (success == 1) {
					logins = json.getJSONArray("logins");

					for (int i = 0; i < logins.length(); i++) {
						JSONObject c = logins.getJSONObject(i);
						int user_id = c.getInt("user_id");
						String user_name = c.getString("user_name");
						String user_password = c.getString("user_password");

						if (inputUsername.equals(user_name)) {
							user_status = 1;
						}
						if (inputPassword.equals(user_password)) {
							password_status = 1;
						}

						// ### user-pass testing purpose
						// Toast.makeText(
						// getBaseContext(),
						// String.valueOf(user_id) + " -" + user_name
						// + "-" + user_password,
						// Toast.LENGTH_SHORT).show();
						// //### user-pass testing end
					}

					if (user_status == 1 && password_status == 1) {
						login_status = 1;
						Toast.makeText(getBaseContext(), "Login Successfully",
								Toast.LENGTH_SHORT).show();
						Log.i("JSON", "Login Successfully");
						Intent intent = new Intent(getApplicationContext(),
								WelcomeActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						user_status = 0;
						password_status = 0;
						login_status = 0;
						startActivity(intent);
					} else {

						if (login_status == 0) {
							Toast.makeText(getBaseContext(), "Login Fail",
									Toast.LENGTH_SHORT).show();
							Log.i("JSON", "Login Fail");

							if (user_status == 1) {
								Toast.makeText(getBaseContext(),
										"Username Exists but Password error",
										Toast.LENGTH_SHORT).show();
								Log.i("JSON",
										"Username Exists but Password error");
							}

						}
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.print("JSON UI");
		//AppLocationManager appLocationManager = new AppLocationManager(
		//		MainActivity.this);
		//String lat = appLocationManager.getLatitude();
		//String lng = appLocationManager.getLongitude();
		//Toast.makeText(getApplicationContext(),
		//		"Your current Location is " + lat + " , " + lng,
		//		Toast.LENGTH_LONG).show();
		
		
		// funGPSon();

		// ("http://extjs.org.cn/extjs/examples/grid/survey.html");
		// //192.168.1.100/test/get_all_data.php
		// Toast.makeText(getApplicationContext(),"UI",Toast.LENGTH_LONG).show();
	}

	public void onStart() {
		super.onResume();
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etUsername.setText("");
		etPassword.setText("");
	}

	public void funLogin(View v) {
		String iUname = etUsername.getText().toString();
		String iPass = etPassword.getText().toString();
		if (iUname.equals("admin") && iPass.equals("admin")) {
			funAdmin();
		} else {
			new ReadJSONFeedTask().execute("http://" + ipaddress
					+ "/esdb/get_all_data.php");
		}

	}

	public void funRegister(View v) {
		Intent intent = new Intent(getApplicationContext(),
				RegisterActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void funAdmin() {
		Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void funGPSon(View v) {
		Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
		startActivity(intent);
	}

	public void funGetLoc(View v) {
		String lat;
		String lng;
		lat = getLocation("lat");
		lng = getLocation("lng");
		Toast.makeText(getApplicationContext(),
				"Your current Location is " + lat + " , " + lng,
				Toast.LENGTH_LONG).show();
	}

	public String getLocation(String key) {
		SharedPreferences sharedPref = this.getSharedPreferences(
				"com.engineer4myanmar.json", Context.MODE_PRIVATE);
		String val = sharedPref.getString(key, "00.000000");
		return val;
	}

	public void setLocation(String key, String val) {
		SharedPreferences sharedPref = this.getSharedPreferences(
				"com.engineer4myanmar.json", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key, val);
		editor.commit();
	}
	
	public void funViewMap(View v)
	{
		Intent intent = new Intent(this,MapActivity.class);
		startActivity(intent);
	}

}
