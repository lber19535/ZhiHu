package com.bill.zhihu.util;

import com.bill.zhihu.R;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.feeds.FeedsItemVerb;
import com.bill.zhihu.api.utils.TextUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class FeedsItemUtils {
    private static final String TAG = "FeedsItemUtils";

    /**
     * Get the title from {@link FeedsItem}, it might be a question title or article title or column title
     *
     * @param item
     * @return
     */
    public static String getTitle(FeedsItem item) {
        switch (item.verb) {

            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
                return item.target.question.title;

            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
                return item.target.title;

            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
                return item.target.title;

            case FeedsItemVerb.PROMOTION_ANSWER:
                return item.target.question.title;

            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
                return item.target.question.title;

            case FeedsItemVerb.VOTE_UP_ANSWER:
                return item.target.question.title;

            case FeedsItemVerb.VOTE_UP_ARTICLE:
                return item.target.title;

            case FeedsItemVerb.MEMBER_ASK_QUESTION:
                return item.target.title;

            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                return item.target.title;

            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
                return item.target.title;

            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
                return item.target.title;

            case FeedsItemVerb.COLUMN_NEW_ARTICLE:
                return item.target.column.title;

            default:
                Logger.t(TAG).d("Time line adapter, none of specific verb ", item.verb);
                return null;
        }
    }

    /**
     * Get small avatar image url
     *
     * @param item
     * @return
     */
    public static String getAvatarUrl(FeedsItem item) {
        switch (item.verb) {

            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
                return item.target.author.avatarUrl;

            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.PROMOTION_ANSWER:
                return item.target.author.avatarUrl;
            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.VOTE_UP_ANSWER:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.VOTE_UP_ARTICLE:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.MEMBER_ASK_QUESTION:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
                return item.actors.get(0).avatarUrl;

            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
                return item.target.author.avatarUrl;

            case FeedsItemVerb.COLUMN_NEW_ARTICLE:
                return item.target.column.imageUrl;

            default:
                Logger.t(TAG).d("Time line adapter, none of specific verb ", item.verb);
                return null;
        }
    }

    /**
     * Format the source text and color it
     *
     * @param item
     * @return
     */
    public static CharSequence formatSourceString(FeedsItem item) {
        int highLightTextColor = ZhihuApp.getRes().getColor(R.color.blue_light);

        String sourceText;
        String highLightText;
        switch (item.verb) {
            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
                highLightText = item.target.column.title;
                sourceText = highLightText + " " + "中很多人赞同了该文章";
                break;
            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "回答了该问题";
                break;
            case FeedsItemVerb.MEMBER_ASK_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "提了一个问题";
                break;
            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "发布了文章";
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "关注了专栏";
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "关注了该问题";
                break;
            case FeedsItemVerb.PROMOTION_ANSWER:
                highLightText = "";
                sourceText = "热门回答";
                break;
            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
                highLightText = item.actors.get(0).name;
                sourceText = "来自" + " " + highLightText;
                break;
            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = "来自" + " " + highLightText;
                break;
            case FeedsItemVerb.VOTE_UP_ANSWER:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "赞同了该回答";
                break;
            case FeedsItemVerb.VOTE_UP_ARTICLE:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "赞同了该文章";
                break;
            case FeedsItemVerb.COLUMN_NEW_ARTICLE:
                highLightText = item.target.column.title;
                sourceText = highLightText + " " + "中发表聊发表了新文章";
                break;
            default:
                sourceText = "";
                highLightText = "";
                break;
        }

        Logger.t(TAG).d("source text", sourceText);
        Logger.t(TAG).d("high Light Text", highLightText);

        return TextUtils.getColorString(sourceText, highLightTextColor, highLightText);
    }
}
