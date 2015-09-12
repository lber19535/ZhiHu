package com.bill.zhihu.api.cmd;

import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.bean.QuestionContent;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuApiParser;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * 加载问题
 * <p/>
 * Created by Bill Lv on 2015/8/12.
 */
public class CmdLoadQuestion extends Command {


    private static final String TAG = "CmdLoadQuestion";

    private String questionUrl;
    private ZhihuStringRequest request;
    private CallBackListener listener;

    public CmdLoadQuestion(String questionUrl) {
        this.questionUrl = questionUrl;

    }

    @Override
    public void exec() {
        request = new ZhihuStringRequest(questionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                // 解析过程比较耗时，做成异步的
                new AsyncTask<Void, Void, QuestionContent>() {
                    @Override
                    protected QuestionContent doInBackground(Void... params) {
                        return ZhihuApiParser.parseQuestionPage(response);
                    }

                    @Override
                    protected void onPostExecute(QuestionContent questionContent) {
                        super.onPostExecute(questionContent);
                        listener.callBack(questionContent);
                        ZhihuLog.dFlag(TAG, "load question end");
                        ZhihuLog.d(TAG, "question answer count is " + questionContent.getAnswers().size());
                    }
                }.execute();


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
        void callBack(QuestionContent content);
    }
}
