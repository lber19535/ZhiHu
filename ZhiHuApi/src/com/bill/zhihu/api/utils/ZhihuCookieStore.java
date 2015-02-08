package com.bill.zhihu.api.utils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 持久化cookies数据
 * 
 * @author Bill Lv
 *
 */
public class ZhihuCookieStore implements CookieStore {

	private static final String COOKIE_FILE_NAME = "zhihu_cookies";
	private static final String TAG = ZhihuCookieStore.class.getName();
	private SharedPreferences sp;

	private ConcurrentHashMap<String, Cookie> cookies;

	public ZhihuCookieStore(Context mContext) {
		sp = mContext.getSharedPreferences(COOKIE_FILE_NAME,
				Context.MODE_PRIVATE);
		cookies = new ConcurrentHashMap<>();
	}

	@Override
	public void addCookie(Cookie cookie) {

	}

	@Override
	public void clear() {
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		cookies.clear();
	}

	@Override
	public boolean clearExpired(Date date) {
		return false;
	}

	@Override
	public List<Cookie> getCookies() {
		return null;
	}

}
