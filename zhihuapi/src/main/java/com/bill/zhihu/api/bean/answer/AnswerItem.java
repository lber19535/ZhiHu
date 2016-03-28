package com.bill.zhihu.api.bean.answer;

import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.Question;
import com.bill.zhihu.api.bean.common.SuggestEdit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerItem {
    @JsonProperty("is_copyable")
    public Boolean isCopyable;
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
    public Integer updatedTime;
    @JsonProperty("created_time")
    public Integer createdTime;
    @JsonProperty("voteup_count")
    public Integer voteupCount;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("thanks_count")
    public Integer thanksCount;
}
