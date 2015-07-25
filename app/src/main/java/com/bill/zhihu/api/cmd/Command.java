package com.bill.zhihu.api.cmd;

import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.net.ZhihuVolley;

public abstract class Command {

    protected final String TAG = getClass().getSimpleName();
    protected ZhihuVolley volley;
    protected static String xsrf;

    public Command() {
        volley = ZhihuVolley.getInstance(ZhihuApp.getContext());
        xsrf = ZhihuApi.getXSRF();
    }

    abstract public void exec();

    abstract public void cancel();

    abstract public <T extends CommandCallback> void setOnCmdCallBack(T callback);

    public interface CommandCallback {

    }

}
