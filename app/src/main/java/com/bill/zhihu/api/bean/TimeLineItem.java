package com.bill.zhihu.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * timeline item内容
 *
 * @author Bill Lv
 */
public class TimeLineItem implements Parcelable {
    /*
     * item分为三个部分
     * 
     * |----------------------------------| 
     * |source                         avatar |
     * |----------------------------------| 
     * |question |                 |answer |
     * |----------------------------------|
     */

    public static String KEY = "TimeLineItem";

    private ContentType contentType = ContentType.ANSWER;
    // 头像
    private String avatarImgUrl;
    // source
    private List<String> source = new ArrayList<>();
    // 来自/关注了/赞同了
    private String sourceText;
    private List<Url> sourceUrls = new ArrayList<>();
    private String timeStamp;
    // 人类可读的一个时间
    private String time;
    private String dataOffset;
    // 问题或文章标题
    private String question;
    private Url questionUrl;
    private String voteCount;
    // 回答
    private String answerSummary;
    private Url answerUrl;
    private String answerId;
    // block标记
    private String dataBlock;

    public TimeLineItem() {
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    public List<String> getSource() {
        return source;
    }

    public void addSource(String source) {
        this.source.add(source);
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public List<Url> getSourceUrls() {
        return sourceUrls;
    }

    public void addSourceUrl(String sourceUrl, Url.Type type) {
        this.sourceUrls.add(new Url(sourceUrl, type));
    }

    public void addSourceUrl(Url url) {
        this.sourceUrls.add(url);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Url getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(Url questionUrl) {
        this.questionUrl = questionUrl;
    }

    public Url getAnswerUrl() {
        return answerUrl;
    }

    public void setAnswerUrl(Url answerUrl) {
        this.answerUrl = answerUrl;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        this.answerSummary = answerSummary;
    }

    public String getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(String dataBlock) {
        this.dataBlock = dataBlock;
    }

    public String getDataOffset() {
        return dataOffset;
    }

    public void setDataOffset(String dataOffset) {
        this.dataOffset = dataOffset;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName());
            try {
                sb.append(field.get(this).toString());
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * content类型
     *
     * @author Bill Lv
     */
    public enum ContentType {
        /**
         * 只有问题
         */
        QUESTION,
        /**
         * 默认为问题+答案摘要
         */
        ANSWER,
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentType == null ? -1 : this.contentType.ordinal());
        dest.writeString(this.avatarImgUrl);
        dest.writeStringList(this.source);
        dest.writeString(this.sourceText);
        dest.writeList(this.sourceUrls);
        dest.writeString(this.timeStamp);
        dest.writeString(this.time);
        dest.writeString(this.dataOffset);
        dest.writeString(this.question);
        dest.writeParcelable(this.questionUrl, flags);
        dest.writeString(this.voteCount);
        dest.writeString(this.answerSummary);
        dest.writeParcelable(this.answerUrl, flags);
        dest.writeString(this.dataBlock);
    }

    protected TimeLineItem(Parcel in) {
        int tmpContentType = in.readInt();
        this.contentType = tmpContentType == -1 ? null : ContentType.values()[tmpContentType];
        this.avatarImgUrl = in.readString();
        this.source = in.createStringArrayList();
        this.sourceText = in.readString();
        this.sourceUrls = new ArrayList<Url>();
        in.readList(this.sourceUrls, Url.class.getClassLoader());
        this.timeStamp = in.readString();
        this.time = in.readString();
        this.dataOffset = in.readString();
        this.question = in.readString();
        this.questionUrl = in.readParcelable(Url.class.getClassLoader());
        this.voteCount = in.readString();
        this.answerSummary = in.readString();
        this.answerUrl = in.readParcelable(Url.class.getClassLoader());
        this.dataBlock = in.readString();
    }

    public static final Creator<TimeLineItem> CREATOR = new Creator<TimeLineItem>() {
        public TimeLineItem createFromParcel(Parcel source) {
            return new TimeLineItem(source);
        }

        public TimeLineItem[] newArray(int size) {
            return new TimeLineItem[size];
        }
    };
}
