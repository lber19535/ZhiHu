package com.bill.zhihu.api.cmd;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.jeson.Jeson;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TopFeedListParams;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.NetConstants;
import com.bill.zhihu.api.utils.ToastUtil;
import com.bill.zhihu.api.utils.ZhihuApiParser;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知乎time line 上拉加载更多
 *
 * @author Bill Lv
 */
public class CmdLoadMore extends Command {

    private CallbackListener listener;
    private TopFeedListParams topFeedParams;
    private ZhihuStringRequest request;

    public CmdLoadMore(long blockId, int offset) {
        topFeedParams = new TopFeedListParams();
        topFeedParams.setBlockId(blockId);
        topFeedParams.setOffset(offset);
    }

    @Override
    public void exec() {

        ZhihuLog.dFlag(TAG, "load more start");

        request = new ZhihuStringRequest(Method.POST, ZhihuURL.MORE_STORY,
                new Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
//                        ZhihuLog.d(TAG, response);

                        new AsyncTask<Void, Void, List<TimeLineItem>>() {
                            @Override
                            protected List<TimeLineItem> doInBackground(Void... params) {
                                List<String> htmlItems = new ArrayList<String>();
                                try {
                                    JSONArray msgs = new JSONObject(response)
                                            .getJSONArray("msg");
                                    for (int i = 0; i < msgs.length(); i++) {
                                        String htmlItem = msgs.getString(i);
                                        htmlItems.add(htmlItem);
                                    }
                                    return ZhihuApiParser
                                            .parseTimeLineItems(htmlItems);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return ZhihuApiParser
                                        .parseTimeLineItems(response);
                            }

                            @Override
                            protected void onPostExecute(List<TimeLineItem> items) {
                                super.onPostExecute(items);
                                listener.callback(items);
                                ZhihuLog.dFlag(TAG, "load more end");
                            }
                        }.execute();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ZhihuLog.d(TAG, error);
                ToastUtil.showShortToast(error.getMessage());
                ZhihuLog.dFlag(TAG, "load more start");
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
                    ZhihuLog.d(TAG,
                            "params " + Jeson.bean2String(topFeedParams));
                    params.put("params", Jeson.bean2String(topFeedParams));
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
    public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
        this.listener = (CallbackListener) callback;
    }

    public interface CallbackListener extends CommandCallback {
        public void callback(List<TimeLineItem> items);
    }

    @Override
    public void cancel() {
        request.cancel();
    }

}
