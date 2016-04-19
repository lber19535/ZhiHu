package com.bill.zhihu.model.answer;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichCcontentUtils;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerModel {
    private static final String TAG = "AnswerModel";

    public Observable<String> getAnswer(String id) {
        return ZhihuApi.getAnswer(id)
                .map(new Func1<SingleAnswerResponse, String>() {
                    @Override
                    public String call(SingleAnswerResponse singleAnswerResponse) {

                        String content = RichCcontentUtils.wrapContent(
                                singleAnswerResponse.content + RichCcontentUtils.createTimeTag(singleAnswerResponse.createdTime, singleAnswerResponse.updatedTime)
                                , Theme.LIGHT);

                        return RichCcontentUtils.replaceImage(content);
                    }
                }).subscribeOn(Schedulers.io());
    }


}
