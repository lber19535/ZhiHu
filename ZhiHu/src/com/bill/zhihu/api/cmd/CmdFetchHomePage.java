package com.bill.zhihu.api.cmd;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

public class CmdFetchHomePage extends Command {

	@Override
	public void exec() {
		String url = ZhihuURL.HOST;
		ZhihuStringRequest request = new ZhihuStringRequest(url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZhihuLog.d(TAG, response);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						ZhihuLog.d(TAG, error);

					}
				});
		volley.addQueue(request);

	}

	@Override
	public <T extends CommandCallback> void setOnCmdCallBack(T callback) {

	}

}
