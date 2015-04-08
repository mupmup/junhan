package com.example.test_apk2;

import com.example.test_apk2.badge_count.BadgeCountActivity;
import com.example.test_apk2.check_ime_actived.CheckImeActivedActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity2;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity3;
import com.example.test_apk2.list_animation.ListAnimationActivity;
import com.example.test_apk2.use_ime_range.UseImeRangeActivity;
import com.example.test_apk2.use_ime_range.UseImeRangeWithGridActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	private LinearLayout mMainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main2);
		mMainLayout = (LinearLayout)findViewById(R.id.main_layout);
		
		addItemList();
	}

	private void addItemList() {
		addItem(BadgeCountActivity.class, "�����ܿ� count ���� ǥ��");
		addItem(UseImeRangeActivity.class, "IME ���� �κ��� Activity���� ���");
		addItem(UseImeRangeWithGridActivity.class, "IME ���� �κ��� Activity���� ���, grid ����");
		addItem(CheckImeActivedActivity.class, "IME Ȱ��ȭ ���� üũ �׽�Ʈ");
		addItem(FloatActionButtonActivity.class, "floating ��ư �׽�Ʈ");
		addItem(FloatActionButtonActivity2.class, "floating ��ư �׽�Ʈ 2");
		addItem(FloatActionButtonActivity3.class, "floating ��ư �׽�Ʈ3");
		addItem(ListAnimationActivity.class, "list���� �ִϸ��̼� ó�� ����");
	}
	
	private void addItem(Class<?> cls, String description) {
		MainItemView item;
		item = new MainItemView(this, cls, description);
		mMainLayout.addView(item);
	}
}
