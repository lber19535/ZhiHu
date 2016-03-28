package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.AnswerService;
import com.bill.zhihu.api.utils.AuthStore;

import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public class AnswerApi implements API {
    private AnswerService service;

    public AnswerApi(AnswerService service) {
        this.service = service;
    }

    public Observable<AnswersResponse> getAnswers(String questionId) {
        return service.answers(AuthStore.getAuthorization(), AuthStore.getUnlockTicket(), questionId);
    }

    public Observable<AnswersResponse> nextPage(String questionId, int offset) {
        return service.nextPage(AuthStore.getAuthorization(), AuthStore.getUnlockTicket(), questionId, String.valueOf(offset));
    }
}
