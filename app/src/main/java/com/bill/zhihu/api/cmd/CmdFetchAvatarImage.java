package com.bill.zhihu.api.cmd;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.net.ZhihuImageRequest;
import com.bill.zhihu.api.utils.ZhihuLog;

public class CmdFetchAvatarImage extends Command {

    private CallbackListener listener;
    private ZhihuImageRequest request;

    private String url;

    public CmdFetchAvatarImage(String imgUrl) {
        this.url = imgUrl;
    }

    @Override
    public void exec() {
        request = new ZhihuImageRequest(url, new Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap avatarImg) {
                ZhihuLog.d(TAG, "fetch the avaatar img");
                listener.callback(avatarImg);
            }
        }, 0, 0, Config.ARGB_8888, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ZhihuLog.d(TAG, "fetch the captcha img faild");
                ZhihuLog.d(TAG, error.networkResponse.statusCode);
                ZhihuLog.d(TAG, new String(error.networkResponse.data));

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
        this.listener = (CallbackListener) callback;
    }

    public interface CallbackListener extends CommandCallback {
        public void callback(Bitmap captchaImg);
    }
}
