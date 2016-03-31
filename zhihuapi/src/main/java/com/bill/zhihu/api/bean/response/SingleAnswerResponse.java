package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.Question;
import com.bill.zhihu.api.bean.common.SuggestEdit;
import com.bill.zhihu.api.bean.question.Relationship;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleAnswerResponse {

    @JsonProperty("suggest_edit")
    public SuggestEdit suggestEdit;
    @JsonProperty("is_copyable")
    public Boolean isCopyable;
    @JsonProperty("can_comment")
    public CanComment canComment;
    @JsonProperty("is_mine")
    public Boolean isMine;
    @JsonProperty("relationship")
    public Relationship relationship;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("url")
    public String url;
    @JsonProperty("question")
    public Question question;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("updated_time")
    public long updatedTime;
    @JsonProperty("content")
    public String content;
    @JsonProperty("comment_count")
    public int commentCount;
    @JsonProperty("comment_permission")
    public String commentPermission;
    @JsonProperty("created_time")
    public long createdTime;
    @JsonProperty("voteup_count")
    public int voteupCount;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public long id;
    @JsonProperty("thanks_count")
    public int thanksCount;

}
