package com.bill.zhihu.api.bean;

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

    private String question;
    private String questionUrl;
    private String voteCount;

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

}
