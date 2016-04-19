package com.bill.zhihu.api.bean.feeds;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bill_lv on 2016/3/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsItem implements Parcelable {

    @JsonProperty("target")
    public FeedsItemTarget target;
    @JsonProperty("updated_time")
    public long updateTime;
    // item type
    @JsonProperty("verb")
    public String verb;
    @JsonProperty("actors")
    public List<FeedsActor> actors;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public int id;
    @JsonProperty("count")
    public String count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.target, flags);
        dest.writeLong(this.updateTime);
        dest.writeString(this.verb);
        dest.writeList(this.actors);
        dest.writeString(this.type);
        dest.writeInt(this.id);
        dest.writeString(this.count);
    }

    public FeedsItem() {
    }

    protected FeedsItem(Parcel in) {
        this.target = in.readParcelable(FeedsItemTarget.class.getClassLoader());
        this.updateTime = in.readLong();
        this.verb = in.readString();
        this.actors = new ArrayList<>();
        in.readList(this.actors, FeedsActor.class.getClassLoader());
        this.type = in.readString();
        this.id = in.readInt();
        this.count = in.readString();
    }

    public static final Creator<FeedsItem> CREATOR = new Creator<FeedsItem>() {
        public FeedsItem createFromParcel(Parcel source) {
            return new FeedsItem(source);
        }

        public FeedsItem[] newArray(int size) {
            return new FeedsItem[size];
        }
    };
}
