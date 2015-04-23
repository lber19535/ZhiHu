package com.bill.zhihu.api.cmd;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.jeson.Jeson;
import com.bill.zhihu.api.bean.TopFeedListParams;
import com.bill.zhihu.api.bean.TopRefreshParams;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.NetConstants;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

/**
 * 知乎time line 下拉刷新/更多动态
 * 
 * @author Bill Lv
 *
 */
public class CmdTopRefresh extends Command {

    private CallbackListener listener;
    private TopRefreshParams topRefreshParams;
    private ZhihuStringRequest request;

    @Override
    public void exec() {

        request = new ZhihuStringRequest(Method.POST, ZhihuURL.MORE_STORY,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ZhihuLog.d(TAG, response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ZhihuLog.d(TAG, error);
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
                ZhihuLog.d(TAG, "xsrf " + xsrf);
                params.put(NetConstants.XSRF, xsrf);
                try {
                    TopRefreshParams topRefreshParams = new TopRefreshParams();
                    ZhihuLog.d(TAG,
                            "params " + Jeson.bean2String(topRefreshParams));
                    params.put("params", Jeson.bean2String(topRefreshParams));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                params.put("method", "next");
                return params;
            }
        };
        volley.addQueue(request);

    }

    @Override
    public void cancel() {
        request.cancel();
    }

    @Override
    public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
        this.listener = (CallbackListener) callback;
    }

    public interface CallbackListener extends CommandCallback {
        public void callback(int code, Bitmap captch);
    }

}
