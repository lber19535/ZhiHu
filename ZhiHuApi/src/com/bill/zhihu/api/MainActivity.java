package com.bill.zhihu.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private RequestQueue queue;
	private boolean login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		queue = Volley.newRequestQueue(this, new HttpClientStack(
				new DefaultHttpClient()));
		ZhihuVolley.getInstance(this);

		initComHomePage();

	}

	private void initComHomePage() {
		String url = "https://class.coursera.org/compilers-004/lecture?lecture_player=html5";
		ZhihuStringRequest request = new ZhihuStringRequest(url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						Elements e = doc.select("a[target=_new]");
						String address = e.attr("href");
						System.out
								.println("------------------------------initZhihuHomePage--------------------------");
						System.out.println(response);
						login(address);
					}

				}, null);
		queue.add(request);
	}

	private void initZhihuHomePage() {
		ZhihuStringRequest request = new ZhihuStringRequest(ZhihuURL.HOME_PAGE,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						Elements e = doc.select("input[name=_xsrf]");
						String xsrf = e.attr("value");
						System.out
								.println("------------------------------initZhihuHomePage--------------------------");
						System.out.println(response);
						if (!login) {
							login(xsrf);
						}
					}

				}, null);
		queue.add(request);
	}

	private void login(final String xsrf) {
		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// Document doc = Jsoup.parse(response);
						// Elements e = doc.select("input[name=_xsrf]");
						// String xsrf = e.attr("value");
						System.out
								.println("------------------------------login--------------------------");
						System.out.println(response);
						login = true;
						// initZhihuHomePage();
					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("email", "lber19535@126.com");
				params.put("password", "albaer19535");
				params.put("rememberme", "y");
				return params;
			}
		};
		queue.add(request);
	}

}
