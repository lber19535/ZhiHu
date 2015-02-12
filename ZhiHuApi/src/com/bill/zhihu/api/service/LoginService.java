package com.bill.zhihu.api.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.bill.jeson.Jeson;
import com.bill.zhihu.api.TopFeedListParams;
import com.bill.zhihu.api.ZhihuLog;
import com.bill.zhihu.api.ZhihuStringRequest;
import com.bill.zhihu.api.ZhihuURL;
import com.bill.zhihu.api.ZhihuVolley;
import com.bill.zhihu.api.binder.aidl.ILogin;
import com.bill.zhihu.api.binder.aidl.ILoginServiceCallback;

/**
 * 登录服务 1.登录 2.登出 3.验证码
 *
 * 验证码可以通过aidl传递（Bitmap继承了parcelable接口可以直接传递）， 也可以存在本地传递地址，实现的时候分别验证下效率
 * 
 * @author Bill Lv
 *
 */
public class LoginService extends Service {

	private ILoginServiceCallback callBack;

	private ILogin.Stub mLogin = new ILogin.Stub() {

		@Override
		public void login(String account, String pwd, String captcha)
				throws RemoteException {
			ZhihuLog.d(TAG, "account " + account);
			ZhihuLog.d(TAG, "pwd " + pwd);
			ZhihuLog.d(TAG, "captcha " + captcha);
			loginWeb(account, pwd, captcha);
		}

		@Override
		public boolean haveCaptcha() throws RemoteException {
			return haveCaptcha;
		}

		@Override
		public void registerCallback(ILoginServiceCallback cb)
				throws RemoteException {
			callBack = cb;

		}

		@Override
		public void unregisterCallback(ILoginServiceCallback cb)
				throws RemoteException {
			callBack = cb;

		}
	};

	private String xsrf;
	private String captcha;

	private ZhihuVolley volley;

	private boolean haveCaptcha;
	private static final String TAG = LoginService.class.getName();

	@Override
	public void onCreate() {
		super.onCreate();
		volley = ZhihuVolley.getInstance(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		ZhihuLog.d(TAG, "On Bind");
		return mLogin;
	}

	private void fetchKeyWords() {
		ZhihuStringRequest request = new ZhihuStringRequest(ZhihuURL.HOME_PAGE,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						xsrf = doc.select("input[name=_xsrf]").attr("value");
						captcha = doc.select("img[class=js-captcha-img]").attr(
								"src");
						ZhihuLog.d(TAG, "xsrf " + xsrf);
						if (captcha.isEmpty()) {
							haveCaptcha = false;
						} else {
							haveCaptcha = true;
						}
						ZhihuLog.d(TAG, "captcha url " + captcha);
						try {
							callBack.valueChanged("dadsad");
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		volley.addQueue(request);
	}

	private void loginWeb(final String account, final String pwd,
			final String captcha) {
		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZhihuLog.d(TAG, "login response " + response);
						Document doc = Jsoup.parse(response);
						String text = doc.select("div[class=failure]").text();
						if (!text.isEmpty()) {
							Toast.makeText(LoginService.this, text,
									Toast.LENGTH_SHORT).show();
						}
					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("email", account);
				params.put("password", pwd);
				params.put("rememberme", "y");
				if (haveCaptcha) {
					params.put("captcha", captcha);
				}
				return params;
			}
		};
		volley.addQueue(request);
	}

	private void topStoreFeedList(long blockId) {
		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.MORE_STORY, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZhihuLog.d(TAG, "topStoreFeedList response " + response);
					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("method", "next");
				TopFeedListParams mTopFeedListParams = new TopFeedListParams();
				mTopFeedListParams.setAction("next");
				mTopFeedListParams.setBlockId(1L);
				mTopFeedListParams.setOffset(16);
				try {
					params.put("params", Jeson.bean2String(mTopFeedListParams));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return params;
			}
		};
		volley.addQueue(request);
	}

}
