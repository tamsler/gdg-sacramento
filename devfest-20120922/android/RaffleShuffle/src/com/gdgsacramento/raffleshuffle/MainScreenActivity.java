package com.gdgsacramento.raffleshuffle;

import com.gdgsacramento.raffleshuffle.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_screen, menu);
        return true;
    }
}
