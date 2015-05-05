package com.rafcarl.lifecycle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSTest extends Activity{

	EditText num, msg;
	TextView lat, lon, addr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_test);
		double latitude;
		double longitude;

		num = (EditText)findViewById(R.id.numEnt);
		msg = (EditText)findViewById(R.id.msgEnt);
		lat = (TextView)findViewById(R.id.lati);
		lon = (TextView)findViewById(R.id.longi);
		addr = (TextView)findViewById(R.id.addressed);

		GpsLocationTracker gps = new GpsLocationTracker(SMSTest.this);
		if(gps.canGetLocation()){
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			//Log.i("LAT", String.format("Latitude: %s", latitude));
			//Log.i("LON", String.format("Longitude: %s", longitude));
			lat.setText(Double.toString(latitude));
			lon.setText(Double.toString(longitude));
			
			Geocoder geocoder;
			List<Address> addresses = null;
			geocoder = new Geocoder(this, Locale.getDefault());
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String address = addresses.get(0).getAddressLine(0);
			String city = addresses.get(0).getAddressLine(1);
			String region = addresses.get(0).getAddressLine(2);
			String country = addresses.get(0).getAddressLine(3);
			
			String fulladdr = address + ", " + city + ", " + region + ", " + country;
			addr.setText(fulladdr);
		}
		else{gps.showSettingsAlert();}
	}

	public void sendSMS(View v)
	{      
		String phoneNumber = num.getText().toString();
		String message = msg.getText().toString();

		try{
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phoneNumber, null, message, null, null);
			Toast.makeText(getApplicationContext(), "Wee", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Aww", Toast.LENGTH_SHORT).show();
		}
	}
}
