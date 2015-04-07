package com.example.test_apk2;

import com.example.test_apk2.badge_count.BadgeCountActivity;
import com.example.test_apk2.check_ime_actived.CheckImeActivedActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity2;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity3;
import com.example.test_apk2.list_animation.ListAnimationActivity;
import com.example.test_apk2.use_ime_range.UseImeRangeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends Activity {

	private Button mBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBtn = (Button)findViewById(R.id.btn);
		mBtn.setOnClickListener(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private View.OnClickListener mListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = null;
			
//			intent = new Intent(MainActivity.this, BadgeCountActivity.class);
//			intent = new Intent(MainActivity.this, UseImeRangeActivity.class);
//			intent = new Intent(MainActivity.this, CheckImeActivedActivity.class);
//			intent = new Intent(MainActivity.this, FloatActionButtonActivity.class);
//			intent = new Intent(MainActivity.this, FloatActionButtonActivity2.class);
//			intent = new Intent(MainActivity.this, FloatActionButtonActivity3.class);
			intent = new Intent(MainActivity2.this, ListAnimationActivity.class);
			
			if(null != intent) {
				startActivity(intent);
				finish();
			}
		}
	};
}
