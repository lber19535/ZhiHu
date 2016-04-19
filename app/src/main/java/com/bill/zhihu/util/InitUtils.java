package com.bill.zhihu.util;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.StrictMode;

import com.bill.zhihu.BuildConfig;
import com.bill.zhihu.api.ZhihuApi;
import com.github.moduth.blockcanary.BlockCanary;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tramsun.libs.prefcompat.Pref;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * initialize some lib or plugin
 * <p/>
 * Created by bill_lv on 2016/4/5.
 */
public class InitUtils {

    private static final String TAG = "ZhihuApp";

    public static void init(Application application) {
        initApi(application);
        initImageLib(application);
        initDebug(application);
        initLeakCanary(application);
        initBlockCanary(application);
        initPref(application);
    }

    private static void initApi(Context context) {
        ZhihuApi.init(context);
    }

    private static void initImageLib(Context context) {
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
        Logger.d("xxxx");
    }

    private static void initLeakCanary(Application application) {
        LeakCanary.install(application);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    private static void initBlockCanary(Application application) {
        if (BuildConfig.DEBUG) {
            BlockCanary.install(application, new AppBlockCanaryContext()).start();
        }
    }

    private static void initPref(final Application application) {
        // because use strict mode of thread, so io behaviors must be used in background
        Observable.just("init pref").subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.t(TAG).d("init pref complete");
            }

            @Override
            public void onError(Throwable e) {
                Logger.t(TAG).e(e.toString());
            }

            @Override
            public void onNext(String s) {
                Pref.init(application);
            }
        });
    }


}
