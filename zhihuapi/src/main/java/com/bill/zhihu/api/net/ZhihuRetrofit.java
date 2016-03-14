package com.bill.zhihu.api.net;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cookie.URLCookiesStore;
import com.bill.zhihu.api.utils.XHeaders;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by bill_lv on 2016/2/16.
 */
public class ZhihuRetrofit {

    private static Retrofit retrofit;

    private static final int HTTP_CACHE_SIZE = 50 * 1024 * 1024;

    private ZhihuRetrofit() {

        // 持久化 cookie
        URLCookiesStore cookiesStore = URLCookiesStore.getInstance();
        CookieManager manager = new CookieManager(cookiesStore, CookiePolicy.ACCEPT_ALL);

        // set cache
        File cacheDir = ZhihuApi.getContext().getCacheDir();
        File cache = new File(cacheDir, "http_cache");

        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(manager);
        client.setCache(new Cache(cache, HTTP_CACHE_SIZE));
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request req = chain.request().newBuilder().header(XHeaders.X_APP_ZA.split(":")[0], XHeaders.X_APP_ZA.split(":")[1].trim()).build();
                return chain.proceed(req);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.zhihu.com")
                .client(client)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit retrofit() {
        if (retrofit == null) {
            new ZhihuRetrofit();
        }
        return retrofit;
    }
}
