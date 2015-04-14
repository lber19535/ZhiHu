package com.bill.zhihu.api.cmd;

import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuApiParser;
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
//                        ZhihuLog.d(TAG, response);

                        listener.callback(ZhihuApiParser.parseTimeLineItems(response));

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
        void callback(List<TimeLineItem> timelineItems);
    }

}
