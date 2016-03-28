package com.bill.zhihu.model;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.FeedsResponse;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/28.
 */
public class HomeModel {

    public Observable<FeedsResponse> loadMore(int id) {
        return ZhihuApi.loadMore(id)
                .subscribeOn(Schedulers.io());
    }

    public Observable<FeedsResponse> loadHomePage() {
        return ZhihuApi.loadHomePage()
                .subscribeOn(Schedulers.io());
    }
}
