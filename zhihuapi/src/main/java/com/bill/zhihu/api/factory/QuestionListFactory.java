package com.bill.zhihu.api.factory;

import android.text.TextUtils;

import com.bill.zhihu.api.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.AnswerItemInQuestion;
import com.bill.zhihu.api.bean.QuestionContent;
import com.bill.zhihu.api.utils.UrlUtils;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
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
        ZhihuLog.d(TAG, "QuestionListFactory");
        // question id 用于关注
        String questionId = element.select("div[id=zh-question-detail]").attr("data-resourceid");
        // content
        content.setQuestionId(questionId);
        ZhihuLog.d(TAG, "questionId");

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

            // 加入css
            Attribute relAttr = new Attribute("rel", "stylesheet");
            Attribute hrefAttr = new Attribute("href", "answer_style.css");
            Attribute typeAttr = new Attribute("type", "text/css");
            Attributes attrs = new Attributes();
            attrs.put(relAttr);
            attrs.put(hrefAttr);
            attrs.put(typeAttr);
            Element styleSheetElement = new Element(Tag.valueOf("link"), "", attrs);
            questElement.prepend(styleSheetElement.toString());

            content.setQuestionDetail(questElement.toString());
        }

        // title
        String title = element.select("h2[class^=zm-item-title]").text();
        content.setQuestionTitle(title);

        // 话题
        Elements topicElements = element.select("a[class=zm-item-tag]");
        for (Element topicElement : topicElements) {
            String topicUrl = topicElement.attr("href");
            String topic = topicElement.text();
            // content
            content.addTopic(topic, ZhihuURL.HOST + topicUrl);
            ZhihuLog.dValue(TAG, "topicUrl ", topicUrl);
            ZhihuLog.dValue(TAG, "topic ", topic);
        }

        // 各个回答
        // 由于网页经常调整，这里可能会经常修改
        Elements answerElements = element.select("div[id=zh-question-answer-wrap] > div[class^=zm-item-answer]");
        ZhihuLog.dValue(TAG, "answerElements size is ", answerElements.size());
        for (Element e : answerElements) {
            AnswerItemInQuestion item = new AnswerItemInQuestion();

            // 问题
            item.setQuestionTitle(title);
            // 答主姓名加入对匿名用户的判断
            String name = e.select("a[href^=/people]").text();
            if (TextUtils.isEmpty(name)) {
                item.setPeopleName(ZhihuApi.getRes().getString(R.string.anonymous));
            } else {
                item.setPeopleName(name);
            }
            ZhihuLog.dValue(TAG, "name  is ", name);
            // 答主签名
            String bio = e.select("strong[class=zu-question-my-bio]").text();
            ZhihuLog.dValue(TAG, "bio  is ", bio);
            item.setPeopleBio(bio);
            // 赞同
            String voteCount = e.select("span[class=count]").text();
            ZhihuLog.dValue(TAG, "voteCount  is ", voteCount);
            item.setVoteCount(voteCount);
            // 头像url
            String avatarUrl = e.select("img[class=zm-list-avatar]").attr("src");
            ZhihuLog.dValue(TAG, "avatarUrl  is ", avatarUrl);
            item.setAvatarUrl(UrlUtils.avatarUrlParse(avatarUrl));

            // 答案url
            String answerUrl = e.select("a[class^=answer-date-link]").attr("href");
            ZhihuLog.dValue(TAG, "answerUrl  is ", answerUrl);
            item.setAnswerUrl(ZhihuURL.HOST + answerUrl);

            // 回答内容
            String richTextOriginal = e.select("div[class=zm-item-rich-text]").text();
            ZhihuLog.dValue(TAG, "richTextOriginal  is ", richTextOriginal);
            String answerSummary;
            // 裁剪回答内容，便于显示summary
            if (richTextOriginal.length() > SUMMARY_SIZE) {
                answerSummary = richTextOriginal.substring(0, SUMMARY_SIZE);
            } else {
                answerSummary = richTextOriginal;
            }
            ZhihuLog.dValue(TAG, "answerSummary  is ", answerSummary);
            item.setAnswerSummary(answerSummary);
            content.addAnswers(item);
        }
        return content;
    }
}