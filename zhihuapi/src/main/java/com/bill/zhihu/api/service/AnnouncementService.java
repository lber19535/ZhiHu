package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.AnnouncementResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by bill_lv on 2016/3/29.
 */
public interface AnnouncementService {

    @GET("/announcement")
    Observable<AnnouncementResponse> announcement();
}
