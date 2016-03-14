package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsItemTarget implements Parcelable{

    @JsonProperty("title")
    public String title;
    // people or answer url
    @JsonProperty("url")
    public String url;
    @JsonProperty("answer_count")
    public  String answerCount;
    @JsonProperty("comment_count")
    public String commentCount;
    @JsonProperty("follower_count")
    public  String followerCount;
    @JsonProperty("type")
    public  String type;
    @JsonProperty("id")
    public  String id;
    @JsonProperty("author")
    public  FeedsItemAuthor author;
    @JsonProperty("question")
    public FeedsItemQuestion question;
    @JsonProperty("column")
    public FeedsItemColumn column;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("updated_time")
    public  String updatedTime;
    @JsonProperty("created_time")
    public String createdTime;
    @JsonProperty("voteup_count")
    public  String voteupCount;
    @JsonProperty("thanks_count")
    public String thanksCount;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.answerCount);
        dest.writeString(this.commentCount);
        dest.writeString(this.followerCount);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.question, flags);
        dest.writeParcelable(this.column, flags);
        dest.writeString(this.excerpt);
        dest.writeString(this.updatedTime);
        dest.writeString(this.createdTime);
        dest.writeString(this.voteupCount);
        dest.writeString(this.thanksCount);
    }

    public FeedsItemTarget() {
    }

    protected FeedsItemTarget(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.answerCount = in.readString();
        this.commentCount = in.readString();
        this.followerCount = in.readString();
        this.type = in.readString();
        this.id = in.readString();
        this.author = in.readParcelable(FeedsItemAuthor.class.getClassLoader());
        this.question = in.readParcelable(FeedsItemQuestion.class.getClassLoader());
        this.column = in.readParcelable(FeedsItemColumn.class.getClassLoader());
        this.excerpt = in.readString();
        this.updatedTime = in.readString();
        this.createdTime = in.readString();
        this.voteupCount = in.readString();
        this.thanksCount = in.readString();
    }

    public static final Creator<FeedsItemTarget> CREATOR = new Creator<FeedsItemTarget>() {
        public FeedsItemTarget createFromParcel(Parcel source) {
            return new FeedsItemTarget(source);
        }

        public FeedsItemTarget[] newArray(int size) {
            return new FeedsItemTarget[size];
        }
    };
}