package com.engineer4myanmar.json;
//Just for Reserve (Map)
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Map2Activity extends Activity{
	WebView wv1;
	HashMap<String,String> hashMap = new HashMap<String,String>();
	int z=5;
	String name1;
	String address1;
	String lat1;
	String lng1;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
		hashMap = (HashMap<String, String>) intent.getSerializableExtra("DetailHashMap");
        setContentView(R.layout.activity_map2);    
        wv1=(WebView)findViewById(R.id.webView1);
        wv1.setWebViewClient(new MyBrowser());    
        name1=hashMap.get("name");
        address1=hashMap.get("address");
        lat1=hashMap.get("lat");
        lng1=hashMap.get("lng");
        showMap2(name1,address1,lat1,lng1);
    }
    
    
    public void showMap2(String name, String address, String lat, String lng)
    {
    	  //String url ="http://192.168.1.100/esdb/map2.php?name="+name+"&address="+address+"&lat="+lat+"&lng="+lng;
    	  String url ="http://mmgreenhackers.com/esdb/map2.php?name="+name+"&address="+address+"&lat="+lat+"&lng="+lng;
          wv1.getSettings().setLoadsImagesAutomatically(true);
          wv1.getSettings().setJavaScriptEnabled(true);
          wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
          wv1.loadUrl(url);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           view.loadUrl(url);
           return true;
        }
    }
    
}
