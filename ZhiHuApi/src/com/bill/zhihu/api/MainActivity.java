package com.bill.zhihu.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private RequestQueue queue;
	private boolean login;
	private String xsrf;
	private ImageView captchaIv;
	private EditText captchaEdt;
	private String captcha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		queue = Volley.newRequestQueue(this, new HttpClientStack(
				new DefaultHttpClient()));
		ZhihuVolley.getInstance(this);

		findViewById(R.id.home).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				homePage();
			}
		});
		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});

		captchaIv = (ImageView) findViewById(R.id.captcha);
		captchaEdt = (EditText) findViewById(R.id.captcha_code);

	}

	private void homePage() {
		ZhihuStringRequest request = new ZhihuStringRequest(ZhihuURL.HOME_PAGE,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						Elements e = doc.select("input[name=_xsrf]");
						xsrf = e.attr("value");
						System.out
								.println("------------------------------initZhihuHomePage--------------------------");
						System.out.println(response);

					}

				}, null);
		queue.add(request);
	}

	private void login() {
		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						// Elements e = doc.select("input[name=_xsrf]");
						// String xsrf = e.attr("value");
						System.out
								.println("------------------------------login--------------------------");
						System.out.println(response);
						String captcha = doc
								.select("img[class=js-captcha-img]")
								.attr("src");
						System.out.println(captcha);

						ImageRequest a = new ImageRequest(ZhihuURL.HOME_PAGE
								+ captcha, new Response.Listener<Bitmap>() {

							@Override
							public void onResponse(Bitmap arg0) {
								captchaIv.setImageBitmap(arg0);
							}

						}, 120, 30, Config.ARGB_8888,
								new Response.ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError arg0) {

									}
								});
						queue.add(a);
					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("email", "xxxx");
				params.put("password", "xxx");
				params.put("rememberme", "y");
				params.put("captcha", captchaEdt.getText().toString().trim());
				return params;
			}
		};
		queue.add(request);
	}

	private void timeLine() {
		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						System.out
								.println("------------------------------time line--------------------------");
						System.out.println(response);

					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("action", "next");
					jsonObject.put("block_id", 1422777494);
					jsonObject.put("offset", 16);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params.put("params", jsonObject.toString());
				params.put("method", "next");
				params.put("_xsrf", xsrf);
				return params;
			}
		};
		queue.add(request);
	}

}
