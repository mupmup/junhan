package com.example.test_apk2.use_ime_range;

import com.example.test_apk2.R;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class UseImeRangeActivity extends Activity {

	private View mLayoutActivity;
	private Button mBtn;
	private EditText mEditor;
//	private View mDumyView;
	private LinearLayout mDumyView;
	
	private PopupWindow mPopupWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_ime_range_activity);
		
		mLayoutActivity = findViewById(R.id.layout_activity);
		mBtn = (Button)findViewById(R.id.btn);
		mEditor = (EditText)findViewById(R.id.editor);
		mDumyView = (LinearLayout)findViewById(R.id.dumy);
		
		mBtn.setOnClickListener(mListener);
		
		checkKeyboardHeight(mLayoutActivity);
		
		int imeHeight = (int)getResources().getDimension(R.dimen.ime_height);
		changeKeyboardHeight(imeHeight);
		
		layoutPopupView();
		layoutEditor();
	}
	
	
	private View.OnClickListener mListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
//			if(mInputViewShown) {
//			if(isKeyBoardVisible) {
//				hideInputView();
//			} else {
//				showInputView();
//			}
			if(!mPopupWindow.isShowing()) {
				if(isKeyBoardVisible) {
					mDumyView.setVisibility(View.GONE);
				} else {
					mDumyView.setVisibility(View.VISIBLE);
				}
				mPopupWindow.showAtLocation(mLayoutActivity, Gravity.BOTTOM, 0, 0);
			} else {
				mPopupWindow.dismiss();
			}
		}
	};
	
	
//	private boolean mInputViewShown = false;
	
//	private void showInputView() {
//		if(null != mPopupWindow) {
//			mPopupWindow.showAtLocation(mLayoutActivity, Gravity.BOTTOM, 0, 0);
//		}
//		mDumyView.setVisibility(View.VISIBLE);
//		
////		mInputViewShown = true;
//	}
//	
//	private void hideInputView() {
//		if(null != mPopupWindow) {
//			if(mPopupWindow.isShowing()) {
//				mPopupWindow.dismiss();
//			}
//		}
//		mDumyView.setVisibility(View.GONE);
//		
////		mInputViewShown = false;
//	}
	
	
	boolean isKeyBoardVisible = false;
	int previousHeightDiffrence = 0;
	
	private void checkKeyboardHeight(final View parentLayout) {
		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);
						
						int screenHeight = parentLayout.getRootView().getHeight();
						int heightDifference = screenHeight - (r.bottom);
//						Log.d("mup","onGlobalLayout()::" + heightDifference);
						
						if (previousHeightDiffrence - heightDifference > 50) {							
							mPopupWindow.dismiss();
						}
						previousHeightDiffrence = heightDifference;
						if (heightDifference > 100) {
							isKeyBoardVisible = true;
							changeKeyboardHeight(heightDifference);
						} else {
							isKeyBoardVisible = false;
						}
					}
				});
	}
	
	private int keyboardHeight = 0;
	
	private void changeKeyboardHeight(int height) {

		if (height > 100) {
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, keyboardHeight);
//			emoticonsCover.setLayoutParams(params);
			
			mDumyView.setLayoutParams(params);
			
			if(null != mPopupWindow) {
				mPopupWindow.setHeight(keyboardHeight);
			}
		}

	}
	
	
	private void layoutPopupView() {
		TextView tv = new TextView(this);
		tv.setText("Å×½ºÆ®");
		tv.setBackgroundColor(0x88BBBBFF);
		mPopupWindow = new PopupWindow(tv, LayoutParams.MATCH_PARENT, keyboardHeight, false);
		
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mDumyView.setVisibility(View.GONE);
			}
		});
	}
	
	private void layoutEditor() {
		mEditor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			return false;
		} else {
			return super.onKeyUp(keyCode, event);
		}
	}
}
