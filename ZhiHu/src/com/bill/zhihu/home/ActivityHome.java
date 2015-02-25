package com.bill.zhihu.home;

import android.os.Bundle;

import com.bill.zhihu.activity.BaseActivity;

public class ActivityHome extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		toggleFragment(new FragmentHome());
	}
	

}
