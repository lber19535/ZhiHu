package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public interface AnswerService {
    /**
     * answer list in quesiton
     */
    @GET("/questions/{id}/answers")
    Observable<AnswersResponse> answers(@Path("id") String actorsId);

    /**
     * single answer
     */
    @GET("/answers/{id}")
    Observable<SingleAnswerResponse> answer(@Path("id") String actorsId);

    @GET("/questions/{id}/answers")
    Observable<AnswersResponse> nextPage(@Path("id") String actorsId, @Query("offset") String offset);
}
