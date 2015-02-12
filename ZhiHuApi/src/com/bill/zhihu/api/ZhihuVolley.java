package com.bill.zhihu.api;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
import com.bill.zhihu.api.utils.ZhihuCookieStore;

/**
 * 单例，所有request由一个request queue统一管理
 * 
 * @author Bill Lv
 *
 */
public class ZhihuVolley {

	private RequestQueue queue;
	private static ZhihuVolley zhihuVolley;
	private DefaultHttpClient client;

	private ZhihuVolley(Context mContext) {

		ZhihuCookieStore cookieStore = new ZhihuCookieStore(mContext);

		client = new DefaultHttpClient();
		client.setCookieStore(cookieStore);

		queue = Volley.newRequestQueue(mContext, new HttpClientStack(client));
	}

	public static ZhihuVolley getInstance(Context mContext) {
		if (zhihuVolley == null) {
			zhihuVolley = new ZhihuVolley(mContext);
		}
		return zhihuVolley;
	}

	public <T> void addQueue(Request<T> request) {
		queue.add(request);
	}

	public RequestQueue getQueue() {
		return queue;
	}

}
