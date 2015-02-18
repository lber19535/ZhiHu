package com.bill.zhihu.api;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.cmd.Command;
import com.bill.zhihu.api.net.ZhihuCookieStore;

public class ZhihuApi {

	public static final String XSRF = "_xsrf";
	private static Context mContext = ZhihuApp.getContext();
	private static Resources res = mContext.getResources();

	public static void execCmd(Command cmd) {
		cmd.exec();
	}

	public static void clearCookies() {
		new ZhihuCookieStore().clear();
	}

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

	public static float getCacheUsableSpace() {
		File cacheDir = mContext.getCacheDir();
		long usableSpace = cacheDir.getUsableSpace();
		return usableSpace / 1024f;
	}

}
