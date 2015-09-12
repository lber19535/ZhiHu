package com.bill.zhihu.api.utils;

/**
 * 知乎对一些资源的 url 不是很规范，这个工具类用来确保一些容易出问题的 url 变得正常
 *
 * Created by Bill Lv on 2015/9/12.
 */
public class UrlUtils {

    public static String avatarUrlParse(String avatarUrl){
        if (!avatarUrl.startsWith("http"))
            return "http:" + avatarUrl;
        else
            return avatarUrl;
    }
}
