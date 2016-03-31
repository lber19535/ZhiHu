package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.AnswerService;

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
        return service.answers( questionId);
    }

    public Observable<AnswersResponse> nextPage(String questionId, int offset) {
        return service.nextPage( questionId, String.valueOf(offset));
    }

    public Observable<SingleAnswerResponse> getAnswer(String answerId) {
        return service.answer( answerId);
    }
}
