package com.bill.zhihu.api.utils;

import android.os.Build;

/**
 * Created by bill_lv on 2016/2/17.
 */
public class XHeaders {

    public static final String X_API_VERSION = "x-api-version: 3.0";
    public static final String X_APP_VERSION = "x-app-version: 2.4.4";
    // x-app-za
    public static final String X_APP_ZA = "x-app-za: OS=Android" +
            "&Release=" + Build.VERSION.RELEASE +
            "&Model=" + Build.MODEL.replace(" ", "+") +
            "&VersionName=2.4.4" +
            "&VersionCode=244" +
            "&Width=" + Build.MODEL.split(" ")[Build.MODEL.split(" ").length - 1].split("x")[0] +
            "&Height=" + Build.MODEL.split(" ")[Build.MODEL.split(" ").length - 1].split("x")[1] +
            "&Installer=%E7%9F%A5%E4%B9%8E";
    public static final String ZHIHU_UA = "User-Agent: ZhihuApi/1.0.0-beta Google-HTTP-Java-Client/1.20.0 (gzip) Google-HTTP-Java-Client/1.20.0 (gzip)";
    public static final String GOOGLE_UA = "User-Agent: Google-HTTP-Java-Client/1.20.0 (gzip)";
    public static final String ACCEPT_ENCODE = "Accept-Encoding: gzip";
    public static final String API_HOST = "Host: api.zhihu.com";

}
