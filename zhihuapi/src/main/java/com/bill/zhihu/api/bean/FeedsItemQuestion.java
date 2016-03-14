package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsItemQuestion implements Parcelable{

    @JsonProperty("url")
    public String url;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public  String id;
    @JsonProperty("title")
    public  String title;
    @JsonProperty("author")
    public  FeedsItemAuthor author;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeParcelable(this.author, 0);
    }

    public FeedsItemQuestion() {
    }

    protected FeedsItemQuestion(Parcel in) {
        this.url = in.readString();
        this.type = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.author = in.readParcelable(FeedsItemAuthor.class.getClassLoader());
    }

    public static final Creator<FeedsItemQuestion> CREATOR = new Creator<FeedsItemQuestion>() {
        public FeedsItemQuestion createFromParcel(Parcel source) {
            return new FeedsItemQuestion(source);
        }

        public FeedsItemQuestion[] newArray(int size) {
            return new FeedsItemQuestion[size];
        }
    };
}
