package com.bill.zhihu.model.question;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.QuestionResponse;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/4/15.
 */
public class QuestionModel {

    public Observable<QuestionResponse> loadQuestion(String questionId) {
        return ZhihuApi.getQuestion(questionId).subscribeOn(Schedulers.io());
    }

    public Observable<AnswersResponse> loadAnswers(String questionId) {
        return ZhihuApi.getAnswers(questionId).subscribeOn(Schedulers.io());
    }

    public Observable<AnswersResponse> loadAnswersNext(String questionId, int offset) {
        return ZhihuApi.getNextAnswers(questionId, offset).subscribeOn(Schedulers.io());
    }
}
