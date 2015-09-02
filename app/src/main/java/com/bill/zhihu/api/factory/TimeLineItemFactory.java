package com.bill.zhihu.api.factory;

import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TimeLineItem.ContentType;
import com.bill.zhihu.api.bean.Url;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;

/**
 * 简化生成item的过程
 *
 * @author Bill Lv
 */
public class TimeLineItemFactory {

    private static final String TAG = "TimeLineItemFactory";

    private TimeLineItem item;
    private Element element;

    public TimeLineItemFactory(Element element) {
        this.item = new TimeLineItem();
        this.element = element;

    }

    /**
     * 设置头像
     *
     * @return
     */
    private TimeLineItemFactory setAvatar() {
        // avatar 头像
        Elements avatarElements = element
                .select("div[class=feed-item-inner]>div[class=avatar]>a");
        String avatarImgUrl = avatarElements.select("img").attr("src");
        String blockId = element.attr("data-block");
        String dataOffset = element.attr("data-offset");
        ZhihuLog.dValue(TAG, "avatarImgUrl ", avatarImgUrl);
        ZhihuLog.dValue(TAG, "blockId ", blockId);
        // 由于官方首页代码中avatar url的改动去掉了 http 所以这里要加上
        item.setAvatarImgUrl("http:" + avatarImgUrl);
        item.setDataBlock(blockId);
        item.setDataOffset(dataOffset);
        return this;
    }

    /**
     * 设置来源，赞同了xxx回答，关注了xx问题/专栏，来自xxx
     *
     * @return
     */
    private TimeLineItemFactory setSource() {
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
            item.addSourceUrl(getUrl(element));
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
    private TimeLineItemFactory setQuestion() {
        Elements contentElements = element.select("div[class=content]");
        boolean haveAnswer = contentElements.select("div").hasClass(
                "entry-body");

        String question = contentElements.select("h2>a").text();
        String questionUrl = contentElements.select("a").attr("href");

        Element header = contentElements.select("h2>a").get(0);
        ZhihuLog.d(TAG, header);
        Url headerUrl = getUrl(header);

        ZhihuLog.dValue(TAG, "question ", question);
        ZhihuLog.dValue(TAG, "questionUrl ", header);
        ZhihuLog.dValue(TAG, "haveAnswer ", haveAnswer);
        ZhihuLog.dValue(TAG, "questionUrl ", questionUrl);
        item.setQuestion(question);
        item.setQuestionUrl(headerUrl);
        if (!haveAnswer) {
            item.setContentType(ContentType.QUESTION);
        } else {
            String answerId = contentElements.select("div[class=entry-body]").attr("data-aid");
            item.setContentType(ContentType.ANSWER);
            item.setAnswerId(answerId);
        }

        return this;
    }

    private Url getUrl(Element element) {
        Url.Type type = getUrlType(element);
        String href = element.attr("href");
        String url = "";
        switch (type) {
            // 网页上的url不完整 需要加知乎的host
            case TOPIC:
            case ANSWER:
            case QUESTION:
                url = ZhihuURL.HOST + href.split("#")[0];
                break;
            case COLUMN:
                // 网页上的url就是需要的url
            case COLUMN_ARTICLE:
            case PEOPLE:
                url = href;
                break;
            case COMMENT:
                break;
        }
        return getUrl(url, type);
    }

    /**
     * 通过 class href等获取链接类型，评论类型的暂时还不知道怎么判断
     *
     * @param element
     * @return
     */
    private Url.Type getUrlType(Element element) {
        String href = element.attr("href");
        boolean haveClass = element.hasAttr("class");
        if (haveClass) {
            if (element.hasClass("column_link")) {
                return Url.Type.COLUMN;
            } else if (element.hasClass("post-link")) {
                return Url.Type.COLUMN_ARTICLE;
            } else if (element.hasClass("zg-link")) {
                return Url.Type.PEOPLE;
            } else if (element.hasClass("question_link")) {
                return Url.Type.QUESTION;
            } else if (element.hasClass("toggle-expand") || element.hasClass("answer-date-link meta-item")) {
                return Url.Type.ANSWER;
            }
        } else {
            if (href.startsWith("/topic")) {
                return Url.Type.TOPIC;
            }
        }
        return Url.Type.UNKONW;
    }

    private Url getUrl(String url, Url.Type type) {
        return new Url(url, type);
    }

    /**
     * 答案摘要
     *
     * @return
     */
    private TimeLineItemFactory setAnswer() {
        if (item.getContentType() == ContentType.QUESTION) {
            return this;
        }
        Elements answerElements = element.select("div[class=content]");
        String answerSummary = answerElements.select(
                "div[class=zh-summary summary clearfix]").text();
        String voteCount = answerElements.select("[class^=zm-item-vote-info]")
                .attr("data-votecount");

        Element toggleExpand = answerElements.select(
                "div[class=zh-summary summary clearfix]>a").last();
        String dataAtoken = element.select("div[class^=entry-body]").attr("data-atoken");

        ZhihuLog.dValue(TAG, "answerSummary ", answerSummary);
        ZhihuLog.dValue(TAG, "voteCount ", voteCount);
        ZhihuLog.dValue(TAG, "toggleExpand ", toggleExpand);

        // 有的答案比较短，在展开的tag中不带目标的地址，需要自己组合一下
        Url url = null;
        if (toggleExpand == null) {
            String answerUrl = ZhihuURL.HOST + element.select("a[class=question_link]").attr("href").split("-")[0].replace("#", "/") + "/" + dataAtoken;
            url = new Url(answerUrl, Url.Type.ANSWER);
        } else {
            url = getUrl(toggleExpand);
        }

        item.setAnswerUrl(url);
        item.setAnswerSummary(answerSummary.replace("显示全部", ""));
        item.setVoteCount(voteCount);

        return this;
    }

    /**
     * 生成一个item
     *
     * @return
     */
    public TimeLineItem create() {
        setAvatar().setSource().setQuestion().setAnswer();
        return item;
    }
}
