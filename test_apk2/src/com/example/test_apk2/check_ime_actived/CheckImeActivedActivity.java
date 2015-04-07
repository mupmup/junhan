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
	 *  여기서부터
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
	 * 여기까지 핵심 부분입니다.
	 * 메소드에 입력되는 paramer(View parentLayout)는 
	 * 전체 Activity의 layout입니다. 
	 * 입력기가 활성화 됐을 때 layout이 줄어드는 것을 체크해서  입력기 활성화 여부를 체크합니다.
	 * 
	 * if (heightDifference > 100) {
	 * 이 조건문에서 비교 값을 굳이 100으로 잡을 필요 없을 듯한데, 제가 참고한 예제에서 이렇게 사용하고 있네요. 
	 * 실제로는 0으로 잡아도 됩니다. 
	 * 
	 * 문제라면 위 메소드는 입력기가 활성화 되어 있는 상태에서 계~속 호출됩니다.
	*/ 
	////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////
	/**
	 * 위 메소드 너무 자주 호출되기 때문에 
	 * setVisibility()가 조금이라도 덜 사용되라고 현재 상태를 boolean으로  처리하는 것이 좋을 듯합니다.
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
