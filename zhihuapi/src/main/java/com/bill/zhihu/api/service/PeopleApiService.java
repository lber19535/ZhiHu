package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.PeopleBasicResponse;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bill_lv on 2016/2/17.
 */
public interface PeopleApiService {

    @GET("/people/self/basic")
    Observable<PeopleBasicResponse> selfBasic();

    @GET("/people/{id}/following_topics")
    Observable<ResponseBody> followTopic(@Path("id") String uid);

    @GET("/people/{id}/followees")
    Observable<ResponseBody> followees(@Path("id") String uid);
}
