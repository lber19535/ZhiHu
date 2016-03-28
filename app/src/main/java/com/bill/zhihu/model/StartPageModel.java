package com.bill.zhihu.model;

import android.content.Context;

import com.bill.zhihu.api.ZhihuApi;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class StartPageModel {
    private Context context;

    public StartPageModel(Context context) {
        this.context = context;
    }

    /**
     * Get login status
     *
     * @return
     */
    public Observable<Boolean> initStartPage() {
        return ZhihuApi.haveLogin().subscribeOn(Schedulers.io());
    }
}
