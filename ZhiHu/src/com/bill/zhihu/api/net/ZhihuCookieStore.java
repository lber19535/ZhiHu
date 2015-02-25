package com.bill.zhihu.api.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bill.jeson.Jeson;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * 持久化cookies数据
 * 
 * 
 * @author Bill Lv
 *
 */
public class ZhihuCookieStore implements CookieStore {

	private final String COOKIE_FILE_NAME = "zhihu_cookies";
	private final String TAG = getClass().getName();
	private final String COOKIE_KEYS = "cookie_keys";
	private SharedPreferences sp;
	private Set<String> cookiesNames;

	private ConcurrentHashMap<String, Cookie> cookies;

	public ZhihuCookieStore() {
		ZhihuLog.d(TAG, "cookie store is init");
		sp = ZhihuApp.getContext().getSharedPreferences(COOKIE_FILE_NAME,
				Context.MODE_PRIVATE);
		cookies = new ConcurrentHashMap<>();
		/*
		 * cookie的所有key存在一个string set中
		 * cookie的key-value以sharedpreference中的key-value形式存在，value再保存的
		 * 时候会被转换成json对象
		 * 
		 * 初始化的时候先去取到cookie的所有key，然后再根据key去取json对象然后再赋值到cookie对象中
		 */
		cookiesNames = sp.getStringSet(COOKIE_KEYS, new HashSet<String>());
		for (String name : cookiesNames) {
			String cookieJson = sp.getString(name, "");
			if (cookieJson.isEmpty()) {
				continue;
			} else {
				try {
					CookieJson value = Jeson.createBean(CookieJson.class,
							cookieJson);
					BasicClientCookie cookie = new BasicClientCookie(name,
							value.getValue());
					cookie.setComment(value.getComment());
					cookie.setDomain(value.getDomain());
					cookie.setExpiryDate(new Date(value.getExpiryDate()));
					cookie.setPath(value.getPath());
					cookie.setVersion(value.getVersion());
					cookies.put(name, cookie);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 将cookie内容转换成json字符串存在本地
	 */
	@Override
	public void addCookie(Cookie cookie) {
		ZhihuLog.d(TAG, "add cookie " + cookie.getValue());
		// 获得存储的时候用到的key name
		String name = cookie.getName();

		if (cookie.isExpired(new Date())) {
			ZhihuLog.d(TAG, "expired");
			cookies.remove(name);
		} else {
			ZhihuLog.d(TAG, "not expired");
			cookies.put(name, cookie);
		}
		// 将cookie转化成java bean
		CookieJson json = new CookieJson();
		json.setName(cookie.getName());
		json.setCommentUrl(cookie.getCommentURL());
		json.setDomain(cookie.getDomain());
		if (cookie.getExpiryDate() != null) {
			json.setExpiryDate(cookie.getExpiryDate().getTime());
		}
		json.setPath(cookie.getPath());
		json.setValue(cookie.getValue());
		json.setVersion(cookie.getVersion());

		Editor editor = sp.edit();

		cookiesNames.add(name);
		editor.putStringSet(COOKIE_KEYS, cookiesNames);
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
		ZhihuLog.d(TAG, "clear all cookies");
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		cookies.clear();
	}

	@Override
	public boolean clearExpired(Date date) {
		ZhihuLog.d(TAG, "clear expired cookies");

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
		ZhihuLog.d(TAG, "get cookie list");
		return new ArrayList<Cookie>(cookies.values());
	}

}
