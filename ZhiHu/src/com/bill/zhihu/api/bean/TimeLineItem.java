package com.bill.zhihu.api.bean;

import java.lang.reflect.Field;

/**
 * timeline item内容
 * 
 * @author Bill Lv
 *
 */
public class TimeLineItem {

    private String avatarName;
    private String avatarImgUrl;
    private String avatarHome;
    // 话题，人，专栏
    private String source;
    private String sourceUrl;
    private String timeStamp;
    private String timeTips;
    // 赞同，关注，来自
    private String typeString;
    private boolean isTopic;
    // item是否只是个问题
    private boolean onlyQuestion;
    // 问题
    private String question;
    private String questionUrl;
    private String voteCount;
    // 回答
    private String answerSummary;

    public String getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        this.answerSummary = answerSummary;
    }

    public boolean isOnlyQuestion() {
        return onlyQuestion;
    }

    public void setOnlyQuestion(boolean onlyQuestion) {
        this.onlyQuestion = onlyQuestion;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    public String getAvatarHome() {
        return avatarHome;
    }

    public void setAvatarHome(String avatarHome) {
        this.avatarHome = avatarHome;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getTimeTips() {
        return timeTips;
    }

    public void setTimeTips(String timeTips) {
        this.timeTips = timeTips;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public boolean isTopic() {
        return isTopic;
    }

    public void setTopic(boolean isTopic) {
        this.isTopic = isTopic;
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
