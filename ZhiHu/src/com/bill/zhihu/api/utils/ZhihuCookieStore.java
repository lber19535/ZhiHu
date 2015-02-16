package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.bill.jeson.Jeson;

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

	private final String COOKIE_FILE_NAME = "zhihu_cookies";
	private final String TAG = getClass().getName();
	private final String COOKIE_STORE_NAMES = "cookies_names";
	private SharedPreferences sp;
	private Set<String> cookiesNames;

	private ConcurrentHashMap<String, Cookie> cookies;

	public ZhihuCookieStore(Context mContext) {
		sp = mContext.getSharedPreferences(COOKIE_FILE_NAME,
				Context.MODE_PRIVATE);
		cookies = new ConcurrentHashMap<>();
		cookiesNames = sp.getStringSet(COOKIE_STORE_NAMES,
				new HashSet<String>());
		for (String name : cookiesNames) {
			CookieJson value = null;
			String cookieJson = sp.getString(name, "");
			if (cookieJson.isEmpty()) {
				continue;
			} else {
				try {
					value = Jeson.createBean(CookieJson.class, cookieJson);
					BasicClientCookie cookie = new BasicClientCookie(name,
							Jeson.bean2String(value));
					cookies.put(name, cookie);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addCookie(Cookie cookie) {
		System.out.println(cookie.toString());
		// 获得存储的时候用到的key name
		String name = cookie.getName() + cookie.getDomain();

		if (cookie.isExpired(new Date())) {
			cookies.put(name, cookie);
		} else {
			cookies.remove(name);
		}
		// 将cookie转化成java bean
		CookieJson json = new CookieJson();
		json.setName(cookie.getName());
		json.setCommentUrl(cookie.getCommentURL());
		json.setDomain(cookie.getDomain());
		json.setExpiryDate(cookie.getExpiryDate().getTime());
		json.setPath(cookie.getPath());
		json.setValue(cookie.getValue());
		json.setVersion(cookie.getVersion());

		Editor editor = sp.edit();

		cookiesNames.add(name);
		editor.putStringSet(COOKIE_STORE_NAMES, cookiesNames);
		try {
			// 将java bean转成json字符串
			editor.putString(name, Jeson.bean2String(json));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
