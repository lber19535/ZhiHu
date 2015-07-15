package com.bill.zhihu.api.cmd;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuApiParser;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * Created by Bill-pc on 2015/7/4.
 */
public class CmdLoadAnswer extends Command {


    private static final String TAG = "CmdLoadAnswer";

    private String answerUrl;
    private ZhihuStringRequest request;
    private CallBackListener listener;

    public CmdLoadAnswer(String answerUrl) {
        this.answerUrl = answerUrl;

    }

    @Override
    public void exec() {
        request = new ZhihuStringRequest(answerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                listener.callBack(ZhihuApiParser.parseAnswerContent(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ZhihuLog.dValue(TAG, "error code", error.networkResponse);
            }
        });

        volley.addQueue(request);
    }

    @Override
    public void cancel() {
        request.cancel();
    }

    @Override
    public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
        this.listener = (CallBackListener) callback;
    }

    public interface CallBackListener extends CommandCallback {
        void callBack(AnswerContent content);
    }
}
