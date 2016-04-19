package com.bill.zhihu.api.bean.answer;

import android.os.Parcel;
import android.os.Parcelable;

import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.Question;
import com.bill.zhihu.api.bean.common.SuggestEdit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerItem implements Parcelable {
    @JsonProperty("is_copyable")
    public boolean isCopyable;
    @JsonProperty("suggest_edit")
    public SuggestEdit suggestEdit;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("url")
    public String url;
    @JsonProperty("question")
    public Question question;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("updated_time")
    public int updatedTime;
    @JsonProperty("created_time")
    public int createdTime;
    @JsonProperty("voteup_count")
    public String voteupCount;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;
    @JsonProperty("thanks_count")
    public int thanksCount;


    public AnswerItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(isCopyable ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.suggestEdit, flags);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.url);
        dest.writeParcelable(this.question, flags);
        dest.writeString(this.excerpt);
        dest.writeInt(this.updatedTime);
        dest.writeInt(this.createdTime);
        dest.writeString(this.voteupCount);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeInt(this.thanksCount);
    }

    protected AnswerItem(Parcel in) {
        this.isCopyable = in.readByte() != 0;
        this.suggestEdit = in.readParcelable(SuggestEdit.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.url = in.readString();
        this.question = in.readParcelable(Question.class.getClassLoader());
        this.excerpt = in.readString();
        this.updatedTime = in.readInt();
        this.createdTime = in.readInt();
        this.voteupCount = in.readString();
        this.type = in.readString();
        this.id = in.readString();
        this.thanksCount = in.readInt();
    }

    public static final Creator<AnswerItem> CREATOR = new Creator<AnswerItem>() {
        @Override
        public AnswerItem createFromParcel(Parcel source) {
            return new AnswerItem(source);
        }

        @Override
        public AnswerItem[] newArray(int size) {
            return new AnswerItem[size];
        }
    };
}
