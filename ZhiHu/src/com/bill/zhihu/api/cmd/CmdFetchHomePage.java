package com.bill.zhihu.api.cmd;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

/**
 * 首页
 * 
 * @author Bill Lv
 *
 */
public class CmdFetchHomePage extends Command {

	private CallbackListener listener;

	@Override
	public void exec() {
		String url = ZhihuURL.HOST;
		ZhihuStringRequest request = new ZhihuStringRequest(url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						ZhihuLog.d(TAG, response);
						Document doc = Jsoup.parse(response);
						// 获取问题列表
						Elements feedListElements = doc.select(
								"div[id=js-home-feed-list]").select(
								"div[id~=feed]");
						int feedListLength = feedListElements.size();

						ZhihuLog.d(TAG, "feed List Length " + feedListLength);

						for (Element element : feedListElements) {
							ZhihuLog.d(TAG, element.attr("class"));
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						ZhihuLog.d(TAG, error);
					}
				}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = super.getHeaders();
				headers.put("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				headers.put("Referer", "http://www.zhihu.com/");
				headers.put("Host", "www.zhihu.com");
				return headers;
			}

		};
		volley.addQueue(request);

	}

	@Override
	public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
		listener = (CallbackListener) callback;
	}

	public interface CallbackListener extends CommandCallback {
		void callback(String homepage);
	}

}
