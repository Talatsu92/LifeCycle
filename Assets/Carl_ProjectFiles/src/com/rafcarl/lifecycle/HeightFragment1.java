package com.rafcarl.lifecycle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeightFragment1 extends Fragment{
	
	public static HeightFragment1 newInstance(){
		HeightFragment1 hf1 = new HeightFragment1();
		return hf1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View hview = inflater.inflate(R.layout.frag_ht1, container, false);
		return hview;
	}

}
