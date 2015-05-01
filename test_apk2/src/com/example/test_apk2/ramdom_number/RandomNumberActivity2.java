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

public class RandomNumberActivity2 extends Activity {
	
	private EditText mCountEditor;
	private EditText mRangeStartEditor, mRangeEndEditor;
	private TextView mResultView;
	private Button mStarButton, mClearButton;
	
	private ArrayList<Integer> mTargetList;
	private int[] mShowDataList;
	private int mDataIndex = 0;
	
	private int mCount;
	private int mStartNum = 0, mEndNum = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.random_number_activity2);
		
		initLayout();
	}
	
	private void initLayout() {
		mCountEditor = (EditText)findViewById(R.id.editor_count);
		mRangeEndEditor = (EditText)findViewById(R.id.editor_range_end);
		mRangeStartEditor = (EditText)findViewById(R.id.editor_range_start);
		mResultView = (TextView)findViewById(R.id.view_result);
		
		mStarButton = (Button)findViewById(R.id.btn_start);
		mStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s_count = mCountEditor.getText().toString();
				String s_range_start = mRangeStartEditor.getText().toString();
				String s_range_end = mRangeEndEditor.getText().toString();
				if(TextUtils.isEmpty(s_count) || TextUtils.isEmpty(s_range_start) || TextUtils.isEmpty(s_range_end)) {
					mResultView.setText("ERROR!!! Empty range");
					return;
				}
				if(!TextUtils.isDigitsOnly(s_count) || !TextUtils.isDigitsOnly(s_range_start) || !TextUtils.isDigitsOnly(s_range_end)) {
					mResultView.setText("ERROR!!! wrong text.\nPlease Input only number");
					return;
				}
				
				int count = Integer.valueOf(s_count);
				int startNum = Integer.valueOf(s_range_start);
				int endNum = Integer.valueOf(s_range_end);
				
				if(!validCheck(count, startNum, endNum)) {
					return;
				}
				
				checkDataChange(count, startNum, endNum);
				
				start(mCount, mStartNum, mEndNum);
			}
		});
		
		mClearButton = (Button)findViewById(R.id.btn_clear);
		mClearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mResultView.setText("Clear~~");
				
				dataClear();
			}
		});
	}
	
	private void dataClear() {
		if(null != mTargetList) {
			mTargetList.clear();
			mTargetList = null;
		}
		
		mShowDataList = null;
		mDataIndex = 0;
		
		mCount = 0;
		mStartNum = 0;
		mEndNum = 0;
	}
	
	private void checkDataChange(int count, int range_start, int range_end) {
		
		if(0 == mCount && 0 == mStartNum && 0 == mEndNum) {
			mCount = count;
			mStartNum = range_start;
			mEndNum = range_end;
		} else {
			if((mCount != count) || (mStartNum != range_start) || (mEndNum != range_end)) {
				dataClear();
				
				mCount = count;
				mStartNum = range_start;
				mEndNum = range_end;
			}
		}
	}
	
	private boolean validCheck(int count, int startNum, int range_end) {
		if(count <= 0 || startNum <= 0 || range_end <= 0) {
			mResultView.setText("ERROR!!! Positive number more than 0.");
			return false;
		}
		
		if(range_end <= startNum) {
			mResultView.setText("ERROR!!! range end is bigger than start.");
			return false;
		}
		
		int range = range_end - startNum;
		if(range_end < range) {
			mResultView.setText("ERROR!!! range is larger than count.");
			return false;
		}
		
		return true;
	}
	
	
	
	private void start(int count, int range_start, int range_end) {
		if(null == mTargetList) {
			generateTarget(range_start, range_end);
			
			mShowDataList = new int[count];
		} else {
			chooseRandom(count);
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
	
	private void generateTarget(int range_start, int range_end) {
		mTargetList = new ArrayList<Integer>();
		
		int start = range_start;
		int total = range_end;
		for(int i=start ; i<=total ; i++) {
			mTargetList.add(i);
		}
	}
	
	private void chooseRandom(int count) {
		Log.w("mup", "chooseRandom");
		for(int c : mTargetList) {
			Log.d("mup", "data : " + c);
		}
		
		
		// 타겟이 다 사라진 경우
		if(mTargetList.size() == 0) {
			Toast.makeText(this, "complete 1", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 채워놓을 칸이 다 채워진 경우1. index 확인
		if(mDataIndex >= count) {
			Toast.makeText(this, "complete 2", Toast.LENGTH_SHORT).show();
			return;
		}
		// 채워놓을 칸이 다 채워진 경우2. array 확인
		boolean complete = true;
		for(int i=0 ; i<mShowDataList.length ; i++) {
			if(0 == mShowDataList[i]) {
				complete = false;
				break;
			}
		}
		if(complete) {
			Toast.makeText(this, "complete 3", Toast.LENGTH_SHORT).show();
			return;
		}
		
		double random = Math.random() * mTargetList.size();
			
		int index = (int)random;
//		Log.e("mup", "index : " + index + " | data : " + mTargetList.get(index));
		mShowDataList[mDataIndex] = mTargetList.get(index);
		mDataIndex++;
		mTargetList.remove(index);
	}
}
