package com.bill.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.bill.zhihu.api.ZhihuApi;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.UmengRegistrar;

/**
 * Application
 *
 */
public class ZhihuApp extends Application {

    private static final String TAG = "ZhihuApp";

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // set globel context
        ZhihuApi.registerContext(this);
        CrashReport.initCrashReport(this, "900009454", false);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Resources getRes(){
        return mContext.getResources();
    }

}
