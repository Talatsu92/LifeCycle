package com.rafcarl.lifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {
	public static final String preferenceFile = "com.rafcarl.lifecycle.flags";
	SharedPreferences sharedPref = null;
	SharedPreferences.Editor editor = null;
	public static final String LOG = "MainMenu";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		getActionBar().hide();
				
		sharedPref = getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

		if(sharedPref.getBoolean(Flags.FIRST_RUN, true)){
			editor = sharedPref.edit();
			editor.putBoolean(Flags.FIRST_RUN, true);
			editor.commit();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("First Use");
			builder.setMessage(R.string.redirectUser);
			builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					Intent intent = new Intent(getApplicationContext(), UserMenu.class);
					startActivity(intent);
				}
			});
			
			AlertDialog dialog = builder.create();	
			dialog.show();
		}
	}

	public void goToUser(View v){
		Intent intent = new Intent(this, UserMenu.class);
		startActivity(intent);
	}

	public void goToContacts(View v){
		Intent intent = new Intent(this, ContactsMenu.class);
		startActivity(intent);
	}

	public void goToHelp(View v){
		Intent intent = new Intent(this, HelpMenu.class);
		startActivity(intent);
	}
	
	public void goToTest(View v){
		Intent intent = new Intent(this, AccelTest.class);
		startActivity(intent);
	}
	public void openAboutDialog(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("LifeCycle");
		builder.setMessage(R.string.aboutDialog);
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}
	public void goToSMS(View v){
		Intent intent = new Intent(this, SMSTest.class);
		startActivity(intent);
	}
	
}
