package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.PeopleBasicResponse;
import com.bill.zhihu.api.utils.XHeaders;
import okhttp3.ResponseBody;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by bill_lv on 2016/2/17.
 */
public interface PeopleApiService {

    @GET("/people/self/basic")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<PeopleBasicResponse> selfBasic(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket);

    @GET("/people/{id}/following_topics")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<ResponseBody> followTopic(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id") String id);

    @GET("/people/{id}/followees")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<ResponseBody> followees(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id") String id);
}
