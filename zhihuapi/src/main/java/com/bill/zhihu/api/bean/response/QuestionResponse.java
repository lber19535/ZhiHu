package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.SuggestEdit;
import com.bill.zhihu.api.bean.question.Relationship;
import com.bill.zhihu.api.bean.question.Status;
import com.bill.zhihu.api.bean.question.Topic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionResponse {
    @JsonProperty("status")
    public Status status;
    @JsonProperty("suggest_edit")
    public SuggestEdit suggestEdit;
    @JsonProperty("relationship")
    public Relationship relationship;
    @JsonProperty("title")
    public String title;
    @JsonProperty("url")
    public String url;
    @JsonProperty("topics")
    public List<Topic> topics = new ArrayList<>();
    @JsonProperty("author")
    public Author author;
    @JsonProperty("detail")
    public String detail;
    @JsonProperty("answer_count")
    public int answerCount;
    @JsonProperty("updated_time")
    public long updatedTime;
//    @JsonProperty("redirection")
//    public Redirection redirection;
    @JsonProperty("comment_count")
    public int commentCount;
    @JsonProperty("except")
    public String except;
//    @JsonProperty("draft")
//    public Draft draft;
    @JsonProperty("follower_count")
    public int followerCount;
    @JsonProperty("allow_delete")
    public boolean allowDelete;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public long id;
}
