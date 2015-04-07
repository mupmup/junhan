package com.example.test_apk2.floating_action_button;

import java.util.ArrayList;

import com.example.test_apk2.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FloatActionButtonActivity3 extends Activity {

	private Button mFloatingButton;
	private ListView mList;
	
	private ListAdapter mAdapter;
	private ArrayList<String> mArrayList = new ArrayList<String>();
	
	private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_action_button_activity3);
		
		mFloatingButton = (Button)findViewById(R.id.btn);
		mList = (ListView)findViewById(R.id.list);
		
		initList();
	}
	
	private void initList() {
		for(int i=0 ; i<30 ; i++) {
			mArrayList.add("test::" + i);
		}
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0, mArrayList);
		mList.setAdapter(mAdapter);
		
		mList.setOnScrollListener(mScrollListener);
	}
	
	private OnScrollListener mScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if(SCROLL_STATE_IDLE == scrollState) {
//				Log.d("mup", "SCROLL_STATE_IDLE");
//				mFloatingButton.setVisibility(View.VISIBLE);
				showBtn();
			} else if(SCROLL_STATE_FLING == scrollState) {
//				Log.d("mup", "SCROLL_STATE_FLING");
//				mFloatingButton.setVisibility(View.GONE);
				hideBtn();
			} else if(SCROLL_STATE_TOUCH_SCROLL == scrollState) {
//				Log.d("mup", "SCROLL_STATE_TOUCH_SCROLL");
//				mFloatingButton.setVisibility(View.GONE);
				hideBtn();
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
	
	private void showBtn() {
		doAnimation(true);
	}
	
	private void hideBtn() {
		doAnimation(false);
	}
	
	private final int ANIMATION_DURATION = 200;
	
	private void doAnimation(boolean visible) {
		int height = mFloatingButton.getHeight();
//		Log.d("mup", height +" "+ mFloatingButton.getBottom() + " " + getButtonMarginBottom());
		int translationY = visible ? 0 : height + getButtonMarginBottom();
		
		mFloatingButton.animate().setInterpolator(mInterpolator)
		.setDuration(ANIMATION_DURATION)
		.translationY(translationY);
	}
	
	private int getButtonMarginBottom() {
		int marginBottom = 0;
		final ViewGroup.LayoutParams layoutParams = mFloatingButton.getLayoutParams();
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
		}
		return marginBottom;
	}
}
