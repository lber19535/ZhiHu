package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 持久化cookies数据
 * 
 * @author Bill Lv
 *
 */
public class ZhihuCookieStore implements CookieStore {

	private static final String COOKIE_FILE_NAME = "zhihu_cookies";
	private static final String TAG = ZhihuCookieStore.class.getName();
	private static final String COOKIE_STORE_NAMES = "cookies_names";
	private SharedPreferences sp;

	private ConcurrentHashMap<String, Cookie> cookies;

	public ZhihuCookieStore(Context mContext) {
		sp = mContext.getSharedPreferences(COOKIE_FILE_NAME,
				Context.MODE_PRIVATE);
		cookies = new ConcurrentHashMap<>();

	}

	@Override
	public void addCookie(Cookie cookie) {

		String name = cookie.getName() + cookie.getDomain();

		if (cookie.isExpired(new Date())) {
			cookies.put(name, cookie);
		} else {
			cookies.remove(name);
		}

		Editor editor = sp.edit();
		editor.putString(COOKIE_STORE_NAMES,
				TextUtils.join(",", cookies.keySet()));
		editor.commit();

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
		boolean clear = false;
		Editor editor = sp.edit();
		Set<Entry<String, Cookie>> cookiesEntry = cookies.entrySet();
		for (Entry<String, Cookie> entry : cookiesEntry) {
			String name = entry.getKey();
			Cookie cookie = entry.getValue();
			if (cookie.isExpired(date)) {
				cookies.remove(name);

				editor.remove(name);

				clear = true;
			}
		}
		editor.commit();
		return clear;
	}

	@Override
	public List<Cookie> getCookies() {
		return new ArrayList<>(cookies.values());
	}

}
