package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TimeLineItem.ContentType;

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
            // source 下每个tag a会代表一个source
            Elements elements = sourceElements.select("a");
            // source有时候会有多个
            for (Element element : elements) {
                String source = element.text();
                String sourceUrl = element.attr("href");
                // 当没有关注这个话题的时候网页上会出现一个隐藏的关注话题，当鼠标放过去才会显示出来
                if (source.equals("关注话题")) {
                    continue;
                }
                item.addSource(source);
                item.addSourceUrl(sourceUrl);
            }
            String time = sourceElements.select("span[class=time]").text();
            // 只要保留 来自/赞同了等关键字
            String sourcetxt = sourceElements.text().replace("•", "")
                    .replace("关注话题", "").replace(time, "");
            String timestamp = sourceElements.select("span[class=time]").attr(
                    "data-timestamp");

            ZhihuLog.dValue(TAG, "source ",
                    Arrays.toString(item.getSource().toArray()));
            ZhihuLog.dValue(TAG, "sourceUrl ",
                    Arrays.toString(item.getSourceUrls().toArray()));
            ZhihuLog.dValue(TAG, "sourcetxt ", sourcetxt);
            ZhihuLog.dValue(TAG, "timestamp ", timestamp);
            ZhihuLog.dValue(TAG, "time ", time);

            item.setSourceText(sourcetxt);
            item.setTime(time);
            item.setTimeStamp(timestamp);
            // item.setSourceType(sourceType);

            return this;
        }

        /**
         * 问题或文章标题
         * 
         * @return
         */
        public TimeLineItemBuilder setQuestion() {
            Elements contentElements = element.select("div[class=content]");
            boolean haveAnswer = contentElements.select("div").hasClass(
                    "entry-body");

            String question = contentElements.select("h2>a").text();
            String questionUrl = contentElements.select("a").attr("href");

            ZhihuLog.dValue(TAG, "question ", question);
            ZhihuLog.dValue(TAG, "questionUrl ", questionUrl);
            ZhihuLog.dValue(TAG, "haveAnswer ", haveAnswer);
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
            Elements answerElements = element.select("div[class=content]");
            String answerSummary = answerElements.select(
                    "div[class=zh-summary summary clearfix]").text();
            String voteCount = answerElements.select(
                    "[class^=zm-item-vote-info]").attr("data-votecount");

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
