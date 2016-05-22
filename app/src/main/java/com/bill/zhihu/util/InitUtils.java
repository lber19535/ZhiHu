package com.bill.zhihu.util;

import android.app.Application;
import android.content.Context;
import android.os.HandlerThread;
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
    private static final int IMAGE_LOADER_THREAD_POOL_SIZE = 6;

    public static void init(final Application application) {
        initLogger();
        initApi(application);
        initImageLib(application);

        initLeakCanary(application);
        initBlockCanary(application);
        initDebug(application);
        initPref(application);


    }

    private static void initApi(Context context) {
        ZhihuApi.init(context);
    }

    private static void initImageLib(Context context) {
        // cache the image in local file
        DisplayImageOptions opt = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(opt)
                .threadPoolSize(IMAGE_LOADER_THREAD_POOL_SIZE)
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private static void initLogger() {
        if (BuildConfig.DEBUG) {
            Logger.init(TAG);
        } else {
            Logger.init(TAG).logLevel(LogLevel.NONE);
        }
    }

    private static void initDebug(final Context context) {
        Logger.t(TAG).d("init bugly");
        Observable.just("init bugly").subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.t(TAG).d("init bugly complete");
            }

            @Override
            public void onError(Throwable e) {
                Logger.t(TAG).d(e.toString());
            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG) {
                    CrashReport.initCrashReport(context, "900009454", true);
                } else {
                    CrashReport.initCrashReport(context, "900009454", false);
                }
            }
        });
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
        Logger.t(TAG).d("init pref");
        Observable.just("init pref thread").subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.t(TAG).d("init pref complete");
            }

            @Override
            public void onError(Throwable e) {
                Logger.t(TAG).d(e.toString());
            }

            @Override
            public void onNext(String s) {
                Pref.init(application);
            }
        });
    }


}
