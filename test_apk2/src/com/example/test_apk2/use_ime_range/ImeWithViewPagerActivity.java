package com.example.test_apk2.use_ime_range;

import java.util.ArrayList;
import java.util.List;

import com.example.test_apk2.R;

import android.app.Activity;
import android.content.ComponentName;
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
import android.view.View.OnClickListener;
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

public class ImeWithViewPagerActivity extends Activity {

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
						
//						if (previousHeightDiffrence - heightDifference > 50) {
						if (previousHeightDiffrence > heightDifference) {
							mPopupWindow.dismiss();
						}
						if (heightDifference > 100) {
							isKeyBoardVisible = true;
							if(previousHeightDiffrence != heightDifference) {
								changeKeyboardHeight(heightDifference);
							}
						} else {
							isKeyBoardVisible = false;
						}
						previousHeightDiffrence = heightDifference;
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
			
			// page 별 개수, page 개수 계산
			calculateCount();
//			
//			// List 정리
			resetPageList();
			initPageList();
			
			Log.d("mup","aaaa");
//			gridView.setAdapter(new GridAdpter(this, mPageList.get(0)));
			
//			layoutPopupView();
		}

	}
	
	
	private GridAdpter mGridAdpter;
	
	private void layoutPopupView() {
		layoutGrid();
		
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.ime_with_viewpager_layout, null);
		GridView gridView = (GridView)layout.findViewById(R.id.grid_view);
//		gridView.setAdapter(new GridAdpter(this, mPageList.get(0)));
		mGridAdpter = new GridAdpter(this, mPageList.get(0));
		gridView.setAdapter(mGridAdpter);
		mPopupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT, keyboardHeight, false);
		
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
		
		mPageCount = mApps.size() / mItemCount;
		
		// List 정리
		resetPageList();
		initPageList();
	}

	private int mPageCount = 0;
	private int mItemCount = 0;
	
	private void calculateCount() {
		int itemHeight = getResources().getDimensionPixelSize(R.dimen.grid_item_height);
		int itemWidth = getResources().getDimensionPixelSize(R.dimen.grid_item_width);
		int row = (keyboardHeight / itemHeight);
//		int column = mLayoutActivity.getRootView().getWidth() / itemWidth;
		int column = 4;
		
		Log.w("mup", mLayoutActivity.getRootView().getHeight() +" "+ mLayoutActivity.getRootView().getWidth());
		Log.d("mup", itemHeight +" "+ itemWidth +" "+ row +" "+ column);
		
		mItemCount = row * column;
//		mPageCount = mApps.size() / mItemCount;
	}
	
	private ArrayList<List<ResolveInfo>> mPageList;

	private void initPageList() {
		if(null == mApps || mApps.size() == 0 || mPageCount == 0) {
			return;
		}
		
		mPageList = new ArrayList<List<ResolveInfo>>();
		List<ResolveInfo> page;
		
		page = mApps.subList(0, 7);
		mPageList.add(page);
		
		page = mApps.subList(8, 15);
		mPageList.add(page);
		
//		for(int i=0 ; i<mPageCount ; i++) {
//			int s = i * mItemCount;
//			int e = Math.min(((i+1) * mItemCount)-1, mApps.size()-1);
//			page = mApps.subList(s, e);
//			mPageList.add(page);
//		}
		
		if(null != mGridAdpter) {
			mGridAdpter.setUpdateList(mPageList.get(0));
		}
	}
	
	private void resetPageList() {
		if(null != mPageList) {
			for(List<ResolveInfo> page : mPageList) {
				page.clear();
			}
			mPageList.clear();
			mPageList = null;
		}
	}
	
	
	public class GridAdpter extends BaseAdapter {
		private Activity mActivity;
		private LayoutInflater mInflator;
		private List<ResolveInfo> mList;
		
		public GridAdpter(Activity activity, List<ResolveInfo> list) {
			mActivity = activity;
			mInflator = mActivity.getLayoutInflater();
			mList = list;
		}
		
		
		public void setUpdateList(List<ResolveInfo> list) {
			mList = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
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
			
			final ResolveInfo info = mList.get(position);
			
			ImageView image = (ImageView)convertView.findViewById(R.id.image_view);
			TextView text = (TextView)convertView.findViewById(R.id.text_view);
			
			image.setImageDrawable(info.activityInfo.loadIcon(mPackageManager));
			text.setText(info.activityInfo.loadLabel(mPackageManager).toString());
			
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_RUN);
					intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
					mActivity.startActivity(intent);
				}
			});
			
			return convertView;
		}
		
	}
}
