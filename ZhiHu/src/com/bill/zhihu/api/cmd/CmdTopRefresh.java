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
 * 知乎time line 下拉刷新
 * 
 * @author Bill Lv
 *
 */
public class CmdTopRefresh extends Command {

    private CallbackListener listener;
    private TopRefreshParams topRefreshParams;

    @Override
    public void exec() {

        ZhihuStringRequest request = new ZhihuStringRequest(Method.POST,
                ZhihuURL.MORE_STORY, new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ZhihuLog.d(TAG, response);
                        /*
                         * 结构是{r:0, msg:[array]}
                         * 
                         * array中是item条目，没一个条目是一个html片段
                         * 
                         * Elements eles = doc.select("div");
                         * 每一个div标签下就是一个item，每个item中包含了问题赞同数，评论数，回答数，问题链接，
                         * 答案链接，出现在timeline的原因 等内容
                         * 
                         * Elements itemInner =
                         * element.select("div[class=feed-item-inner]");
                         */
                        try {
                            JSONArray array = new JSONObject(response)
                                    .getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                String html = array.getString(i);
                                Document doc = Jsoup.parse(html);
                                // feed-item/feed-item-inner/avatar
                                // 赞或关注问题的人，来自哪个tag的头像
                                // title
                                Elements eles = doc.select("div")
                                        .select("div[class=feed-item-inner]")
                                        .select("div[class=avatar]")
                                        .select("a").select("img");
                                String avatarImgUrl = eles.first().attr("src");

                                // source
                                eles = doc.select("div")
                                        .select("div[class=feed-item-inner]")
                                        .select("div[class=feed-main]")
                                        .select("div[class=source]");
                                String peopleName = eles.first().select("a")
                                        .text();
                                String peopleHomePageUrl = eles.first()
                                        .select("a").attr("href");
                                String time = eles.first()
                                        .select("span[class=time]")
                                        .attr("href");

                                // content
                                eles = doc.select("div")
                                        .select("div[class=feed-item-inner]")
                                        .select("div[class=feed-main]")
                                        .select("div[class=content]");

                                String questionTitle = eles.select("h2")
                                        .select("a").text();
                                String questionUrl = eles.select("h2")
                                        .select("a").attr("herf");

                                // feed-item/feed-item-inner/feed-main/source
                                // content表示是来自，
                                // 还是问题被很多人关注，还是赞了什么问题或者是关注
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

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
    public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
        this.listener = (CallbackListener) callback;
    }

    public interface CallbackListener extends CommandCallback {
        public void callback(int code, Bitmap captch);
    }

}
