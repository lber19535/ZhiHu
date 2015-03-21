package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bill.zhihu.api.bean.TimeLineItem;

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
            // avatar
            Elements avatarElements = element
                    .select("div[class=feed-item-inner]>div[class=avatar]>a");
            String avatarName = avatarElements.attr("title");
            String avatarImgUrl = avatarElements.select("img").attr("src");
            String avatarHome = avatarElements.attr("href");
            String blockId = element.attr("data-block");
            ZhihuLog.dValue(TAG, "avatarName ", avatarName);
            ZhihuLog.dValue(TAG, "avatarImgUrl ", avatarImgUrl);
            ZhihuLog.dValue(TAG, "avatarHome ", avatarHome);

            // 来源
            Elements sourceElements = element
                    .select("div[class=feed-main]>div[class=source]");
            String source = sourceElements.select("a").text();
            String sourceUrl = sourceElements.select("a").attr("href");
            String rawTime = sourceElements.select("span[class=time]").attr(
                    "data-timestamp");
            String timeTips = sourceElements.select("span[class=time]").text();
            // 首页中 来自话题的item是没有时间的
            boolean isTopic = sourceElements.select("a")
                    .hasAttr("data-topicid");
            String typeString = "";
            if (sourceElements.select("a").isEmpty()) {
                typeString = sourceElements.text();
            } else {
                typeString = sourceElements.get(0).textNodes().get(1).text();
            }
            ZhihuLog.dValue(TAG, "source ", source);
            ZhihuLog.dValue(TAG, "sourceUrl ", sourceUrl);
            ZhihuLog.dValue(TAG, "rawTime ", rawTime);
            ZhihuLog.dValue(TAG, "timeTips ", timeTips);
            ZhihuLog.dValue(TAG, "typeString ", typeString);
            ZhihuLog.dValue(TAG, "isTopic ", isTopic);

            // 内容
            Elements contentElements = element.select("div[class=content]");
            String question = contentElements.select("a[class=question_link]")
                    .text();
            String questionUrl = contentElements.select(
                    "a[class=question_link]").attr("href");
            String voteCount = contentElements.select("[class=zm-item-vote]>a")
                    .text();
            ZhihuLog.dValue(TAG, "question ", question);
            ZhihuLog.dValue(TAG, "questionUrl ", questionUrl);
            ZhihuLog.dValue(TAG, "voteCount ", voteCount);

            // 是否包含feed-question-detail-item 来确定是否有是否只有问题还是也有回答
            boolean onlyQuestion = !contentElements.select("div").hasClass(
                    "feed-question-detail-item");
            ZhihuLog.dValue(TAG, "onlyQuestion ", onlyQuestion);
            String answer = contentElements.select(
                    "div[class=zh-summary summary clearfix]").text();

            TimeLineItem item = new TimeLineItem();
            item.setAvatarHome(avatarHome);
            item.setAvatarImgUrl(avatarImgUrl);
            item.setAvatarName(avatarName);
            item.setSource(source);
            item.setSourceUrl(sourceUrl);
            item.setTimeStamp(rawTime);
            item.setTimeTips(timeTips);
            item.setTopic(isTopic);
            item.setTypeString(typeString);
            item.setVoteCount(voteCount);
            item.setQuestion(question);
            item.setQuestionUrl(questionUrl);
            item.setOnlyQuestion(onlyQuestion);
            item.setAnswerSummary(answer);
            item.setDataBlock(Long.valueOf(blockId));

            timelineItems.add(item);
            ZhihuLog.d(TAG, "------------");
            ZhihuLog.dValue(TAG, "time line size is  ", timelineItems.size());
        }
        return timelineItems;

    }

}
