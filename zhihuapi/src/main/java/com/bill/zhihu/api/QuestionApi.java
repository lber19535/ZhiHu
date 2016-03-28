package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.QuestionResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.QuestionService;
import com.bill.zhihu.api.utils.AuthStore;

import rx.Observable;

/**
 * Created by bill_lv on 2016/3/24.
 */
public class QuestionApi implements API {

    private QuestionService service;

    public QuestionApi(QuestionService service) {
        this.service = service;
    }

    public Observable<QuestionResponse> getQuestion(String questionId){
        return service.question(AuthStore.getAuthorization(),AuthStore.getUnlockTicket(),questionId);
    }
}
