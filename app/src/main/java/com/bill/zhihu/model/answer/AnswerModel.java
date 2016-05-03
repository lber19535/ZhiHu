package com.bill.zhihu.model.answer;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.answer.AnswerRelationship;
import com.bill.zhihu.api.bean.response.NoHelpResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.bean.response.ThankResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichContentUtils;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerModel {
    private static final String TAG = "AnswerModel";
    private SingleAnswerResponse response;

    public Observable<String> getAnswer(String id) {
        return ZhihuApi.getAnswer(id)
                .map(new Func1<SingleAnswerResponse, String>() {
                    @Override
                    public String call(SingleAnswerResponse singleAnswerResponse) {
                        response = singleAnswerResponse;
                        String content = RichContentUtils.wrapContent(
                                singleAnswerResponse.content + RichContentUtils.createTimeTag(singleAnswerResponse.createdTime, singleAnswerResponse.updatedTime)
                                , Theme.LIGHT);

                        return RichContentUtils.replaceImage(content);
                    }
                }).subscribeOn(Schedulers.io());
    }

    public AnswerRelationship getAnswerRelationship() {
        return response.relationship;
    }

    public Observable<VoteResponse> vote(String answerId, int voteType) {
        return ZhihuApi.vote(answerId, voteType).subscribeOn(Schedulers.io());
    }

    public Observable<NoHelpResponse> nohelp(String answerId){
        return ZhihuApi.nohelp(answerId);
    }

    public Observable<NoHelpResponse> cancelNohelp(String answerId){
        return ZhihuApi.cancelNohelp(answerId);
    }

    public Observable<ThankResponse> thanks(String answerId){
        return ZhihuApi.thanks(answerId);
    }

    public Observable<ThankResponse> cancelThanks(String answerId){
        return ZhihuApi.cancelThanks(answerId);
    }

}
