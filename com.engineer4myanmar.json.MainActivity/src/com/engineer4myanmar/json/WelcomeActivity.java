package com.engineer4myanmar.json;

import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class WelcomeActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

	}

	public void funCallSearch(View v) {
		Intent intent = new Intent(getApplicationContext(),
				Search2Activity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
		
	 private class AsyncTestTask extends AsyncTask<String, String, String> {
	         
		 
		 @Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
			 
				return null;
			}
		 
		 
	     protected void onPostExecute(String s) {
	        
	     }



		
	 }

}
