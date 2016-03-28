package com.bill.zhihu.api.bean.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Relationship {
    @JsonProperty("is_author")
    public Boolean isAuthor;
    @JsonProperty("is_following")
    public Boolean isFollowing;
    @JsonProperty("is_anonymous")
    public Boolean isAnonymous;
}
