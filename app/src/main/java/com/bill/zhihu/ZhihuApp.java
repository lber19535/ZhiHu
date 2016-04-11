package com.bill.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.bill.zhihu.util.InitUtils;

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
