package com.bill.zhihu.api.cookie;

import android.content.Context;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.utils.ZhihuLog;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * 持久化cookies数据
 * 由于Android 6.0 开始移除了httpclient，所以这里使用net包中的cookiestore，相应的httpclient也换成了 {@code URLConnection}
 * <p/>
 * Created by Bill Lv on 2015/8/30.
 */
public class URLCookiesStore implements CookieStore {

    private static final String COOKIE_FILE_NAME = "zhihu_cookies";
    private static final String TAG = "URLCookiesStore";
    private Set<String> cookiesUris;
    private RealmConfiguration configuration;
    private Realm realm;

    private ConcurrentHashMap<URI, List<HttpCookie>> cookieMap;

    private static URLCookiesStore instance;

    private URLCookiesStore(Context mContext) {
        ZhihuLog.d(TAG, "cookie store is init");

        // realm 配置
        configuration = new RealmConfiguration.Builder(mContext).name(COOKIE_FILE_NAME).build();
        realm = Realm.getInstance(configuration);
        /*
         * key 存 URI， value 存 httpcookie 的 list
         *
         * 初始化的时候先去取到cookie的所有key，然后再根据key去取json对象然后再赋值到cookie对象中
         */
        cookieMap = new ConcurrentHashMap<>();
        cookiesUris = new HashSet<>();
        RealmResults<CookieTable> cookiesTables = realm.allObjects(CookieTable.class);
        for (CookieTable item : cookiesTables) {
            cookiesUris.add(item.getUri());
        }
        for (String uri : cookiesUris) {
            List<HttpCookie> cookieList = new ArrayList<>();
            for (CookieTable item : cookiesTables) {
                HttpCookie cookie = new HttpCookie(item.getName(), item.getValue());
                cookie.setComment(item.getComment());
                cookie.setDomain(item.getDomain());
                cookie.setMaxAge(item.getMaxAge());
                cookie.setPath(item.getPath());
                cookie.setVersion(item.getVersion());
                cookieList.add(cookie);
            }
            try {
                cookieMap.put(new URI(uri), cookieList);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        realm.close();
    }

    public static URLCookiesStore getInstance() {
        if (instance == null)
            instance = new URLCookiesStore(ZhihuApi.getContext());
        return instance;
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        ZhihuLog.d(TAG, "add cookie " + cookie.getValue());

        // 存储到内存
        if (cookieMap.containsKey(uri)) {
            cookieMap.get(uri).add(cookie);
        } else {
            List<HttpCookie> cookieList = new ArrayList<>();
            cookieList.add(cookie);
            cookiesUris.add(uri.toString());
            cookieMap.put(uri, cookieList);
        }

        // 存储到数据库
        realm = Realm.getInstance(configuration);
        // 查询是否已经存在
        CookieTable cookieItem = realm.where(CookieTable.class).equalTo("name", cookie.getName()).findFirst();

        realm.beginTransaction();
        if (cookieItem == null)
            cookieItem = realm.createObject(CookieTable.class);
        // 转化为 orm 对象
        cookieItem.setUri(uri.toString());
        cookieItem.setComment(convertToEmpty(cookie.getComment()));
        cookieItem.setCommentUrl(convertToEmpty(cookie.getCommentURL()));
        cookieItem.setDomain(convertToEmpty(cookie.getDomain()));
        cookieItem.setMaxAge(cookie.getMaxAge());
        cookieItem.setName(convertToEmpty(cookie.getName()));
        cookieItem.setValue(convertToEmpty(cookie.getValue()));
        cookieItem.setPath(convertToEmpty(cookie.getPath()));
        cookieItem.setVersion(cookie.getVersion());
        realm.commitTransaction();
        realm.close();

    }

    private String convertToEmpty(String str){
        if (str == null)
            return "";
        else
            return str;
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
                // 删除内存中的
                cookies.remove(mCookie);
                // 删除数据库中的
                realm = Realm.getInstance(configuration);
                realm.beginTransaction();
                CookieTable cookieItem = realm.where(CookieTable.class).equalTo("name", cookie.getName()).equalTo("uri", uri.toString()).findFirst();
                cookieItem.removeFromRealm();
                realm.commitTransaction();
                realm.close();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        // 清除持久化的cookie和当前cookie
        ZhihuLog.d(TAG, "clear all cookieMap");
        realm = Realm.getInstance(configuration);
        realm.beginTransaction();
        realm.allObjects(CookieTable.class).clear();
        realm.commitTransaction();
        realm.close();
        cookieMap.clear();
        cookiesUris.clear();
        return true;
    }
}
