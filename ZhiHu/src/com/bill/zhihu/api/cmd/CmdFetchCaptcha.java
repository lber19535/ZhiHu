package com.bill.zhihu.api.cmd;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuImageRequest;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

public class CmdFetchCaptcha extends Command {

	private CallbackListener linstener;
	private long time;
	private String url;

	public CmdFetchCaptcha() {
		time = System.currentTimeMillis();
		url = ZhihuURL.CAPTCHA + "?r=" + time;
		ZhihuLog.d(TAG, "captcha url " + url);
	}

	@Override
	public void exec() {
		ZhihuImageRequest request = new ZhihuImageRequest(url,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap img) {
						ZhihuLog.d(TAG, "fetch the captcha img");
						linstener.callback(img);
					}
				}, 0, 0, Config.ARGB_8888, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						ZhihuLog.d(TAG, "fetch the captcha img faild");
						ZhihuLog.d(TAG, error.networkResponse.statusCode);
						ZhihuLog.d(TAG, new String(error.networkResponse.data));

					}
				});
		volley.addQueue(request);

	}

	@Override
	public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
		linstener = (CallbackListener) callback;
	}

	public interface CallbackListener extends CommandCallback {
		public void callback(Bitmap captchaImg);
	}

}
