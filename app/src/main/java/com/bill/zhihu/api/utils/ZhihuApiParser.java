package com.bill.zhihu.api.utils;

import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.factory.AnswerContentFactory;
import com.bill.zhihu.api.factory.TimeLineItemFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析接口获取到的html或者xml
 *
 * @author Bill Lv
 */
public class ZhihuApiParser {

    private static final String TAG = "ZhihuApiParser";

    public static List<TimeLineItem> parseTimeLineItems(String content) {
        List<TimeLineItem> timelineItems = new ArrayList<TimeLineItem>();
        Document doc = Jsoup.parse(content);
        // 获取问题列表
        Elements feedListElements = doc
                .select("div[id=js-home-feed-list]>div[id^=feed]");
        int feedListLength = feedListElements.size();

        ZhihuLog.dValue(TAG, "feedListLength", feedListLength);
        ZhihuLog.d(TAG, "----------------");
        ZhihuLog.d(TAG, "parsing");

        for (Element element : feedListElements) {
            // 是否包含feed-question-detail-item 来确定是否有是否只有问题还是也有回答
            TimeLineItem item = parseTimeLineItem(element);
            timelineItems.add(item);
        }

        ZhihuLog.d(TAG, "parsing end");
        return timelineItems;
    }

    /**
     * 每一项html
     *
     * @param htmlItems
     * @return
     */
    public static List<TimeLineItem> parseTimeLineItems(List<String> htmlItems) {
        List<TimeLineItem> items = new ArrayList<TimeLineItem>();
        for (String html : htmlItems) {
            items.add(parseTimeLineItem(html));
        }
        return items;
    }

    /**
     * 加载更多等接口返回的json中每一个item都是一个html，所以会用到直接把html转成item的
     *
     * @param html
     * @return
     */
    private static TimeLineItem parseTimeLineItem(String html) {
        Elements eles = Jsoup.parse(html).select("div[class^=feed-item]");
        Element element = eles.get(0);
        return parseTimeLineItem(element);
    }

    private static TimeLineItem parseTimeLineItem(Element element) {
        return new TimeLineItemFactory(element).create();
    }

    public static AnswerContent parseAnswerContent(String html){
        Document doc = Jsoup.parse(html);
        Elements answerElements = doc.select("div[id=zh-question-answer-wrap]>div[tabindex=-1]");
        return new AnswerContentFactory(answerElements).create();
    }

}
