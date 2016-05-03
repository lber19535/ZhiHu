package com.bill.zhihu.api.bean.articles;

import com.bill.zhihu.api.bean.common.Author;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/5/3.
 */
public class Column {
    @JsonProperty("updated")
    public long updated;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("url")
    public String url;
    @JsonProperty("comment_permission")
    public String commentPermission;
    @JsonProperty("title")
    public String title;
    @JsonProperty("image_url")
    public String imageUrl;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;
}
