package com.rafcarl.lifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class UserMenu extends Activity {
	public static final String preferenceFile = "com.rafcarl.lifecycle.Flags";
	SharedPreferences userpref, sharedPref;
	SharedPreferences.Editor spEditor = null;
	ViewSwitcher switcher;

	TextView name;
	TextView age;
	Spinner blood;
	Spinner anti;
	TextView heightCM;
	TextView heightFT;
	TextView heightIN;
	Spinner heightspin;
	TextView weight;
	TextView meds;
	TextView conds;
	TextView allergs;
	public static final String PREF = "UserPrefs" ;
	public static final String Name = "nameKey"; 
	public static final String Age = "ageKey"; 
	public static final String Blood = "bloodKey"; 
	public static final String Anti = "antiKey";
	public static final String HeightCM = "heightCMKey";
	public static final String HeightFT = "heightFTKey";
	public static final String HeightIN = "heightINKey";
	public static final String HeightSpin = "heightspinKey"; 
	public static final String Weight = "weightKey";
	public static final String Meds = "medKey";
	public static final String Conds = "condtKey";
	public static final String Allergs = "allergKey";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_menu);

		switcher = (ViewSwitcher) findViewById(R.id.switcher);
		name = (TextView) findViewById(R.id.user_name_i);
		age = (TextView) findViewById(R.id.user_age_i);
		blood = (Spinner) findViewById(R.id.user_blood_s);
		anti = (Spinner) findViewById(R.id.user_blood_a);
		heightCM = (TextView) findViewById(R.id.user_height_i);
		heightFT = (TextView) findViewById(R.id.user_height_j);
		heightIN = (TextView) findViewById(R.id.user_height_k);
		heightspin = (Spinner) findViewById(R.id.user_height_s);
		weight = (TextView) findViewById(R.id.user_weight_i);
		meds = (TextView) findViewById(R.id.user_multi1_i);
		conds = (TextView) findViewById(R.id.user_multi2_i);
		allergs = (TextView) findViewById(R.id.user_multi3_i);

		userpref = getSharedPreferences(PREF, Context.MODE_PRIVATE);

		if(userpref.contains(Name)){name.setText(userpref.getString(Name, ""));}
		if(userpref.contains(Age)){age.setText(userpref.getString(Age, ""));}
		if(userpref.contains(Blood)){blood.setSelection(userpref.getInt(Blood, 0));}
		if(userpref.contains(Anti)){anti.setSelection(userpref.getInt(Anti, 0));}
		if(userpref.contains(HeightSpin)){heightspin.setSelection(userpref.getInt(HeightSpin, 0)); switcher.showNext();}
		
		heightspin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String selected = arg0.getItemAtPosition(arg2).toString();
				if(selected.equals("ft, in")){
					switcher.showPrevious();
				}
				else if(selected.equals("cm")){
					switcher.showNext();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});
		
		if(userpref.contains(HeightCM)){heightCM.setText(userpref.getString(HeightCM, ""));}
		if(userpref.contains(HeightFT)){heightFT.setText(userpref.getString(HeightFT, ""));}
		if(userpref.contains(HeightIN)){heightIN.setText(userpref.getString(HeightIN, ""));}
		if(userpref.contains(Weight)){weight.setText(userpref.getString(Weight, ""));}
		if(userpref.contains(Meds)){meds.setText(userpref.getString(Meds, ""));}
		if(userpref.contains(Conds)){conds.setText(userpref.getString(Conds, ""));}
		if(userpref.contains(Allergs)){allergs.setText(userpref.getString(Allergs, ""));}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_menu, menu);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void clearForm(ViewGroup group)
	{       
		for (int i = 0, count = group.getChildCount(); i < count; ++i) {
			View view = group.getChildAt(i);
			if (view instanceof EditText){
				((EditText)view).setText("");
			}
			if (view instanceof Spinner){
				((Spinner)view).setSelection(0);
			}

			if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
				clearForm((ViewGroup)view);
		}
	}

	public void saveUserInfo(View view){
		String n = name.getText().toString();
		String a = age.getText().toString();
		int b = blood.getSelectedItemPosition();
		int an = anti.getSelectedItemPosition();
		String hc = heightCM.getText().toString();
		String hf = heightFT.getText().toString();
		String hi = heightIN.getText().toString();
		int hs = heightspin.getSelectedItemPosition();
		String w = weight.getText().toString();
		String m = meds.getText().toString();
		String c = conds.getText().toString();
		String al = allergs.getText().toString();

		Editor editor = userpref.edit();
		editor.putString(Name, n);
		editor.putString(Age, a);
		editor.putInt(Blood, b);
		editor.putInt(Anti, an);
		editor.putString(HeightCM, hc);
		editor.putString(HeightFT, hf);
		editor.putString(HeightIN, hi);
		editor.putInt(HeightSpin, hs);
		editor.putString(Weight, w);
		editor.putString(Meds, m);
		editor.putString(Conds, c);
		editor.putString(Allergs, al);

		editor.commit();
		Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

		sharedPref = getSharedPreferences("com.rafcarl.lifecycle.flags", Context.MODE_PRIVATE);
		if(sharedPref.getBoolean(Flags.FIRST_RUN, true)){
			spEditor = sharedPref.edit();
			spEditor.putBoolean(Flags.FIRST_RUN, false);
			spEditor.commit();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("First Use");
			builder.setMessage(R.string.redirectContacts);
			builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(getApplicationContext(), ContactsMenu.class);
					startActivity(intent);
				}
			});

			AlertDialog dialog = builder.create();	
			dialog.show();
		}

	}

	public void clearUserInfo(View view){
		ViewGroup group = (ViewGroup) findViewById(R.id.scrollView1);
		clearForm(group);
		Editor editor = userpref.edit();
		editor.clear();
		editor.commit();
		Toast.makeText(this, "Cleared", Toast.LENGTH_LONG).show();
	}
}
