package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TimeLineItem.ContentType;
import com.bill.zhihu.api.bean.TimeLineItem.SourceType;

/**
 * 解析item
 * 
 * @author Bill Lv
 *
 */
public class ItemParser {

    private static final String TAG = "ItemParser";

    public static List<TimeLineItem> getTimeLineItems(String content) {
        List<TimeLineItem> timelineItems = new ArrayList<TimeLineItem>();
        Document doc = Jsoup.parse(content);
        // 获取问题列表
        Elements feedListElements = doc
                .select("div[id=js-home-feed-list]>div[id^=feed]");
        int feedListLength = feedListElements.size();

        ZhihuLog.dValue(TAG, "feedListLength", feedListLength);
        ZhihuLog.d(TAG, "----------------");

        for (Element element : feedListElements) {

            // 是否包含feed-question-detail-item 来确定是否有是否只有问题还是也有回答
            TimeLineItem item = new TimeLineItemBuilder(element).setAvatar()
                    .setSource().setQuestion().setAnswer().build();
            timelineItems.add(item);
            ZhihuLog.d(TAG, "------------");
            ZhihuLog.dValue(TAG, "time line size is  ", timelineItems.size());
        }
        return timelineItems;
    }

    public static class TimeLineItemBuilder {
        private static final String SOURCE_FORM_TAG = "来自";

        private TimeLineItem item;
        private Element element;

        public TimeLineItemBuilder(Element element) {
            this.item = new TimeLineItem();
            this.element = element;
        }

        /**
         * 设置头像
         * 
         * @return
         */
        public TimeLineItemBuilder setAvatar() {
            // avatar 头像
            Elements avatarElements = element
                    .select("div[class=feed-item-inner]>div[class=avatar]>a");
            String avatarImgUrl = avatarElements.select("img").attr("src");
            String blockId = element.attr("data-block");
            ZhihuLog.dValue(TAG, "avatarImgUrl ", avatarImgUrl);
            ZhihuLog.dValue(TAG, "blockId ", blockId);
            item.setAvatarImgUrl(avatarImgUrl);
            item.setDataBlock(blockId);
            return this;
        }

        /**
         * 设置来源，赞同了xxx回答，关注了xx问题/专栏，来自xxx
         * 
         * @return
         */
        public TimeLineItemBuilder setSource() {
            // 来源
            Elements sourceElements = element
                    .select("div[class=feed-main]>div[class=source]");
            String source = sourceElements.select("a").attr(
                    "data-original_title");
            String time = sourceElements.select("span[class=time]").text();
            String sourcetxt = sourceElements.text();
            String sourceUrl = sourceElements.select("a").attr("href");
            String timestamp = sourceElements.select("span[class=time]").attr(
                    "data-timestamp");
            TimeLineItem.SourceType sourceType = SourceType.LEFT;
            if (sourcetxt.contains(SOURCE_FORM_TAG)) {
                sourceType = SourceType.RIGHT;
            }
            if (source.isEmpty()) {
                Elements elements = sourceElements.select("a");
                for (Element element : elements) {
                    source += element.text() + " ";
                }
                source = source.subSequence(0, source.length() - 1).toString();
            }

            ZhihuLog.dValue(TAG, "source ", source);
            ZhihuLog.dValue(TAG, "sourceUrl ", sourceUrl);
            ZhihuLog.dValue(TAG, "sourcetxt ", sourcetxt);
            ZhihuLog.dValue(TAG, "timestamp ", timestamp);
            ZhihuLog.dValue(TAG, "time ", time);

            item.setSource(source);
            item.setSourceText(sourcetxt);
            item.setSourceUrl(sourceUrl);
            item.setTime(time);
            item.setTimeStamp(timestamp);
            item.setSourceType(sourceType);

            return this;
        }

        /**
         * 问题或文章标题
         * 
         * @return
         */
        public TimeLineItemBuilder setQuestion() {
            Elements contentElements = element.select("div[class=content]");
            boolean haveAnswer = contentElements.hasClass("entry-body");

            String question = contentElements.select("h2>a").text();
            String questionUrl = contentElements.select("a").attr("href");

            ZhihuLog.dValue(TAG, "question ", question);
            ZhihuLog.dValue(TAG, "questionUrl ", questionUrl);
            item.setQuestion(question);
            item.setQuestionUrl(questionUrl);
            if (!haveAnswer) {
                item.setContentType(ContentType.QUESTION);
            } else {
                item.setContentType(ContentType.ANSWER);
            }

            return this;
        }

        /**
         * 答案摘要
         * 
         * @return
         */
        public TimeLineItemBuilder setAnswer() {
            if (item.getContentType() == ContentType.QUESTION) {
                return this;
            }
            Elements answerElements = element
                    .select("div[class=content]>div[class=entry-body]");
            String answerSummary = answerElements
                    .select("div[class=zm-item-answer-detail]>div[class=zm-item-rich-text]>div[class=zh-summary summary clearfix]")
                    .text();
            String voteCount = answerElements.select("[class=zm-item-vote]>a")
                    .text();

            ZhihuLog.dValue(TAG, "answerSummary ", answerSummary);
            ZhihuLog.dValue(TAG, "voteCount ", voteCount);

            item.setAnswerSummary(answerSummary);
            item.setVoteCount(voteCount);

            return this;
        }

        /**
         * 生成一个item
         * 
         * @return
         */
        public TimeLineItem build() {
            return item;
        }
    }

}
