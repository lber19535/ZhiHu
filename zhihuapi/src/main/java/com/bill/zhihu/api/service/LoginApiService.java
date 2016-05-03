package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.GetCaptchaResponse;
import com.bill.zhihu.api.bean.response.LoginResponse;
import com.bill.zhihu.api.bean.response.PostCaptchaResponse;
import com.bill.zhihu.api.bean.response.ShowCaptchaResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 *
 * 登陆 api service
 *
 * Created by bill_lv on 2016/2/2.
 */
public interface LoginApiService{

    /**
     * 在 signin 之前需要确认需不需要 captcha，如果需要则要确认captcha对不对再提交用户名和密码
     *
     * @param fields
     * @return
     */
    @POST("/sign_in")
    Observable<LoginResponse> signIn(@Body String fields);

    @GET("/captcha")
    Observable<ShowCaptchaResponse> showCaptcha();

    /**
     *
     * @param fields input_text=8888
     *
     * @return
     */
    @POST("/captcha")
    Observable<PostCaptchaResponse> postCaptcha(@Body String fields);

    @PUT("/captcha")
    Observable<GetCaptchaResponse> getCaptcha();

}
