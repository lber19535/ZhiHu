package com.bill.zhihu.model.answer;

import android.os.Parcel;
import android.os.Parcelable;

import com.bill.zhihu.api.bean.common.Author;

/**
 * Created by bill_lv on 2016/4/18.
 */
public class AnswerIntentValue implements Parcelable {

    private Author author;
    private String answerId;
    private String voteupCount;
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getVoteupCount() {
        return voteupCount;
    }

    public void setVoteupCount(String voteupCount) {
        this.voteupCount = voteupCount;
    }

    public AnswerIntentValue() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.answerId);
        dest.writeString(this.voteupCount);
        dest.writeString(this.title);
    }

    protected AnswerIntentValue(Parcel in) {
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.answerId = in.readString();
        this.voteupCount = in.readString();
        this.title = in.readString();
    }

    public static final Creator<AnswerIntentValue> CREATOR = new Creator<AnswerIntentValue>() {
        @Override
        public AnswerIntentValue createFromParcel(Parcel source) {
            return new AnswerIntentValue(source);
        }

        @Override
        public AnswerIntentValue[] newArray(int size) {
            return new AnswerIntentValue[size];
        }
    };
}
