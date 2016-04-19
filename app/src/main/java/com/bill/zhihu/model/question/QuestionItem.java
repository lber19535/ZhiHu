package com.bill.zhihu.model.question;

import com.bill.zhihu.api.bean.answer.AnswerItem;
import com.bill.zhihu.api.bean.response.QuestionResponse;

/**
 * Created by bill_lv on 2016/4/18.
 */
public class QuestionItem {

    private QuestionResponse questionResponse;
    private AnswerItem answerItem;

    public QuestionResponse getQuestionResponse() {
        return questionResponse;
    }

    public void setQuestionResponse(QuestionResponse questionResponse) {
        this.questionResponse = questionResponse;
    }

    public AnswerItem getAnswerItem() {
        return answerItem;
    }

    public void setAnswerItem(AnswerItem answerItem) {
        this.answerItem = answerItem;
    }
}
