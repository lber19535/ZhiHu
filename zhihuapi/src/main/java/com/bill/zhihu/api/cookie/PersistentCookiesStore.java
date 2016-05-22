package com.bill.zhihu.api.cookie;

import android.content.Context;

import com.bill.zhihu.api.ZhihuApi;
import com.orhanobut.logger.Logger;

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
import okhttp3.Cookie;

/**
 * 持久化cookies数据
 * <p/>
 * Created by Bill Lv on 2015/8/30.
 */
public class PersistentCookiesStore {

    private static final String COOKIE_FILE_NAME = "zhihu_cookies";
    private static final String TAG = "PersistentCookiesStore";
    private Set<String> cookiesUris;
    private RealmConfiguration configuration;
    private Realm realm;

    private ConcurrentHashMap<URI, List<Cookie>> cookieMap;

    private static class CookieHolder {
        public static final PersistentCookiesStore INSTANCE  = new PersistentCookiesStore(ZhihuApi.getContext());
    }

    private PersistentCookiesStore(Context mContext) {
        Logger.t(TAG).d("cookie store is init");
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
            List<Cookie> cookieList = new ArrayList<>();
            for (CookieTable item : cookiesTables) {
                Cookie cookie = new Cookie.Builder()
                        .value(item.getValue())
                        .name(item.getName())
                        .domain(item.getDomain())
                        .expiresAt(item.getMaxAge())
                        .path(item.getPath())
                        .build();

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

    public static PersistentCookiesStore getInstance() {
        return CookieHolder.INSTANCE ;
    }

    public void add(URI uri, Cookie cookie) {
        Logger.t(TAG).d("add cookie " + cookie.value());

        // 存储到内存
        if (cookieMap.containsKey(uri)) {
            cookieMap.get(uri).add(cookie);
        } else {
            List<Cookie> cookieList = new ArrayList<>();
            cookieList.add(cookie);
            cookiesUris.add(uri.toString());
            cookieMap.put(uri, cookieList);
        }

        // 存储到数据库
        realm = Realm.getInstance(configuration);
        // 查询是否已经存在
        CookieTable cookieItem = realm.where(CookieTable.class).equalTo("name", cookie.name()).findFirst();

        realm.beginTransaction();
        if (cookieItem == null)
            cookieItem = realm.createObject(CookieTable.class);
        // 转化为 orm 对象
        cookieItem.setUri(uri.toString());
        cookieItem.setDomain(convertToEmpty(cookie.domain()));
        cookieItem.setMaxAge(cookie.expiresAt());
        cookieItem.setName(convertToEmpty(cookie.name()));
        cookieItem.setValue(convertToEmpty(cookie.value()));
        cookieItem.setPath(convertToEmpty(cookie.path()));
        realm.commitTransaction();
        realm.close();

    }

    private String convertToEmpty(String str) {
        if (str == null)
            return "";
        else
            return str;
    }

    public List<Cookie> get(URI uri) {
        // 由于都是知乎的uri，所以这里就不区分了，只在存储的时候区分下
        return getCookies();
    }

    public List<Cookie> getCookies() {
        Collection<List<Cookie>> cookieCollection = cookieMap.values();
        ArrayList<Cookie> cookieList = new ArrayList<>();

        // 过滤过期的cookie
        for (List<Cookie> cookies : cookieCollection) {
            for (Cookie cookie : cookies) {
                if (cookie.expiresAt() > System.currentTimeMillis())
                    cookieList.add(cookie);
            }
        }
        Logger.t(TAG).d("get cookie size " + cookieList.size());

        return cookieList;
    }

    public List<URI> getURIs() {
        Set<URI> keySet = cookieMap.keySet();
        ArrayList<URI> uriList = new ArrayList<>();
        for (URI uri : keySet) {
            uriList.add(uri);
        }
        return uriList;
    }

    public boolean remove(URI uri, Cookie cookie) {
        List<Cookie> cookies = cookieMap.get(uri);
        for (Cookie mCookie : cookies) {
            if (cookie.name() == mCookie.name()) {
                // 删除内存中的
                cookies.remove(mCookie);
                // 删除数据库中的
                realm = Realm.getInstance(configuration);
                realm.beginTransaction();
                CookieTable cookieItem = realm.where(CookieTable.class).equalTo("name", cookie.name()).equalTo("uri", uri.toString()).findFirst();
                cookieItem.removeFromRealm();
                realm.commitTransaction();
                realm.close();
                return true;
            }
        }
        return false;
    }

    public boolean removeAll() {
        // 清除持久化的cookie和当前cookie
        Logger.t(TAG).d("clear all cookieMap");
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
