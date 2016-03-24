package com.bill.zhihu.api.net;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cookie.ZhihuCookieJar;
import com.bill.zhihu.api.utils.XHeaders;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by bill_lv on 2016/2/16.
 */
public class ZhihuRetrofit {

    private static Retrofit retrofit;

    private static final int HTTP_CACHE_SIZE = 50 * 1024 * 1024;

    private ZhihuRetrofit() {

        // 持久化 cookie
//        PersistentCookiesStore cookiesStore = PersistentCookiesStore.getInstance();
//        CookieManager manager = new CookieManager(cookiesStore, CookiePolicy.ACCEPT_ALL);

        // set cache
        File cacheDir = ZhihuApi.getContext().getCacheDir();
        File cache = new File(cacheDir, "http_cache");

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new ZhihuCookieJar())
                .cache(new Cache(cache, HTTP_CACHE_SIZE))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().header(XHeaders.X_APP_ZA.split(":")[0], XHeaders.X_APP_ZA.split(":")[1].trim()).build();
                        return chain.proceed(req);
                    }
                })
                .build();
//        client.setCookieHandler(manager);
//        client.setCache(new Cache(cache, HTTP_CACHE_SIZE));
//        client.


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
