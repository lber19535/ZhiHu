package com.bill.zhihu.login;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;

public class PollService extends IntentService {

	public PollService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public ComponentName startService(Intent service) {
		// TODO Auto-generated method stub
		return super.startService(service);
	}

}
