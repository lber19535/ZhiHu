package com.bill.zhihu.api.cookie;

import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.Cookie;

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
        List<Cookie> cookies = PersistentCookiesStore.getInstance().getCookies();

        for (Cookie cookie : cookies) {
            Logger.t(TAG).d("cookie" + cookie.name());
            Logger.t(TAG).d("cookie value" + cookie.value());
            if (cookie.name().equals(name)) {
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
        List<Cookie> cookies = PersistentCookiesStore.getInstance().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.name().equals(name)) {
                return cookie.value();
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
        return PersistentCookiesStore.getInstance().getCookies().size() == 0;
    }

    public static boolean clearCookie() {
        return PersistentCookiesStore.getInstance().removeAll();
    }

    public static boolean setLoginCookies() {
//        PersistentCookiesStore.getInstance().add(Uri.parse("https://api.zhihu.com"),new HttpCookie(""));
        return false;
    }

}
