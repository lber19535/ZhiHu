package com.bill.zhihu.api.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bill.jeson.Jeson;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.utils.ZhihuLog;


import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久化cookies数据
 * 由于Android 6.0 开始移除了httpclient，所以这里使用net包中的cookiestore，相应的httpclient也换成了 {@code URLConnection}
 * <p/>
 * Created by Bill Lv on 2015/8/30.
 */
public class URLCookiesStore implements CookieStore {

    private final String COOKIE_FILE_NAME = "zhihu_cookies";
    private final String TAG = "URLCookiesStore";
    private SharedPreferences sp;
    private Set<String> cookiesUris;

    private ConcurrentHashMap<URI, List<HttpCookie>> cookieMap;

    private static URLCookiesStore instance;

    private URLCookiesStore() {
        ZhihuLog.d(TAG, "cookie store is init");
        sp = ZhihuApp.getContext().getSharedPreferences(COOKIE_FILE_NAME,
                Context.MODE_PRIVATE);
        cookieMap = new ConcurrentHashMap<>();
        /*
         * key 存 URI， value 存 httpcookie 的 list
         *
         * 初始化的时候先去取到cookie的所有key，然后再根据key去取json对象然后再赋值到cookie对象中
         */
        cookiesUris = sp.getAll().keySet();
        for (String uristr : cookiesUris) {
            URI uri = null;
            try {
                uri = new URI(uristr);
            } catch (URISyntaxException e) {
                ZhihuLog.d(TAG, uristr);
                e.printStackTrace();
            }
            String cookieListJson = sp.getString(uristr, "");
            if (cookieListJson.isEmpty())
                break;

            CookiesJson cookiesJson = null;
            try {
                cookiesJson = Jeson.createBean(CookiesJson.class, cookieListJson);
            } catch (Exception e) {
                ZhihuLog.d(TAG, cookieListJson);
                e.printStackTrace();
            }
            List<CookieJson> cookies = cookiesJson.getCookieJsonList();
            List<HttpCookie> cookieList = new ArrayList<>();
            for (CookieJson cookieJson : cookies) {
                HttpCookie cookie = new HttpCookie(cookieJson.getName(), cookieJson.getValue());
                cookie.setComment(cookieJson.getComment());
                cookie.setDomain(cookieJson.getDomain());
                cookie.setMaxAge(cookieJson.getExpiryDate());
                cookie.setPath(cookieJson.getPath());
                cookie.setVersion(cookieJson.getVersion());
                cookieList.add(cookie);
            }
            cookieMap.put(uri, cookieList);

        }
    }

    public static URLCookiesStore getInstance() {
        if (instance == null)
            instance = new URLCookiesStore();
        return instance;
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        ZhihuLog.d(TAG, "add cookie " + cookie.getValue());
        // 存储到内存中
        if (cookieMap.containsKey(uri)) {
            cookieMap.get(uri).add(cookie);
        } else {
            List<HttpCookie> cookieList = new ArrayList<>();
            cookieList.add(cookie);
            cookieMap.put(uri, cookieList);
        }

        // uri 的 string 形式
        String uristr = uri.toString();
        // 将 cookie 转化成 java bean
        CookieJson json = new CookieJson();
        List<HttpCookie> cookieList = cookieMap.get(uri);
        for (HttpCookie c : cookieList) {
            if (c.getName() == cookie.getName()){

            }

        }
        json.setName(cookie.getName());
        json.setCommentUrl(cookie.getCommentURL());
        json.setDomain(cookie.getDomain());
        json.setExpiryDate(cookie.getMaxAge());
        json.setPath(cookie.getPath());
        json.setValue(cookie.getValue());
        json.setVersion(cookie.getVersion());

        // 存储到sp中
        String cookiesJsonStr = sp.getString(uristr, "");
        if (TextUtils.isEmpty(cookiesJsonStr)) {
            // cookies 不存在
            CookiesJson cookiesJson = new CookiesJson();
            cookiesJson.setUri(uristr);
            cookiesJson.addCookie(json);
            try {
                cookiesJsonStr = Jeson.bean2String(cookiesJson);
            } catch (Exception e) {
                ZhihuLog.d(TAG, cookiesJsonStr);
                e.printStackTrace();
            }
        } else {
            // cookies 存在
            try {
                CookiesJson cookiesJson = Jeson.createBean(CookiesJson.class, cookiesJsonStr);
                cookiesJson.addCookie(json);
                cookiesJsonStr = Jeson.bean2String(cookiesJson);
            } catch (Exception e) {
                ZhihuLog.d(TAG, cookiesJsonStr);
                e.printStackTrace();
            }
        }
        // commit
        sp.edit().putString(uristr, cookiesJsonStr).commit();

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        // 由于都是知乎的uri，所以这里就不区分了，只在存储的时候区分下
        return getCookies();
    }

    @Override
    public List<HttpCookie> getCookies() {
        Collection<List<HttpCookie>> cookieCollection = cookieMap.values();
        ArrayList<HttpCookie> cookieList = new ArrayList<>();
        // 过滤过期的cookie
        for (List<HttpCookie> cookies : cookieCollection) {
            for (HttpCookie cookie : cookies) {
                if (cookie.getMaxAge() < System.currentTimeMillis())
                    cookieList.add(cookie);
            }
        }
        return cookieList;
    }

    @Override
    public List<URI> getURIs() {
        Set<URI> keySet = cookieMap.keySet();
        ArrayList<URI> uriList = new ArrayList<>();
        for (URI uri : keySet) {
            uriList.add(uri);
        }
        return uriList;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        List<HttpCookie> cookies = cookieMap.get(uri);
        for (HttpCookie mCookie : cookies) {
            if (cookie.getName() == mCookie.getName()) {
                cookies.remove(mCookie);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        // 清除持久化的cookie和当前cookie
        ZhihuLog.d(TAG, "clear all cookieMap");
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        cookieMap.clear();
        return true;
    }
}
