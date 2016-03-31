package com.bill.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.utils.XHeaders;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Application
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

        Fresco.initialize(this);

        if (BuildConfig.DEBUG) {
            CrashReport.initCrashReport(this, "900009454", true);
            Logger.init(TAG);
        } else {
            CrashReport.initCrashReport(this, "900009454", false);
            Logger.init(TAG).logLevel(LogLevel.NONE);
        }

        XHeaders.init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Resources getRes() {
        return mContext.getResources();
    }

}
