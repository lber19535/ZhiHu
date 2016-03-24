package com.bill.zhihu.api.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by bill_lv on 2016/3/24.
 */
public class ZhihuCookieJar implements CookieJar {

    private final PersistentCookiesStore store = PersistentCookiesStore.getInstance();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            store.add(url.uri(),cookie);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return store.get(url.uri());
    }
}
