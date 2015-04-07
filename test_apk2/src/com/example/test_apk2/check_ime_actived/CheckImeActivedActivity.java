package com.example.test_apk2.check_ime_actived;

import com.example.test_apk2.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckImeActivedActivity extends Activity {
	
	private EditText mEditReceive, mEditBody;
	private Button mBtnSend, mBtnDumy;
	private TextView mViewer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_ime_actived_activity);
		
		initLayout();
		View ActivityLayout = findViewById(R.id.activity_layout);
		checkKeyboardHeight(ActivityLayout);
		
		updateDumyBtnStatus();
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		Log.d("mup", "onConfigurationChanged");
	}

	private void initLayout() {
		mEditReceive = (EditText)findViewById(R.id.editor_receive);
		mEditBody = (EditText)findViewById(R.id.editor_body);
		mBtnSend = (Button)findViewById(R.id.btn_send);
		mBtnDumy = (Button)findViewById(R.id.btn_dumy);
		mViewer = (TextView)findViewById(R.id.viewer);
		
		mEditBody.addTextChangedListener(mBodyEditWater);
	}
	
	private TextWatcher mBodyEditWater = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			updateDumyBtnStatus();
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
		
	};
	
	private void updateViewer() {
		if(isKeyBoardVisible) {
			mViewer.setText("Active");
		} else {
			mViewer.setText("hide");
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	/**
	 *  ���⼭����
	 */
	boolean isKeyBoardVisible = false;
//	int previousHeightDiffrence = 0;
	
	private void checkKeyboardHeight(final View parentLayout) {
		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);
						
						int screenHeight = parentLayout.getRootView().getHeight();
						int heightDifference = screenHeight - (r.bottom);
						
//						previousHeightDiffrence = heightDifference;
						if (heightDifference > 100) {
							isKeyBoardVisible = true;
//							updateViewer();
							updateDumyBtnStatus();
						} else {
							isKeyBoardVisible = false;
//							updateViewer();
							updateDumyBtnStatus();
						}
					}
				});
	}
	/** 
	 * ������� �ٽ� �κ��Դϴ�.
	 * �޼ҵ忡 �ԷµǴ� paramer(View parentLayout)�� 
	 * ��ü Activity�� layout�Դϴ�. 
	 * �Է±Ⱑ Ȱ��ȭ ���� �� layout�� �پ��� ���� üũ�ؼ�  �Է±� Ȱ��ȭ ���θ� üũ�մϴ�.
	 * 
	 * if (heightDifference > 100) {
	 * �� ���ǹ����� �� ���� ���� 100���� ���� �ʿ� ���� ���ѵ�, ���� ������ �������� �̷��� ����ϰ� �ֳ׿�. 
	 * �����δ� 0���� ��Ƶ� �˴ϴ�. 
	 * 
	 * ������� �� �޼ҵ�� �Է±Ⱑ Ȱ��ȭ �Ǿ� �ִ� ���¿��� ��~�� ȣ��˴ϴ�.
	*/ 
	////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////
	/**
	 * �� �޼ҵ� �ʹ� ���� ȣ��Ǳ� ������ 
	 * setVisibility()�� �����̶� �� ���Ƕ�� ���� ���¸� boolean����  ó���ϴ� ���� ���� ���մϴ�.
	 */
	boolean isBtnViewing = false;
	
	private void updateDumyBtnStatus() {
		boolean btnVisible = false;
		String text = mEditBody.getText().toString();
		if(isKeyBoardVisible || !TextUtils.isEmpty(text)) {
			btnVisible = false;
		} else {
			btnVisible = true;
		}
		
		if(btnVisible != isBtnViewing) {
//			Log.e("mup", "setVisibility() == " + btnVisible);
			if(btnVisible) {
				mBtnDumy.setVisibility(View.VISIBLE);
			} else {
				mBtnDumy.setVisibility(View.GONE);
			}
			isBtnViewing = btnVisible;
		}
	}
	////////////////////////////////////////////////////////////////////////////
}
