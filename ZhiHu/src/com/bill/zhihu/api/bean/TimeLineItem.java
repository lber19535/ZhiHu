package com.bill.zhihu.api.bean;

import java.lang.reflect.Field;

/**
 * timeline item内容
 * 
 * @author Bill Lv
 *
 */
public class TimeLineItem {
    /*
     * item分为三个部分
     *
     * |----------------------------------|
     * |source                         avatar |
     * |----------------------------------|
     * |question                                 |
     * |answer                                   |
     * |----------------------------------|
     *
     */

    /**
     * 来源类型
     * 
     * @author Bill Lv
     *
     */
    public static enum SourceType {
        /**
         * 变量在右边： 来自XXX
         */
        RIGHT,
        /**
         * 默认在左边，变量在左边 XXX回答/赞同/关注了该问题/回答，XXX关注了专栏/赞同了文章
         */
        LEFT,
    }

    /**
     * content类型
     * 
     * @author Bill Lv
     *
     */
    public static enum ContentType {
        /**
         * 只有问题
         */
        QUESTION,
        /**
         * 默认为问题+答案摘要
         */
        ANSWER
    }

    private SourceType sourceType = SourceType.LEFT;
    private ContentType contentType = ContentType.ANSWER;

    // 头像
    private String avatarImgUrl;

    // source
    private String source;
    // 来自/关注了/赞同了
    private String sourceText;
    private String sourceUrl;
    private String timeStamp;
    // 人类可读的一个时间
    private String time;

    // 问题或文章标题
    private String question;
    private String questionUrl;
    private String voteCount;
    // 回答
    private String answerSummary;
    // block标记
    private String dataBlock;

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        this.answerSummary = answerSummary;
    }

    public String getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(String dataBlock) {
        this.dataBlock = dataBlock;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName());
            try {
                sb.append(field.get(this).toString());
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
