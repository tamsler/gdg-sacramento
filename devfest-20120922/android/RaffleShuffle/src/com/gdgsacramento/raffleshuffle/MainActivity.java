package com.gdgsacramento.raffleshuffle;

import com.gdgsacramento.raffleshuffle.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void submitLogin(View view) {
    	HttpUtil httpUtil = new HttpUtil() {
    		@Override
    		protected void onPostExecute(String result) {
    	        super.onPostExecute(result);
    	        Log.d("Get Result", result);
    	        Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
    	    	startActivity(intent);
    	    }
    	};
    	String query = "http://raffle_app.aws.af.cm/api/v1/hello/";
    	httpUtil.execute(query);
    }
    	
}
