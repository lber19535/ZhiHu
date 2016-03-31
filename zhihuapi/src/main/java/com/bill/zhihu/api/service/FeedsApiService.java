package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.FeedsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bill_lv on 2016/2/17.
 */
public interface FeedsApiService {

    @GET("/feeds")
    Observable<FeedsResponse> feeds();

    @GET("/feeds")
    Observable<FeedsResponse> nextPage(@Query("after_id") String id);
}
