package com.bill.zhihu.api.cookie;

import android.net.Uri;

import com.bill.zhihu.api.utils.ZhihuLog;

import java.net.HttpCookie;
import java.util.List;

/**
 * Cookie 的管理工具类 用来获取一些cookie的状态或者值
 *
 * @author Bill Lv
 */
public class ZhihuCookieManager {

    public static final String TAG = "ZhihuCookieManager";

    /**
     * 查看当前cookie store中是否含有某个cookie
     *
     * @param name
     * @return
     */
    public static boolean haveCookieName(String name) {
        List<HttpCookie> cookies = URLCookiesStore.getInstance().getCookies();

        for (HttpCookie cookie : cookies) {
            ZhihuLog.dValue(TAG, "cookie", cookie.getName());
            ZhihuLog.dValue(TAG, "cookie value", cookie.getValue());
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
        List<HttpCookie> cookies = URLCookiesStore.getInstance().getCookies();
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 判断 cookie 是否为空
     *
     * @return
     */
    public static boolean isCookieEmpty() {
        return URLCookiesStore.getInstance().getCookies().size() == 0 ? true : false;
    }

    public static boolean clearCookie(){
        return URLCookiesStore.getInstance().removeAll();
    }

    public static boolean setLoginCookies(){
//        URLCookiesStore.getInstance().add(Uri.parse("https://api.zhihu.com"),new HttpCookie(""));
        return false;
    }

}
