package com.bill.zhihu.api.net;

import java.util.List;

import org.apache.http.cookie.Cookie;

/**
 * Cookie 的管理工具类 用来获取一些cookie的状态或者值
 * 
 * @author Bill Lv
 *
 */
public class ZhihuCookieManager {

    /**
     * 查看当前cookie store中是否含有某个cookie
     * 
     * @param name
     * @return
     */
    public static boolean haveCookieName(String name) {
        ZhihuCookieStore cookieStore = new ZhihuCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取某个cookie的value值
     * 
     * @param name
     * @return
     */
    public static String getCookieValue(String name) {
        ZhihuCookieStore cookieStore = new ZhihuCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
