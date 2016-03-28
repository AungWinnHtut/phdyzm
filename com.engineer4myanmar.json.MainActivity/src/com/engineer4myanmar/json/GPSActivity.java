package com.engineer4myanmar.json;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends Activity implements LocationListener {
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private String provider;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		funRefresh();
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {

		DecimalFormat precision = new DecimalFormat("00.000000");

		double lat = (location.getLatitude());
		double lng = (location.getLongitude());

		latituteField.setText(precision.format(lat));
		longitudeField.setText(precision.format(lng));
		setLocation("lat",latituteField.getText().toString());
		setLocation("lng",longitudeField.getText().toString() );
		//finish();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}
	
	public void funRefresh() {
		latituteField = (TextView) findViewById(R.id.TextView02);
		longitudeField = (TextView) findViewById(R.id.TextView04);

		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!enabled) {
			Toast.makeText(getApplicationContext(),
					"you must enable gps to use this app", Toast.LENGTH_LONG)
					.show();
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latituteField.setText("Location not available");
			longitudeField.setText("Location not available");
		}
	}
	
	public void saveLocation(String lat, String lng)
	{
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("lat",lat);
		editor.putString("lng", lng);
		editor.commit();
	}

	public HashMap<String,String> getLocation(){
		HashMap map = new HashMap<String,String>();
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);		
		String lat = sharedPref.getString("lat", "22.024104");
		String lng = sharedPref.getString("lng","96.447339");
		map.put("lat",lat);
		map.put("lng",lng);
		return map;
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
	public void funHome(View v)
	{
		this.finish();

	}
	
	
	
}
