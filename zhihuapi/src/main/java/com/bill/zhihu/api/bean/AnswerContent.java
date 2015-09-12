package com.bill.zhihu.api.bean;

/**
 * 答案主体部分，包括答主头像、名字、简介、答案内容、答案id、赞同票
 * <p/>
 * Created by Bill-pc on 2015/7/4.
 */
public class AnswerContent {

    //赞同
    private String vote;
    //头像地址
    private String avatarImgUrl;
    //答主姓名
    private String peopleName;
    //答主简介
    private String intro;
    //答案内容
    private String answer;
    //发布日期
    private String postDate;
    //编辑日期
    private String editDate;
    //答案id
    private String answerId;
    //答主主页地址
    private String peopleUrl;
    // aid用于赞同、举报、没有帮助的时候使用
    private String aid;


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getPeopleUrl() {
        return peopleUrl;
    }

    public void setPeopleUrl(String peopleUrl) {
        this.peopleUrl = peopleUrl;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
