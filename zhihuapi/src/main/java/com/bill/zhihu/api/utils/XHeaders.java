package com.bill.zhihu.api.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * Created by bill_lv on 2016/2/17.
 */
public class XHeaders {

    public static final String ZHUA_KEY = "User-Agent";
    public static final String ZHUA_VALUE = "ZhihuApi/1.0.0-beta Google-HTTP-Java-Client/1.20.0 (gzip) Google-HTTP-Java-Client/1.20.0 (gzip)";
    public static final String GOOGLEUA_KEY = "User-Agent";
    public static final String GOOGLEUA_VALUE = "Google-HTTP-Java-Client/1.20.0 (gzip)";
    public static final String CONTENT_KEY = "Content-Type";
    public static final String CONTENT_VALUE = "application/x-www-form-urlencoded; charset=UTF-8";
    public static final String HOST_KEY = "Host";
    public static final String HOST_VALUE = "api.zhihu.com";
    public static final String CONNECTION_KEY = "Connection";
    public static final String CONNECTION_VALUE = "Keep-Alive";
    public static final String API_VERSION_KEY = "x-api-version";
    public static final String API_VERSION_VALUE = "3.0";
    public static final String APP_VERSION_KEY = "x-app-version";
    public static final String APP_VERSION_VALUE = "2.4.4";
    public static final String APP_ZA_KEY = "x-app-za";
    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String AUTHORIZATION_VALUE = "oauth 8d5227e0aaaa4797a763ac64e0c3b8";
    public static final String ACCOUNT_UNLOCK_KEY = "x-account-unlock";

    private static int width;
    private static int height;

    public static void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    /**
     * Get X_APP_ZA
     *
     * @return
     */
    public static String getZA() {
        return "OS=Android" +
                "&Release=" + Build.VERSION.RELEASE +
                "&Model=" + Build.MODEL.replace(" ", "+") +
                "&VersionName=2.4.4" +
                "&VersionCode=244" +
                "&Width=" + width +
                "&Height=" + height +
                "&Installer=%E7%9F%A5%E4%B9%8E";
    }

}
