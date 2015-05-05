package com.example.gpsasynctest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
						  implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

	GoogleApiClient googleApiClient = null;
	LocationRequest locationRequest = null;
	Location lastLocation = null;
	Location currentLocation = null;
	
	String latitude = null;
	String longitude = null;

	TextView textView = null;
	ProgressBar progressBar = null;
	Button button = null;
	
	View customLayout = null;
	
	LayoutInflater inflater = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		textView = (TextView)findViewById(R.id.textView);
		button = (Button)findViewById(R.id.button);
//		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		inflater = LayoutInflater.from(this);
		
		googleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(LocationServices.API)
		.build();
		
		locationRequest = new LocationRequest();
		locationRequest.setInterval(10000);
		locationRequest.setFastestInterval(5000);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);	
		
		
	}
	
	public void displayDialog(View v){
		L.m("displayDialog() enter"); 
		
		customLayout = inflater.inflate(R.layout.dialog, null, false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(customLayout)
			   .setTitle("GPS")
			   .setPositiveButton("Locate", null)
			   .setNegativeButton("Dismiss", null);
		L.m("AlertDialog builder initialized"); 
		
		
		final AlertDialog alertDialog = builder.create();
		L.m("AlertDialog created"); 
		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
				
				positive.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						googleApiClient.connect();
					}
				});
				
				negative.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog.dismiss();
						
						
					}
				});
			}
		});
		
		alertDialog.show();
		
//		googleApiClient.connect();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connection failed", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		L.m("onConnected enter()");
//		lastLocation = LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
		final ProgressBar dialogProgressBar = (ProgressBar)customLayout.findViewById(R.id.dialogProgressBar);
		final TextView dialogTextView = (TextView)customLayout.findViewById(R.id.dialogTextView);
		
		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
		dialogTextView.setText("Locating...");
		dialogProgressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connection suspended", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		final ProgressBar dialogProgressBar = (ProgressBar)customLayout.findViewById(R.id.dialogProgressBar);
		final TextView dialogTextView = (TextView)customLayout.findViewById(R.id.dialogTextView);
		
		if(location != null){
			latitude = String.valueOf(location.getLatitude());
			longitude = String.valueOf(location.getLongitude());
			
			L.m("Latitude: " + latitude + "\nLongitude: " + longitude);
			
			dialogTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
			dialogProgressBar.setVisibility(View.GONE);
			
			LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
			googleApiClient.disconnect();
			
			L.m("removeLocationUpdates() called, googleApiClient disconnected");			
		}
	}	
}
