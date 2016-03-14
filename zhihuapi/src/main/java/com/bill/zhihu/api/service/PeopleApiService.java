package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.PeopleBasicResponse;
import com.bill.zhihu.api.utils.XHeaders;
import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by bill_lv on 2016/2/17.
 */
public interface PeopleApiService {

    @GET("/people/self/basic")
    @Headers({
            XHeaders.ACCEPT_ENCODE,
            XHeaders.ZHIHU_UA,
            XHeaders.X_API_VERSION,
            XHeaders.X_APP_VERSION,
//            "x-app-za: OS=Android&Release=5.1&Model=Google+Nexus+4+-+5.1.0+-+API+22+-+768x1280&VersionName=2.4.4&VersionCode=244&Width=768&Height=1184&Installer=%E7%9F%A5%E4%B9%8E",
            "Host: api.zhihu.com",
            "Connection: Keep-Alive"
    })
    Observable<PeopleBasicResponse> selfBasic(@Header("Authorization") String auth, @Header("x-account-unlock") String unlockTicket);
}
