package com.bill.zhihu.api.cmd;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.bill.zhihu.api.ZhihuLog;
import com.bill.zhihu.api.ZhihuStringRequest;
import com.bill.zhihu.api.ZhihuURL;

public class CmdLogin extends Command {

	private CallbackListener listener;
	private String account;
	private String pwd;

	public CmdLogin(String account, String pwd) {
		super();
		this.account = account;
		this.pwd = pwd;
	}

	@Override
	public void exec() {

		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.LOGIN, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZhihuLog.d(TAG, "login response " + response);
						Document doc = Jsoup.parse(response);
						String text = doc.select("div[class=failure]").text();
						listener.callback(text);
					}

				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("email", account);
				params.put("password", pwd);
				params.put("rememberme", "y");
				//				if (haveCaptcha) {
				//					params.put("captcha", captcha);
				//				}
				return params;
			}
		};
		volley.addQueue(request);

	}

	@Override
	public void setOnCmdCallBack(CommandCallback callback) {
		this.listener = (CallbackListener) callback;
	}

	public interface CallbackListener extends CommandCallback {
		public void callback(String code);
	}

}