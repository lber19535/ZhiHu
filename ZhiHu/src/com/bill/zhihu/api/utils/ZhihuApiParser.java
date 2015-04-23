package com.bill.zhihu.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.builder.TimeLineItemBuilder;

/**
 * 解析接口获取到的html或者xml
 * 
 * @author Bill Lv
 *
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

}
