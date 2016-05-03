package com.bill.zhihu.util;

import com.bill.zhihu.api.bean.answer.AnswerItem;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.model.answer.AnswerIntentValue;

/**
 * Created by bill_lv on 2016/4/18.
 */
public class IntentUtils {

    public static AnswerIntentValue convert(FeedsItem item) {
        AnswerIntentValue value = new AnswerIntentValue();
        value.setAuthor(item.target.author);
        value.setAnswerId(item.target.id);
        value.setVoteupCount(item.target.voteupCount);
        value.setTitle(FeedsItemUtils.getTitle(item));
        return value;
    }

    public static AnswerIntentValue convert(AnswerItem item) {
        AnswerIntentValue value = new AnswerIntentValue();
        value.setAuthor(item.author);
        value.setAnswerId(item.id);
        value.setVoteupCount(item.voteupCount);
        value.setTitle(item.question.title);
        return value;
    }
}
