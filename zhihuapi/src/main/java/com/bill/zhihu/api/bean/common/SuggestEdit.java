package com.bill.zhihu.api.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestEdit implements Parcelable{
    @JsonProperty("status")
    public Boolean status;
    @JsonProperty("reason")
    public String reason;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.status);
        dest.writeString(this.reason);
    }

    public SuggestEdit() {
    }

    protected SuggestEdit(Parcel in) {
        this.status = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.reason = in.readString();
    }

    public static final Creator<SuggestEdit> CREATOR = new Creator<SuggestEdit>() {
        @Override
        public SuggestEdit createFromParcel(Parcel source) {
            return new SuggestEdit(source);
        }

        @Override
        public SuggestEdit[] newArray(int size) {
            return new SuggestEdit[size];
        }
    };
}
