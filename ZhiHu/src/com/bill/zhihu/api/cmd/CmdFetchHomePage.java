package com.bill.zhihu.api.cmd;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuURL;

public class CmdFetchHomePage extends Command {

	@Override
	public void exec() {
		String url = ZhihuURL.HOST;
		ZhihuStringRequest request = new ZhihuStringRequest(url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
		volley.addQueue(request);

	}

	@Override
	public <T extends CommandCallback> void setOnCmdCallBack(T callback) {

	}

}
