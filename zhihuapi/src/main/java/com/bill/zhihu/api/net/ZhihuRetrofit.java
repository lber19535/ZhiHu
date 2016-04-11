package com.bill.zhihu.api.net;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cookie.ZhihuCookieJar;
import com.bill.zhihu.api.utils.AuthStore;
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
        // set cache
        File cacheDir = ZhihuApi.getContext().getCacheDir();
        File cache = new File(cacheDir, "http_cache");

        OkHttpClient client = new OkHttpClient.Builder()
                // 持久化 cookie
                .cookieJar(new ZhihuCookieJar())
                .cache(new Cache(cache, HTTP_CACHE_SIZE))
                .addInterceptor(new ZHInterceptor())
                .build();

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

    class ZHInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            String url = chain.request().url().toString();
            Request.Builder builder = chain.request().newBuilder();

            if (url.equals("https://api.zhihu.com/sign_in")) {
                builder.header(XHeaders.GOOGLEUA_KEY, XHeaders.GOOGLEUA_VALUE);
                builder.header(XHeaders.CONTENT_KEY, XHeaders.CONTENT_VALUE);
            } else {
                if (url.equals("https://api.zhihu.com/captcha")) {
                    builder.header(XHeaders.AUTHORIZATION_KEY, XHeaders.AUTHORIZATION_VALUE);
                }else {
                    builder.header(XHeaders.AUTHORIZATION_KEY, AuthStore.getAuthorization());
                    builder.header(XHeaders.ACCOUNT_UNLOCK_KEY, AuthStore.getUnlockTicket());
                }

                builder.header(XHeaders.ZHUA_KEY, XHeaders.ZHUA_VALUE);
                builder.header(XHeaders.API_VERSION_KEY, XHeaders.API_VERSION_VALUE);
                builder.header(XHeaders.APP_VERSION_KEY, XHeaders.APP_VERSION_VALUE);
                builder.header(XHeaders.APP_ZA_KEY, XHeaders.getZA());

            }

            builder.header(XHeaders.HOST_KEY, XHeaders.HOST_VALUE);
            builder.header(XHeaders.CONNECTION_KEY, XHeaders.CONNECTION_VALUE);

            return chain.proceed(builder.build());
        }
    }
}
