package com.example.test_apk2.list_animation;

import java.util.ArrayList;

import com.example.test_apk2.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class ListAnimationActivity extends Activity {
	
	private ActionBar mActionbar;
	private LinearLayout mBottomLayout;
	private ListView mList;
	
	private ListAdapter mAdapter;
	private ArrayList<String> mArrayList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_animation_activity);
		mList = (ListView)findViewById(R.id.list);
		
		initActionbar();
		initList();
		initButton();
	}
	
	private void initActionbar() {
		mActionbar = getActionBar();
		mActionbar.setTitle("list animation");
		mActionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_TITLE);
	}
	
	private void initButton() {
		mBottomLayout = (LinearLayout)findViewById(R.id.bottom_layout);
	}
	
	private int mListPaddingLeft = 0;
	private int mListPaddingRight = 0;
	private int mListPaddingBottom = 0;
	
	private void initList() {
		for(int i=0 ; i<30 ; i++) {
			mArrayList.add("test::" + i);
		}
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0, mArrayList);
		mList.setAdapter(mAdapter);
		
		mList.setOnScrollListener(mScrollListener);
//		mList.setClipToPadding(true);
		
		mListPaddingLeft = mList.getPaddingLeft();
		mListPaddingRight = mList.getPaddingRight();
		mListPaddingBottom = mList.getPaddingBottom();
	}
	
	private OnScrollListener mScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if(SCROLL_STATE_IDLE == scrollState) {
				show();
			} else if(SCROLL_STATE_FLING == scrollState) {
				hide();
			} else if(SCROLL_STATE_TOUCH_SCROLL == scrollState) {
				hide();
			} else {
				Log.d("mup", "else");
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
//			Log.w("mup", firstVisibleItem +" | "+ visibleItemCount  +" | "+ totalItemCount);
		}

	};
	
	private void hide() {
		mActionbar.hide();
		mBottomLayout.setVisibility(View.GONE);
//		mList.setPadding(mListPaddingLeft, 0, mListPaddingRight, mListPaddingBottom);
	}
	
	private void show() {
		mActionbar.show();
		mBottomLayout.setVisibility(View.VISIBLE);
//		int listPaddingTop = getResources().getDimensionPixelSize(android.R.attr.actionBarSize);
//		int listPaddingTop = getActionBarHeight();
//		mList.setPadding(mListPaddingLeft, listPaddingTop, mListPaddingRight, mListPaddingBottom);
	}
	
	private int getActionBarHeight() {
		int actionBarHeight = 0;
		// Calculate ActionBar height
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
		{
		    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}
}
