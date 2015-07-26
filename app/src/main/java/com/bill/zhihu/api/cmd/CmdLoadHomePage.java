package com.bill.zhihu.api.cmd;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ToastUtil;
import com.bill.zhihu.api.utils.ZhihuApiParser;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

import java.util.List;
import java.util.Map;

/**
 * 首页，只在开始加载首页的时候使用
 *
 * @author Bill Lv
 */
public class CmdLoadHomePage extends Command {

    private CallbackListener listener;

    @Override
    public void exec() {
        ZhihuLog.dFlag(TAG, "load home page start");

        String url = ZhihuURL.HOST;
        ZhihuStringRequest request = new ZhihuStringRequest(url,
                new Listener<String>() {

                    @Override
                    public void onResponse(final String response) {
                        // ZhihuLog.d(TAG, response);

                        new AsyncTask<Void, Void, List<TimeLineItem>>() {
                            @Override
                            protected List<TimeLineItem> doInBackground(Void... params) {
                                return ZhihuApiParser
                                        .parseTimeLineItems(response);
                            }

                            @Override
                            protected void onPostExecute(List<TimeLineItem> items) {
                                super.onPostExecute(items);
                                listener.callback(items);
                                ZhihuLog.dFlag(TAG, "load home page end");
                            }
                        }.execute();
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ZhihuLog.d(TAG, error);
                ToastUtil.showShortToast(error.getMessage());
                listener.callback(null);
                ZhihuLog.dFlag(TAG, "load home page end");
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

    @Override
    public void cancel() {

    }

    public interface CallbackListener extends CommandCallback {
        /**
         * 如果加载到东西 则返回list，如果连接出错，则返回null
         *
         * @param timelineItems
         */
        void callback(List<TimeLineItem> timelineItems);
    }

}
