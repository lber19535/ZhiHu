package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.utils.XHeaders;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public interface AnswerService {
    @GET("/questions/{id}/answers")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<AnswersResponse> answers(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id") String actorsId);

    @GET("/questions/{id}/answers")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<AnswersResponse> nextPage(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id") String actorsId, @Query("offset") String offset);
}
