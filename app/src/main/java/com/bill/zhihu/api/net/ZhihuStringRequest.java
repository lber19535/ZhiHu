package com.bill.zhihu.api.net;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bill.zhihu.api.utils.NetConstants;

/**
 * 作为所有Request的基础，自定义UA
 * 
 * @author Bill Lv
 *
 */
public class ZhihuStringRequest extends GzipStringRequest {

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
        header.put("User-Agent", NetConstants.UA);
        header.put("Accept-Encoding", "gzip, deflate");
        return header;
    }

}
