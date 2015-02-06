package com.bill.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bill.zhihu.login.ActivityHome;

/**
 * 首页
 * 
 * @author Bill Lv
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, ActivityHome.class);
		startActivity(intent);
		finish();
	}

}
