package com.bill.zhihu.api.bean;

/**
 * item中的url类型
 * <p/>
 * Created by Bill-pc on 2015/5/15.
 */
public class Url {

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
}
