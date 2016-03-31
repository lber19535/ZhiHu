package com.bill.zhihu.model;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerModel {

    public Observable<SingleAnswerResponse> getAnswer(String id){
        return ZhihuApi.getAnswer(id).subscribeOn(Schedulers.io());
    }
}
