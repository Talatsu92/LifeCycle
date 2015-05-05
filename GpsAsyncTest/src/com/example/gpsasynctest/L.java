package com.example.gpsasynctest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {

	public L() {
		// TODO Auto-generated constructor stub
	}
	
	public static void m(String message){
		Log.i("GPSAsyncTest", message);
	}
	
	public static void s(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

}
