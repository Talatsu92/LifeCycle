package com.rafcarl.lifecycle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeightFragment2 extends Fragment{
	
	public static HeightFragment2 newInstance(){
		HeightFragment2 hf2 = new HeightFragment2();
		return hf2;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View hview = inflater.inflate(R.layout.frag_ht2, container, false);
		return hview;
	}

	

}
