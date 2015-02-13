package com.bill.zhihu;

import android.app.Application;
import android.content.Context;

public class ZhihuApp extends Application {

	private static final String TAG = "ZhihuApp";

	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}

	public static Context getContext() {
		return mContext;
	}

}
