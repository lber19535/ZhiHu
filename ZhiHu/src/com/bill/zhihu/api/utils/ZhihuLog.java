package com.bill.zhihu.api.utils;

import android.util.Log;

public class ZhihuLog {

	public static boolean Debug = true;

	public static void d(String TAG, Object msg) {
		if (Debug) {
			Log.d(TAG, msg.toString());
		}

	}

}