package com.bill.zhihu.api.factory;

import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.utils.ZhihuURL;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * Created by Bill-pc on 2015/7/4.
 */
public class AnswerContentFactory {

    private static final String TAG = "AnswerContentFactory";

    private AnswerContent answerContent;

    private Elements elements;

    public AnswerContentFactory(Elements elements) {
        this.elements = elements;
    }

    public AnswerContent create() {
        answerContent = new AnswerContent();

        //aid 和 answer_id
        answerContent.setAnswerId(elements.attr("data-atoken"));
        answerContent.setAid(elements.attr("data-aid"));

        //赞同票
        Elements voter = elements.select("div[class=zm-votebar]");
        String voteCount = voter.select("span[class=count]").text();
        answerContent.setVote(voteCount);

        //答主主页url
        Elements peopleUrl = elements.select("a[class=zm-item-link-avatar]");
        String href = peopleUrl.attr("href");
        answerContent.setPeopleUrl(ZhihuURL.HOST + href);

        //答主姓名
        Elements people = elements.select("a[href=" + peopleUrl.attr("href") + "]");
        answerContent.setPeopleName(people.text());

        //答主头像
        Elements avatarUrlElement = elements.select("img[class=zm-list-avatar]");
        answerContent.setAvatarImgUrl(avatarUrlElement.attr("src").replace("_s", "_m"));

        // 简介
        Elements introElement = elements.select("strong[class=zu-question-my-bio]");
        answerContent.setIntro(introElement.attr("title"));

        //发布日期 编辑日期
        Elements postDateElement = elements
                .select("a[class^=answer-date-link]");
        if (postDateElement.hasAttr("data-tip")) {
            //编辑日期
            answerContent.setEditDate(postDateElement.attr("data-tip").replace("s$t$",
                    ""));
        }
        answerContent.setPostDate(postDateElement.text());

        //答案内容 添加一个样式表显示美观，样式表是从知乎网站上copy下来的
        Attribute relAttr = new Attribute("rel", "stylesheet");
        Attribute hrefAttr = new Attribute("href", "answer_style.css");
        Attribute typeAttr = new Attribute("type", "text/css");
        Attributes attrs = new Attributes();
        attrs.put(relAttr);
        attrs.put(hrefAttr);
        attrs.put(typeAttr);
        Element styleSheetElement = new Element(Tag.valueOf("link"), "", attrs);
        Elements answerElement = elements.select("div[class=zm-item-rich-text]>div");
        answerElement.prepend(styleSheetElement.toString());
        answerElement.select("img[class$=lazy]").remove();//去掉img造成的空白图片，webview中加载的是noscript标签中的
        answerContent.setAnswer(answerElement.toString());

        return answerContent;
    }
}
