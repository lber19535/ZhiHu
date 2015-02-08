package com.bill.zhihu.api;

import android.util.Log;

public class ZhihuLog {

	public static boolean Debug = true;

	public static void d(String TAG, String msg) {
		if (Debug) {
			Log.d(TAG, msg);
		}

	}

}
