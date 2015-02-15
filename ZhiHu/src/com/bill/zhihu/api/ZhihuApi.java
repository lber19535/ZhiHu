package com.bill.zhihu.api;

import java.io.File;

import android.content.Context;
import android.widget.Toast;

import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.cmd.Command;
import com.bill.zhihu.api.utils.ZhihuCookieStore;

public class ZhihuApi {

	public static final String XSRF = "_xsrf";
	public static Context mContext = ZhihuApp.getContext();

	public static void execCmd(Command cmd) {
		cmd.exec();
	}

	public static void clearCookies() {
		new ZhihuCookieStore(mContext).clear();
	}

	public static void clearCache() {
		File cacheDir = mContext.getCacheDir();
		File[] cacheFiles = cacheDir.listFiles();
		for (File file : cacheFiles) {
			if (!file.delete()) {
				Toast.makeText(mContext, "有些缓存文件无法删除", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public static float getCacheUsableSpace() {
		File cacheDir = mContext.getCacheDir();
		long usableSpace = cacheDir.getUsableSpace();
		return usableSpace / 1024f;
	}

}
