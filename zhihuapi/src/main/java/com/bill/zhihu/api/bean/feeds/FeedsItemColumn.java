package com.bill.zhihu.api.bean.feeds;

import android.os.Parcel;
import android.os.Parcelable;

import com.bill.zhihu.api.bean.common.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsItemColumn implements Parcelable {
    @JsonProperty("url")
    public String url;
    @JsonProperty("image_url")
    public String imageUrl;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public  String id;
    @JsonProperty("title")
    public  String title;
    @JsonProperty("author")
    public Author author;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.imageUrl);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeParcelable(this.author, 0);
    }

    public FeedsItemColumn() {
    }

    protected FeedsItemColumn(Parcel in) {
        this.url = in.readString();
        this.imageUrl = in.readString();
        this.type = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.author = in.readParcelable(Author.class.getClassLoader());
    }

    public static final Creator<FeedsItemColumn> CREATOR = new Creator<FeedsItemColumn>() {
        public FeedsItemColumn createFromParcel(Parcel source) {
            return new FeedsItemColumn(source);
        }

        public FeedsItemColumn[] newArray(int size) {
            return new FeedsItemColumn[size];
        }
    };
}
