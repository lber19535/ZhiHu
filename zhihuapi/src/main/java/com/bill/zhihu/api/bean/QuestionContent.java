package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题页的所有信息
 * 已添加：问题，问题id，问题描述，所属话题，答案列表
 * <p/>
 * Created by Bill Lv on 2015/8/12.
 */
public class QuestionContent implements Parcelable {

    public static final String KEY = "QuestionContent";

    private String questionTitle;
    private String questionId;
    private String questionDetail;
    private Map<String, String> topics;
    private List<AnswerItemInQuestion> answers;

    public QuestionContent() {
        topics = new HashMap<>();
        answers = new ArrayList<>();
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * 以html方式存储，方便显示
     *
     */
    public String getQuestionDetail() {
        return questionDetail;
    }

    /**
     * 以html方式存储，方便显示
     *
     * @param questionDetail
     */
    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public void addTopic(String topic, String url) {

    }

    public Map<String, String> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, String> topics) {
        this.topics.putAll(topics);
    }

    public void addAnswers(AnswerItemInQuestion item) {
        answers.add(item);
    }

    public List<AnswerItemInQuestion> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerItemInQuestion> answers) {
        this.answers.addAll(answers);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.questionTitle);
        dest.writeString(this.questionId);
        dest.writeString(this.questionDetail);
        dest.writeMap(this.topics);
        dest.writeList(this.answers);
    }

    protected QuestionContent(Parcel in) {
        this.questionTitle = in.readString();
        this.questionId = in.readString();
        this.questionDetail = in.readString();
        this.topics = in.readHashMap(HashMap.class.getClassLoader());
        this.answers = new ArrayList<>();
        in.readList(this.answers, AnswerItemInQuestion.class.getClassLoader());
    }

    public static final Creator<QuestionContent> CREATOR = new Creator<QuestionContent>() {
        public QuestionContent createFromParcel(Parcel source) {
            return new QuestionContent(source);
        }

        public QuestionContent[] newArray(int size) {
            return new QuestionContent[size];
        }
    };
}
