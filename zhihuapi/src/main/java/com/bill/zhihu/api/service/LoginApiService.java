package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 * 登陆 api service
 *
 * Created by bill_lv on 2016/2/2.
 */
public interface LoginApiService{

    @POST("/sign_in")
    Observable<LoginResponse> signIn(@Body String fields);

    @GET("/captcha")
    Observable<ResponseBody> captcha();

}
