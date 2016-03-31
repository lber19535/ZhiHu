package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.QuestionResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public interface QuestionService {

    @GET("/questions/{id}")
    Observable<QuestionResponse> question( @Path("id")String actorsId);


}
