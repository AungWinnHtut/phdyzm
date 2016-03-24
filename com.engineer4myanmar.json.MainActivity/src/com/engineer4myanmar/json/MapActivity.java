package com.engineer4myanmar.json;
//Just for Reserve (Map)
import android.os.Bundle;
import android.app.Activity;
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

public class MapActivity extends Activity{
	WebView wv1;
	Button b1;
	SeekBar zoom ;
	int z=5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);    
        wv1=(WebView)findViewById(R.id.webView2);
        wv1.setWebViewClient(new MyBrowser());
        b1=(Button)findViewById(R.id.b1);
        zoom = (SeekBar)findViewById(R.id.seekBar1);
        zoom.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
				z=progressChanged/20;
				showMap2(15+z);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(getApplicationContext(),"ZOOM level:: "+String.valueOf(15+z)+" !", 
						Toast.LENGTH_SHORT).show();
			}
		});
       
    }
    public void funMap2(View v) {
    	showMap2(15+z);
     }
    public void showMap2(int z2)
    {
    	  String url ="https://www.google.com.mm/maps/@22.0311769,96.4686856,"+String.valueOf(z2)+"z?hl=en";
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
