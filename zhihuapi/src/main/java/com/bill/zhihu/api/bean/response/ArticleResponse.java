package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.articles.Column;
import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.SuggestEdit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/5/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleResponse {
    @JsonProperty("updated")
    public int updated;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("id")
    public int id;
    @JsonProperty("voteup_count")
    public int voteupCount;
    @JsonProperty("created")
    public int created;
    @JsonProperty("url")
    public String url;
    @JsonProperty("comment_permission")
    public String commentPermission;
    @JsonProperty("title")
    public String title;
    @JsonProperty("content")
    public String content;
    @JsonProperty("column")
    public Column column;
    @JsonProperty("comment_count")
    public int commentCount;
    @JsonProperty("image_url")
    public String imageUrl;
    @JsonProperty("can_comment")
    public CanComment canComment;
    @JsonProperty("type")
    public String type;
    @JsonProperty("suggest_edit")
    public SuggestEdit suggestEdit;
}
