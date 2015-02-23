package com.bill.zhihu.api.cmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuURL;

/**
 * 知乎time line
 * 
 * @author Bill Lv
 *
 */
public class CmdTopFeed extends Command {

	private CallbackListener listener;

	@Override
	public void exec() {

		ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
				ZhihuURL.MORE_STORY, new Listener<String>() {
					@Override
					public void onResponse(String response) {
						/*
						 * 结构是{r:0, msg:[array]}
						 * 
						 * array中是item条目，没一个条目是一个html片段
						 * 
						 * Elements eles = doc.select("div");
						 * 每一个div标签下就是一个item，每个item中包含了问题赞同数，评论数，回答数，问题链接，
						 * 答案链接，出现在timeline的原因 等内容
						 * 
						 * Elements itemInner = element.select("div[class=feed-item-inner]");
						 */
						try {
							JSONArray array = new JSONObject(response)
									.getJSONArray("msg");
							for (int i = 0; i < array.length(); i++) {
								String html = array.getString(i);
								Document doc = Jsoup.parse(html);
								// feed-item/feed-item-inner/avatar 赞或关注问题的人，来自哪个tag的头像
								Elements eles = doc.select("div").select("div")
										.select("div").select("a");

								String title = eles.attr("title");
								String peopleHomePage = eles.attr("herf");
								String headImgUrl = eles.select("a")
										.attr("src");
								// feed-item/feed-item-inner/feed-main/source content表示是来自，
								// 还是问题被很多人关注，还是赞了什么问题或者是关注
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = super.getHeaders();
				headers.put("X-Requested-With", "XMLHttpRequest");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("_xsrf", xsrf);
				params.put("params",
						"{\"action\":\"next\",\"block_id\":1424618591,\"offset\":17}");
				params.put("method", "next");
				return params;
			}
		};
		volley.addQueue(request);

	}

	@Override
	public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
		this.listener = (CallbackListener) callback;
	}

	public interface CallbackListener extends CommandCallback {
		public void callback(int code, Bitmap captch);
	}

}
