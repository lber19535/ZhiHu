package com.bill.zhihu.api.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.jeson.Jeson;
import com.bill.zhihu.api.bean.MoreNewsParams;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.NetConstants;
import com.bill.zhihu.api.utils.ZhihuApiParser;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

/**
 * 知乎time line 下拉刷新/更多动态
 * 
 * @author Bill Lv
 *
 */
public class CmdMoreNews extends Command {

    private CallbackListener listener;
    private ZhihuStringRequest request;

    @Override
    public void exec() {

        request = new ZhihuStringRequest(Method.POST, ZhihuURL.MORE_STORY,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ZhihuLog.d(TAG, response);
                        List<String> htmlItems = new ArrayList<String>();
                        try {
                            JSONArray msgs = new JSONObject(response).getJSONArray("msg");
                            for (int i = 0; i < msgs.length(); i++) {
                                String htmlItem = msgs.getString(i);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                        ZhihuApiParser.parseTimeLineItems(response);
                        listener.callback(null);
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
                /*
                 * post data format:
                 * 
                 * params:{"action":"more"}
                 * method:next
                 * _xsrf:35f2fdf15b9b1fbc802041d14f6f75dd
                 * 
                 */
                Map<String, String> params = new HashMap<String, String>();
                ZhihuLog.d(TAG, "xsrf " + xsrf);
                params.put(NetConstants.XSRF, xsrf);
                try {
                    MoreNewsParams moreNewsParams = new MoreNewsParams();
                    ZhihuLog.d(TAG,
                            "params " + Jeson.bean2String(moreNewsParams));
                    params.put("params", Jeson.bean2String(moreNewsParams));
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
        public void callback(List<TimeLineItem> items);
    }

}
