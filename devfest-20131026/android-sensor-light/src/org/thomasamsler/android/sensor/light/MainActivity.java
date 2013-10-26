package org.thomasamsler.android.sensor.light;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mLight;

	private TextView luxTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		luxTextView = (TextView) findViewById(R.id.tv_lux);
	}

	// SensorEventListener method
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

		Log.i("Light Sensor", "Sensor : " + sensor.getName() + " : accuracy : "
				+ accuracy);
	}

	// SensorEventListener method
	@Override
	public void onSensorChanged(SensorEvent event) {

		float lux = event.values[0];
		Log.i("Light Sensor", "Lux : " + lux + " lx");
		luxTextView.setText(lux + " lx");
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
}
