package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.NoHelpResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.bean.response.ThankResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
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
        return service.answers( questionId);
    }

    public Observable<AnswersResponse> nextPage(String questionId, int offset) {
        return service.nextPage( questionId, String.valueOf(offset));
    }

    public Observable<SingleAnswerResponse> getAnswer(String answerId) {
        return service.answer( answerId);
    }

    /**
     * @param voteType {@link com.bill.zhihu.api.bean.common.VoteType}
     */
    public Observable<VoteResponse> vote(String answerId, int voteType){
        return service.vote(answerId,voteType);
    }

    public Observable<NoHelpResponse> nohelp(String answerId){
        return service.nohelp(answerId);
    }

    public Observable<NoHelpResponse> cancelNohelp(String answerId){
        return service.cancelNohelp(answerId, AuthStore.getUID());
    }

    public Observable<ThankResponse> thanks(String answerId){
        return service.thanks(answerId);
    }

    public Observable<ThankResponse> cancelThanks(String answerId){
        return service.cancelThankers(answerId, AuthStore.getUID());
    }
}
