package com.rafcarl.lifecycle;

import java.util.zip.Inflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AccidentDialog extends DialogFragment {
	Button positive, neutral, negative;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_accident, null);
		builder.setTitle("Accident detected!")
			   .setView(view);
		
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		
		return dialog;
	}
	
}
