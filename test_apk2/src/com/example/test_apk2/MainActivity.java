package com.example.test_apk2;

import com.example.test_apk2.badge_count.BadgeCountActivity;
import com.example.test_apk2.check_ime_actived.CheckImeActivedActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity2;
import com.example.test_apk2.floating_action_button.FloatActionButtonActivity3;
import com.example.test_apk2.list_animation.ListAnimationActivity;
import com.example.test_apk2.overlay_view.TestOverlayView;
import com.example.test_apk2.ramdom_number.RandomNumberActivity;
import com.example.test_apk2.ramdom_number.RandomNumberActivity2;
import com.example.test_apk2.test_HttpClient.LoadImageViewActivity;
import com.example.test_apk2.test_HttpClient.UseWebViewActivity;
import com.example.test_apk2.use_ime_range.ImeWithViewPagerActivity;
import com.example.test_apk2.use_ime_range.UseImeRangeActivity;
import com.example.test_apk2.use_ime_range.UseImeRangeWithGridActivity;
import com.example.test_apk2.view_pager.ViewPagerActivity;
import com.example.test_apk2.webview_test.TestWebViewActivity;

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
		addItem(BadgeCountActivity.class, "아이콘에 count 개수 표시");
		addItem(UseImeRangeActivity.class, "IME 영역 부분을 Activity에서 사용");
		addItem(UseImeRangeWithGridActivity.class, "IME 영역 부분을 Activity에서 사용, grid 적용");
		addItem(ImeWithViewPagerActivity.class, "IME 영역 부분을 Activity에서 사용, ViewPager 적용");
		addItem(CheckImeActivedActivity.class, "IME 활성화 여부 체크 테스트");
		addItem(FloatActionButtonActivity.class, "floating 버튼 테스트");
		addItem(FloatActionButtonActivity2.class, "floating 버튼 테스트 2");
		addItem(FloatActionButtonActivity3.class, "floating 버튼 테스트3");
		addItem(ListAnimationActivity.class, "list에서 애니메이션 처리 연습");
		addItem(ViewPagerActivity.class, "ViewPager 연습");
		addItem(RandomNumberActivity2.class, "random number 생성");
		addItem(LoadImageViewActivity.class, "HttpClient, Image 불러오기");
		addItem(UseWebViewActivity.class, "HttpClient, WebView 불러오기");
		addItem(TestWebViewActivity.class, "WebView 테스트");
		addItem(TestOverlayView.class, "OverlayView 테스트");
	}
	
	private void addItem(Class<?> cls, String description) {
		MainItemView item;
		item = new MainItemView(this, cls, description);
		mMainLayout.addView(item);
	}
}
