package com.bill.zhihu.util;

import android.content.Context;

import com.bill.zhihu.BuildConfig;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.utils.XHeaders;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by bill_lv on 2016/4/5.
 */
public class InitUtils {

    private static final String TAG = "ZhihuApp";

    public static void init(Context context) {
        initApi(context);
        initImageLib(context);
        initDebug(context);
    }

    private static void initApi(Context context) {
        ZhihuApi.init(context);
    }

    private static void initImageLib(Context context) {
        Fresco.initialize(context);

        // cache the image in local file
        DisplayImageOptions opt = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(opt).build();
        ImageLoader.getInstance().init(configuration);
    }

    private static void initDebug(Context context) {
        if (BuildConfig.DEBUG) {
            CrashReport.initCrashReport(context, "900009454", true);
            Logger.init(TAG);
        } else {
            CrashReport.initCrashReport(context, "900009454", false);
            Logger.init(TAG).logLevel(LogLevel.NONE);
        }
    }


}
