package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.FeedsApiService;
import com.bill.zhihu.api.utils.AuthStore;
import com.bill.zhihu.api.utils.TextUtils;

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
        return service.feeds(AuthStore.getAuthorization(), AuthStore.getUnlockTicket());
    }

    public Observable<FeedsResponse> nextPage(int id) {
        return service.nextPage(AuthStore.getAuthorization(), AuthStore.getUnlockTicket(), String.valueOf(id));
    }
}
