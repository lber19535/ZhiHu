package com.bill.zhihu.api.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/19.
 */
public class CommentItem {

    @JsonProperty("allow_reply")
    public boolean allowReply;
    @JsonProperty("ancestor")
    public boolean ancestor;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("is_delete")
    public boolean isDelete;
    @JsonProperty("url")
    public String url;
    @JsonProperty("is_author")
    public boolean isAuthor;
    @JsonProperty("is_parent_author")
    public boolean isParentAuthor;
    @JsonProperty("content")
    public String content;
    @JsonProperty("allow_vote")
    public boolean allowVote;
    @JsonProperty("vote_count")
    public int voteCount;
    @JsonProperty("allow_delete")
    public boolean allowDelete;
    @JsonProperty("created_time")
    public int createdTime;
    @JsonProperty("voting")
    public boolean voting;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public int id;
}
