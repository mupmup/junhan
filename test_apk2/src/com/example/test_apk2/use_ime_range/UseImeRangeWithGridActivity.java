package com.example.test_apk2.use_ime_range;

import java.util.List;

import com.example.test_apk2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class UseImeRangeWithGridActivity extends Activity {

	private View mLayoutActivity;
	private Button mBtn;
	private EditText mEditor;
//	private View mDumyView;
	private LinearLayout mDumyView;
	
	private PopupWindow mPopupWindow;
	
	private PackageManager mPackageManager;
	private List<ResolveInfo> mApps;
	
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
		
//		layoutGrid();
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
//						if (previousHeightDiffrence > heightDifference) {
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
//		TextView tv = new TextView(this);
//		tv.setText("Å×½ºÆ®");
//		tv.setBackgroundColor(0x88BBBBFF);
//		mPopupWindow = new PopupWindow(tv, LayoutParams.MATCH_PARENT, keyboardHeight, false);
		
		layoutGrid();
		GridView gv = new GridView(this);
		gv.setBackgroundColor(0x88BBBBFF);
		gv.setColumnWidth(getResources().getDimensionPixelSize(R.dimen.grid_imgae_size));
		gv.setNumColumns(GridView.AUTO_FIT);
		gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gv.setAdapter(new GridAdpter(this));
		mPopupWindow = new PopupWindow(gv, LayoutParams.MATCH_PARENT, keyboardHeight, false);
		
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
	
	private void layoutGrid() {
		mPackageManager = getPackageManager();
		
		Intent lunchIntent = new Intent(Intent.ACTION_MAIN);
		lunchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		mApps = mPackageManager.queryIntentActivities(lunchIntent, 0);
	}
	
	public class GridAdpter extends BaseAdapter {
		private Activity mActivity;
		private LayoutInflater mInflator;
		
		public GridAdpter(Activity activity) {
			mActivity = activity;
			mInflator = mActivity.getLayoutInflater();
		}
		

		@Override
		public int getCount() {
			return mApps.size();
		}

		@Override
		public Object getItem(int position) {
			return mApps.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView) {
				convertView = mInflator.inflate(R.layout.ime_with_grid_item, parent, false);
			}
			
			final ResolveInfo info = mApps.get(position);
			
			ImageView image = (ImageView)convertView.findViewById(R.id.image_view);
			TextView text = (TextView)convertView.findViewById(R.id.text_view);
			
			image.setImageDrawable(info.activityInfo.loadIcon(mPackageManager));
			text.setText(info.activityInfo.loadLabel(mPackageManager).toString());
			
			
			return convertView;
		}
		
	}
}
