package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * item中的url类型
 * <p/>
 * Created by Bill-pc on 2015/5/15.
 */
public class Url implements Parcelable{

    private String url;
    private Type urlType = Type.ANSWER;

    public Url(String url, Type urlType) {
        this.url = url;
        this.urlType = urlType;
    }

    public Type getUrlType() {
        return urlType;
    }

    public void setUrlType(Type urlType) {
        this.urlType = urlType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("type:");
        sb.append(Type.ANSWER.toString());
        sb.append("\n");
        sb.append("url:");
        sb.append(url);
        return sb.toString();
    }

    public enum Type {
        TOPIC,
        ANSWER,
        QUESTION,
        COLUMN,
        COLUMN_ARTICLE,
        PEOPLE,
        COMMENT,
        UNKONW
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.urlType == null ? -1 : this.urlType.ordinal());
    }

    protected Url(Parcel in) {
        this.url = in.readString();
        int tmpUrlType = in.readInt();
        this.urlType = tmpUrlType == -1 ? null : Type.values()[tmpUrlType];
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        public Url createFromParcel(Parcel source) {
            return new Url(source);
        }

        public Url[] newArray(int size) {
            return new Url[size];
        }
    };
}
