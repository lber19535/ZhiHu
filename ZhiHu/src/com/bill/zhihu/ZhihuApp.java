package com.bill.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

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

	//	public static String getString(int id) {
	//		Resources res = mContext.getResources();
	//		return res.getString(id);
	//	}

}
