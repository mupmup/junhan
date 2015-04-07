package com.example.test_apk2.floating_action_button;

import com.example.test_apk2.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class FloatActionButtonActivity2 extends Activity {

	private Button mFloatingButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_action_button_activity2);
		
		mFloatingButton = (Button)findViewById(R.id.btn);
		
	}

}
