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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class RegisterActivity extends Activity {
	Person person = null;
	EditText etUsername;
	EditText etPassword;
	EditText etEmail;
	EditText etPhoneno;
	EditText etDob;
	EditText etProfession;
	EditText etCity;

	String input_username = "";
	String input_password = "";
	String input_email = "";
	String input_phoneno = "";
	String input_dob = "";
	String input_profession = "";
	String input_city = "";

	JSONObject jObj;
	JSONParser jsonParser = new JSONParser();

	// change here your ip/folder/php
	private static String url_register = "http://www.mmgreenhackers.com/esdb/register_user.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPhoneno = (EditText) findViewById(R.id.etPhoneno);
		etDob = (EditText) findViewById(R.id.etDob);
		etProfession = (EditText) findViewById(R.id.etProfession);
		etCity = (EditText) findViewById(R.id.etCity);

	}

	private class HttpAsyncTask extends AsyncTask<String, String, String> {
		JSONObject json = null;

		@Override
		protected String doInBackground(String... urls) {

			InputStream is = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			input_username = etUsername.getText().toString();
			input_password = etPassword.getText().toString();
			input_email = etEmail.getText().toString();
			input_phoneno = etPhoneno.getText().toString();
			input_dob = etDob.getText().toString();
			input_profession = etProfession.getText().toString();
			input_city = etCity.getText().toString();

			if (input_username.equals("") || input_password.equals("")) {		
				//etUsername.setText("error");
				return null;
			} else {
				params.add(new BasicNameValuePair("user_name", input_username));
				params.add(new BasicNameValuePair("user_email", input_email));
				params.add(new BasicNameValuePair("user_password",
						input_password));
				params.add(new BasicNameValuePair("user_phoneno", input_phoneno));
				params.add(new BasicNameValuePair("user_dob", input_dob));
				params.add(new BasicNameValuePair("user_profession",
						input_profession));
				params.add(new BasicNameValuePair("user_city", input_city));

				// getting JSON Object
				try {
					// json = jsonParser.makeHttpRequest(url_register,
					// "POST",params);

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url_register);
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
				Toast.makeText(getBaseContext(), "Data cannot be send! Please fill the required fields !!!"+e.toString(),Toast.LENGTH_LONG).show();
			}
			
			
			
			
			
		}
	}

	private boolean validate() {
		if (etUsername.getText().toString().trim().equals(""))
			return false;
		else if (etPassword.getText().toString().trim().equals(""))
			return false;
		else
			return true;
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

	public void funRegister(View v) {
		// new registerJSONdbTask().execute(url_register);
		new HttpAsyncTask().execute(url_register);
		//funClearAll();
	}
	public void funClearAll()
	{
		etUsername.setText("");
		etPassword.setText("");
		etEmail.setText("");
		etPhoneno.setText("");
		etDob.setText("");
		etProfession.setText("");
		etCity.setText("");		
	}
}
