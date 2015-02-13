package com.bill.zhihu.api.cmd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Response.Listener;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.ZhihuLog;
import com.bill.zhihu.api.ZhihuStringRequest;
import com.bill.zhihu.api.ZhihuURL;

public class CmdFetchXSRF extends Command {

	private CallbackListener listener;
	private SharedPreferences sp;

	public CmdFetchXSRF() {
		sp = PreferenceManager.getDefaultSharedPreferences(ZhihuApp
				.getContext());
		xsrf = sp.getString(ZhihuApi.XSRF, null);
		if (xsrf == null) {
			fetchKeyWords();
		} else {
			listener.callback(xsrf);
		}
	}

	@Override
	public void exec() {
		fetchKeyWords();
	}

	@Override
	public void setOnCmdCallBack(CommandCallback callback) {
		this.listener = (CallbackListener) callback;
	}

	private void fetchKeyWords() {
		ZhihuStringRequest request = new ZhihuStringRequest(ZhihuURL.HOME_PAGE,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						xsrf = doc.select("input[name=_xsrf]").attr("value");
						ZhihuLog.d(TAG, "_xsrf " + xsrf);
						listener.callback(xsrf);
						sp.edit().putString(ZhihuApi.XSRF, xsrf);
					}

				}, null);
		volley.addQueue(request);
	}

	public interface CallbackListener extends CommandCallback {
		void callback(String xsrf);
	}

}
