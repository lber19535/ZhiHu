package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.FeedsResponse;
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
        return service.feeds(TextUtils.upperHead(AuthStore.getTokenType()) + " " + AuthStore.getAccessToken(), AuthStore.getUnlockTicket());
    }

    public Observable<FeedsResponse> getFeedsById(String id) {
        return service.nextPage(TextUtils.upperHead(AuthStore.getTokenType()) + " " + AuthStore.getAccessToken(), AuthStore.getUnlockTicket(), id);
    }
}
