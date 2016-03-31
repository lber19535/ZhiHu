package com.bill.zhihu.api.bean.feeds;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsActor implements Parcelable {
    @JsonProperty("name")
    public String name;
    @JsonProperty("url")
    public String url;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("introduction")
    public String introduction;
    @JsonProperty("avatar_url")
    public String avatarUrl;
    /**
     * topic or people
     * <p/>
     * {@link FeedsActorType}
     */
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.excerpt);
        dest.writeString(this.introduction);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.type);
        dest.writeString(this.id);
    }

    public FeedsActor() {
    }

    protected FeedsActor(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.excerpt = in.readString();
        this.introduction = in.readString();
        this.avatarUrl = in.readString();
        this.type = in.readString();
        this.id = in.readString();
    }

    public static final Creator<FeedsActor> CREATOR = new Creator<FeedsActor>() {
        @Override
        public FeedsActor createFromParcel(Parcel source) {
            return new FeedsActor(source);
        }

        @Override
        public FeedsActor[] newArray(int size) {
            return new FeedsActor[size];
        }
    };
}
