package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.FeedsApiService;

import rx.Observable;

/**
 * Created by bill_lv on 2016/3/1.
 */
public class FeedsApi implements API {
    private FeedsApiService service;

    public FeedsApi(FeedsApiService service) {
        this.service = service;
    }


    public Observable<FeedsResponse> getFeeds() {
        return service.feeds();
    }

    public Observable<FeedsResponse> nextPage(int id) {
        return service.nextPage(String.valueOf(id));
    }
}
