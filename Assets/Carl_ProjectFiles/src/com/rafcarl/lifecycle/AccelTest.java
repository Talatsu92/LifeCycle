package com.rafcarl.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccelTest extends Activity implements SensorEventListener{
	private SensorManager mSensorManager;
	TextView acceleration = null;
	TextView sensorInfo;
	Sensor accelerometer;

	public float[] gravity;
	public float[] linear_acceleration;
	
//	float timeConstant = 0.18f;
	float alpha = 0.9f;
//	float dt = 0;
//	float timestamp = System.nanoTime();
//	float timestampOld = System.nanoTime();
//	private int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accel_test);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		if(accelerometer != null){
			Toast.makeText(this, "Accelerometer detected", Toast.LENGTH_SHORT).show();
			acceleration = (TextView) findViewById(R.id.sensorValues);
			
			gravity = new float[] {0, 0, 0};
			linear_acceleration = new float[]{0, 0, 0};
			
		}
		else{
			Toast.makeText(this, "Accelerometer does not exist", Toast.LENGTH_SHORT).show();
		}
	}

	public void sensorStart(View v){
		float minDelay, resolution, range, power;
		String vendor;
		
		sensorInfo = (TextView) findViewById(R.id.sensorInfo);
		vendor = accelerometer.getVendor();
		minDelay = accelerometer.getMinDelay();
		resolution = accelerometer.getResolution();
		range = accelerometer.getMaximumRange();
		power = accelerometer.getPower();
		
		sensorInfo.setText("\nVendor: " + vendor 
						 + "\nMinimum Delay: " + minDelay 
						 + "\nResolution: " + resolution 
						 + "\nRange: " + range 
						 + "\nPower: " + power);
		
		
		mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void sensorStop(View v){
		mSensorManager.unregisterListener(this);
	}
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {

//		timestamp = System.nanoTime();
		
		// Find the sample period
//		dt = 1 / (count / ((timestamp - timestampOld) / 1000000000.0f));
//		count++;
		
		// Set dynamic alpha
//		alpha = timeConstant / (timeConstant + dt);
		
		// Acceleration due to gravity (Low Pass Filter)
//		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
//		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
//		gravity[2] = alpha * gravity[1] + (1 - alpha) * event.values[2];
		
		// Linear acceleration = raw acceleration data - acceleration due to gravity (High Pass Filter)
//		linear_acceleration[0] = event.values[0] - gravity[0];
//		linear_acceleration[1] = event.values[1] - gravity[1];
//		linear_acceleration[2] = event.values[2] - gravity[2];
		
		double sumVector = Math.sqrt(Math.pow(event.values[0], 2) 
								   + Math.pow(event.values[1], 2) 
								   + Math.pow(event.values[2], 2))
								   / SensorManager.GRAVITY_EARTH;
		if(sumVector <= 0.6f){
			mSensorManager.unregisterListener(this);
			Toast.makeText(this, "Threshold broken " + sumVector, Toast.LENGTH_LONG).show();				
		}
		else{
			acceleration.setText("X: " + event.values[0] 
				       + "\n\nY: "  + event.values[1]
				       + "\n\nZ: "  + event.values[2]
                       + "\n\nSV: " + sumVector);
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accel_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
