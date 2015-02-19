package com.bill.zhihu.api.utils;

import android.widget.Toast;

import com.bill.zhihu.ZhihuApp;

public class ToastUtil {

	public static void showShortToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	public static void showLongToast(String text) {
		showToast(text, Toast.LENGTH_LONG);
	}

	private static void showToast(String text, int duration) {
		Toast.makeText(ZhihuApp.getContext(), text, duration).show();
	}
}
