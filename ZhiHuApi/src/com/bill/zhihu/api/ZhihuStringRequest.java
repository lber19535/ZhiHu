package com.bill.zhihu.api;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
/**
 * 作为所有Request的基础，自定义UA
 * 
 * @author Bill Lv
 *
 */
public class ZhihuStringRequest extends StringRequest {

	// User agent
	private static final String UA = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

	private final Listener<String> mListener;

	public ZhihuStringRequest(int method, String url,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		mListener = listener;
	}

	public ZhihuStringRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> header = new HashMap<String, String>();
		header.put("User-agent", UA);
		return header;
	}

}
