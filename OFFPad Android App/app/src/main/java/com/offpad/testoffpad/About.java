package com.offpad.testoffpad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
	}
	public void Back(View v){
		this.finish();
	}
   
}