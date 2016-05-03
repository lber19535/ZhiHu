package com.bill.zhihu.api.bean.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerRelationship {
    @JsonProperty("voting")
    public int voteType;
    @JsonProperty("is_thanked")
    public boolean isThanked;
    @JsonProperty("is_favorited")
    public boolean isFavorited;
    @JsonProperty("is_nothelp")
    public boolean isNohelp;

}
