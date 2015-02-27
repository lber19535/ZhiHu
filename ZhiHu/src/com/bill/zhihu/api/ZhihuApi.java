package com.bill.zhihu.api;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.cmd.Command;
import com.bill.zhihu.api.net.ZhihuCookieManager;
import com.bill.zhihu.api.net.ZhihuCookieStore;

public class ZhihuApi {

	private static final String XSRF = "_xsrf";
	private static Context mContext = ZhihuApp.getContext();
	private static Resources res = mContext.getResources();
	private static SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(ZhihuApp.getContext());

	public static void execCmd(Command cmd) {
		cmd.exec();
	}

	/**
	 * 清除cookies
	 */
	public static void clearCookies() {
		new ZhihuCookieStore().clear();
	}

	/**
	 * 清除缓存
	 */
	public static void clearCache() {
		File cacheDir = mContext.getCacheDir();
		File[] cacheFiles = cacheDir.listFiles();
		for (File file : cacheFiles) {
			if (!file.delete()) {
				Toast.makeText(mContext,
						res.getString(R.string.cache_delete_toast),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 获取缓存可用空间
	 * 
	 * @return kb
	 */
	public static float getCacheUsableSpace() {
		File cacheDir = mContext.getCacheDir();
		long usableSpace = cacheDir.getUsableSpace();
		return usableSpace / 1024f;
	}

	public static void setXSRF(String xsrf) {
		sp.edit().putString(XSRF, xsrf).commit();
	}

	/**
	 * if don't have xsrf or don't fetch xsrf value return null
	 * 
	 * @return
	 */
	public static String getXSRF() {
		sp = PreferenceManager.getDefaultSharedPreferences(ZhihuApp
				.getContext());
		if (ZhihuCookieManager.haveCookieName(ZhihuApi.XSRF)) {
			return ZhihuCookieManager.getCookieValue(ZhihuApi.XSRF);
		} else {
			return sp.getString(ZhihuApi.XSRF, null);
		}
	}
}
