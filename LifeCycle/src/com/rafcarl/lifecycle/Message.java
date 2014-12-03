package com.rafcarl.lifecycle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Message extends Activity{
	List<Contact> ContactList;
	
	LocationManager locationManager;
	ConnectivityManager connectivityManager;
	
	String streetAddress;
	double latitude;
	double longitude;
	String latitudeDMS;
	String longitudeDMS;
	
	public Message(LocationManager locationManager, ConnectivityManager connectivityManager) {
		ContactList = new ArrayList<Contact>();
		
		this.locationManager = locationManager;
		this.connectivityManager = connectivityManager;
	}
	
	//Location-related methods
	public void getLocation(){
		LocationTracker locationTracker = new LocationTracker(locationManager, connectivityManager, getApplicationContext());
		locationTracker.getLocation();
		streetAddress = locationTracker.getStreetAddress();
		locationTracker.getCoordinates(latitude, longitude);
		
		locationTracker.disconnect();
	}
	
	public void convertToDMS(){
		short degree;
		byte minute;
		float second;
		float temp;
		
		degree = (short) ((latitude >= 0) ? Math.floor(latitude) : Math.ceil(latitude));
		temp = (float) (Math.abs(latitude) - Math.abs(degree)) * 60;
		minute = (byte) Math.floor(temp);
		temp -= minute;
		second = temp * 60;
		
		latitudeDMS = degree + "°" + minute + "'" + second + "\"";
		
		degree = (short) ((longitude >= 0) ? Math.floor(longitude) : Math.ceil(longitude));
		temp = (float) (Math.abs(longitude) - Math.abs(degree)) * 60;
		minute = (byte) Math.floor(temp);
		temp -= minute;
		second = temp * 60;
		
		longitudeDMS = degree + "°" + minute + "'" + second + "\"";
	}
	
	public void sendMessage(){
		SmsManager sms = SmsManager.getDefault();
		String phoneNumber;
		StringBuilder message;
		String mapsLink = "http://maps.google.com/maps?saddr=" + latitudeDMS + "," + longitudeDMS;
		String additional = "\n\n To view the location on Google Maps, follow this link:  " + mapsLink;
				
		for(Contact contact : ContactList) {
			phoneNumber = contact.getNumber();
			message = new StringBuilder(contact.getMessage());
			message.append(additional);
			
			sms.sendTextMessage(phoneNumber, null, message.toString(), null, null);
		}
	}
	
	public void getContacts(){
		Cursor cursor;
		DBHelper.db = DBHelper.dbHelper.getReadableDatabase();
		cursor = DBHelper.db.rawQuery("SELECT * FROM LifeCycleTable", null);

		if(cursor.moveToFirst()){
			while(!cursor.isAfterLast()){
				Contact contact = new Contact();
				contact.setName(cursor.getString(cursor.getColumnIndex(DBHelper.NAME)));
				contact.setNumber(cursor.getString(cursor.getColumnIndex(DBHelper.NUMBER)));
				contact.setMessage(cursor.getString(cursor.getColumnIndex(DBHelper.MESSAGE)));
				contact.setId(cursor.getString(cursor.getColumnIndex(DBHelper.CONTACT_ID)));
				
				ContactList.add(contact);

				cursor.moveToNext();
			}
		}
		cursor.close();
	}
}
