package com.example.test_apk2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainItemView extends LinearLayout {

	private LinearLayout mLayout;
	private TextView mTextMain, mTextSub;
	
	private Context mContext;
	
	private Class<?> mClass;
	private String mDescription;
	
	public MainItemView(Context context, Class<?> cls, String description) {
		super(context);
		
		mContext = context;
		mClass = cls;
		mDescription = description;
		
		init();
	}
	
	private void init() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.main_item_view, this);
		
		mTextMain = (TextView)findViewById(R.id.main_text);
		mTextSub = (TextView)findViewById(R.id.sub_text);
		
		mTextMain.setText(mClass.getSimpleName());
		mTextSub.setText(mDescription);
		
		mLayout = (LinearLayout)findViewById(R.id.item_layout);
		mLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mContext instanceof Activity) {
					Intent intent = new Intent(mContext, mClass);
					((Activity)mContext).startActivity(intent);
//					((Activity)mContext).finish();
				}
			}
		});
	}
}
