package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.bean.response.QuestionResponse;
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
    Observable<QuestionResponse> question(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket, @Path("id")String actorsId);


}
