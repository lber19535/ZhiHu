package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 问题界面下面的答案summary列表中的item
 * 包括头像，答主，回答缩略，赞同数
 * <p/>
 * Created by Bill Lv on 2015/8/12.
 */
public class AnswerItemInQuestion implements Parcelable {
    public static final String KEY = "AnswerItemInQuestion";

    private String avatarUrl;
    private String peopleName;
    // 签名(英文bio是个人经历)
    private String peopleBio;
    private String answerSummary;
    private String voteCount;
    private String answerUrl;
    private String questionTitle;

    public AnswerItemInQuestion() {
    }

    /**
     * 头像缩略图url
     *
     * @return
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * avatar url是个缩略图
     *
     * @param avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleBio() {
        return peopleBio;
    }

    public void setPeopleBio(String peopleBio) {
        this.peopleBio = peopleBio;
    }

    public String getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        this.answerSummary = answerSummary;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getAnswerUrl() {
        return answerUrl;
    }

    public void setAnswerUrl(String answerUrl) {
        this.answerUrl = answerUrl;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatarUrl);
        dest.writeString(this.peopleName);
        dest.writeString(this.peopleBio);
        dest.writeString(this.answerSummary);
        dest.writeString(this.voteCount);
        dest.writeString(this.answerUrl);
        dest.writeString(this.questionTitle);
    }

    protected AnswerItemInQuestion(Parcel in) {
        this.avatarUrl = in.readString();
        this.peopleName = in.readString();
        this.peopleBio = in.readString();
        this.answerSummary = in.readString();
        this.voteCount = in.readString();
        this.answerUrl = in.readString();
        this.questionTitle = in.readString();
    }

    public static final Creator<AnswerItemInQuestion> CREATOR = new Creator<AnswerItemInQuestion>() {
        public AnswerItemInQuestion createFromParcel(Parcel source) {
            return new AnswerItemInQuestion(source);
        }

        public AnswerItemInQuestion[] newArray(int size) {
            return new AnswerItemInQuestion[size];
        }
    };
}
