package com.example.test_apk2.badge_count;

import com.example.test_apk2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BadgeCountActivity extends Activity {

	private Button mBtnIncrease, mBtnDecrease;
	private TextView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.badge_count_activity);
		
        mBtnIncrease = (Button)findViewById(R.id.btn_increase);
        mBtnDecrease = (Button)findViewById(R.id.btn_decrease);
        mView = (TextView)findViewById(R.id.view);

        mBtnIncrease.setOnClickListener(mListener);
        mBtnDecrease.setOnClickListener(mListener);
        
        updateCount();
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.equals(mBtnIncrease)) {
                increaseCount();
            } else if(v.equals(mBtnDecrease)) {
                decreaseCount();
            } else {
                // nothing
            }
        }
    };

	
    private int mCount = 0;

    private void increaseCount() {
        mCount++;

        updateCount();
    }

    private void decreaseCount() {
        if(mCount > 0) {
            mCount--;
        }

        updateCount();
    }

    private void updateCount() {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        intent.putExtra("badge_count_package_name", getComponentName().getPackageName());
        intent.putExtra("badge_count_package_name", "com.example.test_apk2");
//        intent.putExtra("badge_count_class_name", getComponentName().getClassName());
        intent.putExtra("badge_count_class_name", "com.example.test_apk2.MainActivity");
        intent.putExtra("badge_count", mCount);
        sendBroadcast(intent);

//        Toast.makeText(this, "mCount:" + mCount, Toast.LENGTH_SHORT).show();
        mView.setText("Count: " + mCount);
    }
}
