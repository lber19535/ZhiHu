package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.FeedsResponse;
import com.bill.zhihu.api.utils.XHeaders;
import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by bill_lv on 2016/2/17.
 */
public interface FeedsApiService {

    @GET("/feeds")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<FeedsResponse> feeds(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket);

    @GET("/feeds")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<FeedsResponse> nextPage(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Query("after_id") String id);
}
