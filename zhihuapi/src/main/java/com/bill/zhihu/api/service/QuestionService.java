package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.FeedsResponse;
import com.bill.zhihu.api.utils.XHeaders;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public interface QuestionService {

    @GET("/questions/{id}")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<FeedsResponse> question(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id")String actorsId);

    @GET("/questions/{id}/answers")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<FeedsResponse> answers(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id")String actorsId);
}
