package com.example.test_apk2.ramdom_number;

import java.util.ArrayList;

import com.example.test_apk2.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RandomNumberActivity extends Activity {
	
	private EditText mCountEditor;
	private EditText mRangeEndEditor;
	private TextView mResultView;
	private Button mStarButton, mClearButton;
	
	private ArrayList<Integer> mSelecedList;
	private int[] mShowDataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.random_number_activity);
		
		initLayout();
	}
	
	private void initLayout() {
		mCountEditor = (EditText)findViewById(R.id.editor_count);
		mRangeEndEditor = (EditText)findViewById(R.id.editor_range_end);
		mResultView = (TextView)findViewById(R.id.view_result);
		
		mStarButton = (Button)findViewById(R.id.btn_start);
		mStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s_count = mCountEditor.getText().toString();
				String s_range_end = mRangeEndEditor.getText().toString();
				if(TextUtils.isEmpty(s_count) || TextUtils.isEmpty(s_range_end)) {
					mResultView.setText("ERROR!!! Empty range");
				}
				if(!TextUtils.isDigitsOnly(s_count) || !TextUtils.isDigitsOnly(s_range_end)) {
					mResultView.setText("ERROR!!! wrong text.\nPlease Input only number");
				}
				
				start(Integer.valueOf(s_count), Integer.valueOf(s_range_end));
			}
		});
		
		mClearButton = (Button)findViewById(R.id.btn_clear);
		mClearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mResultView.setText("Clear~~");
				
				mSelecedList.clear();
				mSelecedList = null;
				
				mShowDataList = null;
			}
		});
	}
	
	private void start(int count, int range_end) {
		if(null == mSelecedList) {
			mSelecedList = new ArrayList<Integer>();
			
			mShowDataList = new int[count];
		} else {
			chooseRandom(count, range_end);
		}
		
		showResult();
	}

	private void showResult() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0 ; i<mShowDataList.length ; i++) {
			sb.append(i+1);
			sb.append(".  ");
			if(0 == mShowDataList[i]) {
				sb.append("??");
			} else {
				sb.append(mShowDataList[i]);
			}
			sb.append("\n");
		}
		
		mResultView.setText(sb);
	}
	
	private void chooseRandom(int count, int range_end) {
//		Log.w("mup", "chooseRandom");
		
		if(mSelecedList.size() >= count) {
			Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();;
			return;
		}
		
		while(true) {
			double random = Math.random() * range_end + 1;
			
			int data = (int)random;
//			Log.e("mup", "data : " + data);
			if(!mSelecedList.contains(data)) {
//				Log.i("mup", "selected");
				mSelecedList.add(data);
				for(int i=0 ; i<mShowDataList.length ; i++) {
					if(0 == mShowDataList[i]) {
						mShowDataList[i] = data;
						break;
					}
				}
				break;
			}
		}
	}
}
