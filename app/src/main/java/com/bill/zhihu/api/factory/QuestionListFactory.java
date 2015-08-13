package com.bill.zhihu.api.factory;

import com.bill.zhihu.api.bean.AnswerItemInQuestion;
import com.bill.zhihu.api.bean.QuestionContent;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析问题列表
 * <p/>
 * Created by Bill Lv on 2015/8/12.
 */
public class QuestionListFactory {

    private static final String TAG = "QuestionListFactory";

    private static final int SUMMARY_SIZE = 80;

    private Element element;

    /**
     * 问题页面的root element
     *
     * @param element
     */
    public QuestionListFactory(Element element) {
        this.element = element;
    }

    /**
     *
     */
    public QuestionContent create() {
        QuestionContent content = new QuestionContent();

        // question id 用于关注
        String questionId = element.select("div[id=zh-question-detail]").attr("data-resourceid");
        // content
        content.setQuestionId(questionId);
        ZhihuLog.dValue(TAG, "questionId", questionId);

        // 问题详细描述
        Element questElement = element.select("div[class=zm-editable-content]").first();
        if (!element.select("div[class=zm-editable-content]").first().text().isEmpty()) {
            String richTextQuestionHtml = element.select("div[class=zm-editable-content]").first().outerHtml();
            // content
            content.setQuestionDetail(richTextQuestionHtml);
            // 对问题描述加入继承css
            String newClass = questElement.attr("class") + " zhi";
            questElement.removeClass("zm-editable-content");
            questElement.addClass(newClass);
        }

        // 话题
        Elements topicElements = element.select("a[class=zm-item-tag]");
        for (Element topicElement : topicElements) {
            String topicUrl = topicElement.attr("href");
            String topic = topicElement.text();
            // content
            content.addTopic(topic, ZhihuURL.HOST +  topicUrl);
            ZhihuLog.dValue(TAG, "topicUrl ", topicUrl);
            ZhihuLog.dValue(TAG, "topic ", topic);
        }

        // 各个回答
        Elements answerElements = element.select("div[class=zm-item-answer]");
        for (Element e : answerElements) {
            AnswerItemInQuestion item = new AnswerItemInQuestion();

            // 答主姓名
            String name = e.select("a[href^=/people]").text();
            item.setPeopleName(name);
            // 答主签名
            String bio = e.select("strong[class=zu-question-my-bio]").text();
            item.setPeopleBio(bio);
            // 赞同
            String voteCount = e.select("span[class=count]").text();
            item.setVoteCount(voteCount);
            // 头像url
            String avatarUrl = e.select("img[class=zm-list-avatar]").attr("src");
            item.setAvatarUrl(avatarUrl);

            // 回答内容
//            String richTextHtml = e.select("div[class=zm-item-rich-text]").html();
            String richTextOriginal = e.select("div[class=zm-item-rich-text]").text();
            String answerSummary = null;
            // 裁剪回答内容，便于显示summary
            if (richTextOriginal.length() > SUMMARY_SIZE) {
                answerSummary = richTextOriginal.substring(0, SUMMARY_SIZE);
            } else {
                answerSummary = richTextOriginal;
            }
            item.setAnswerSummary(answerSummary);
            content.addAnswers(item);
        }
        return content;
    }
}