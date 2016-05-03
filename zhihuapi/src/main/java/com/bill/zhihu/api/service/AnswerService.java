package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.NoHelpResponse;
import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.bean.response.ThankResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<AnswersResponse> answers(@Path("id") String questionId);

    /**
     * single answer
     */
    @GET("/answers/{id}")
    Observable<SingleAnswerResponse> answer(@Path("id") String questionId);

    @GET("/questions/{id}/answers")
    Observable<AnswersResponse> nextPage(@Path("id") String questionId, @Query("offset") String offset);

    @POST("/answers/{id}/nothelpers")
    Observable<NoHelpResponse> nohelp(@Path("id") String answersId);

    @DELETE("/answers/{id}/nothelpers/{uid}")
    Observable<NoHelpResponse> cancelNohelp(@Path("id") String answersId, @Path("uid") String uid);

    @POST("/answers/{id}/thankers")
    Observable<ThankResponse> thanks(@Path("id") String answersId);

    @DELETE("/answers/{id}/thankers/{uid}")
    Observable<ThankResponse> cancelThankers(@Path("id") String answersId, @Path("uid") String uid);

    @FormUrlEncoded
    @POST("/answers/{id}/voters")
    Observable<VoteResponse> vote(@Path("id") String answersId, @Field("voting") int voteType);


}
