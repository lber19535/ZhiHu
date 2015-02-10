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
import com.bill.zhihu.api.ZhihuLog;
import com.bill.zhihu.api.ZhihuStringRequest;
import com.bill.zhihu.api.ZhihuURL;
import com.bill.zhihu.api.ZhihuVolley;
import com.bill.zhihu.api.binder.aidl.ILogin;
import com.bill.zhihu.api.binder.aidl.ILoginService;
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

	private ILoginService.Stub mLoginService = new ILoginService.Stub() {

		@Override
		public void unregisterCallback(ILoginServiceCallback cb)
				throws RemoteException {
			// TODO Auto-generated method stub
			callBack = null;
		}

		@Override
		public void registerCallback(ILoginServiceCallback cb)
				throws RemoteException {
			// TODO Auto-generated method stub
			callBack = cb;

		}
	};

	private ILogin.Stub mLogin = new ILogin.Stub() {

		@Override
		public int login(String account, String pwd, String captcha)
				throws RemoteException {
			ZhihuLog.d(TAG, "account " + account);
			ZhihuLog.d(TAG, "pwd " + pwd);
			ZhihuLog.d(TAG, "captcha " + captcha);
			loginWeb(account, pwd, captcha);
			return 2333;
		}

		@Override
		public boolean haveCaptcha() throws RemoteException {
			return haveCaptcha;
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
		String action = intent.getAction();
		ZhihuLog.d(TAG, "action " + action);
		String className = intent.getStringExtra("CLASS");
		ZhihuLog.d(TAG, "className  " + className);
		if (ILogin.class.getName().equals(className)) {
			System.out.println("mLogin");
			return mLogin;
		} else if (ILoginService.class.getName().equals(className)) {
			System.out.println("mLoginService");
			return mLoginService;
		}
		return null;
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
						ZhihuLog.d(TAG, "response " + response);
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

}
