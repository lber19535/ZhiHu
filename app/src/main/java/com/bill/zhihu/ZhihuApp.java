package com.bill.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.utils.XHeaders;
import com.bill.zhihu.util.InitUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Application
 */
public class ZhihuApp extends Application {
    
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        InitUtils.init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Resources getRes() {
        return mContext.getResources();
    }

}
