package com.bill.zhihu.api.bean;

/**
 * Item type
 *
 * Some type in zhihu app 2.4.4 have not display
 *
 * Created by bill_lv on 2016/3/8.
 */
public class FeedsItemVerb {

    // vote up answer
    public static final String VOTE_UP_ANSWER = "MEMBER_VOTEUP_ANSWER";
    // vote up article
    public static final String VOTE_UP_ARTICLE = "MEMBER_VOTEUP_ARTICLE";
    // post a column article
    public static final String MEMBER_CREATE_ARTICLE = "MEMBER_CREATE_ARTICLE";
    // who answer the question
    public static final String MEMBER_ANSWER_QUESTION = "MEMBER_ANSWER_QUESTION";
    // follow people from roundtable, this verb's type just ignore, if new version of official zhihu app had new display form, I will add it also.
    @Deprecated
    public static final String ANSWER_QUESTION_ROUNDTABLE = "MEMBER_FOLLOW_ROUNDTABLE";
    // follow the column
    public static final String MEMBER_FOLLOW_COLUMN = "MEMBER_FOLLOW_COLUMN";
    // follow the question
    public static final String MEMBER_FOLLOW_QUESTION = "MEMBER_FOLLOW_QUESTION";
    // from which topic
    public static final String TOPIC_ACKNOWLEDGED_ANSWER = "TOPIC_ACKNOWLEDGED_ANSWER";
    // who ask the question
    public static final String MEMBER_ASK_QUESTION = "MEMBER_ASK_QUESTION";
    // the popular question from popular topic
    public static final String TOPIC_POPULAR_QUESTION = "TOPIC_POPULAR_QUESTION";
    // popular article
    public static final String COLUMN_POPULAR_ARTICLE = "COLUMN_POPULAR_ARTICLE";
    // promotion answer
    public static final String PROMOTION_ANSWER = "PROMOTION_ANSWER";

}
