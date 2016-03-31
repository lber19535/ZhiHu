package com.bill.zhihu.api.bean.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {
    @JsonProperty("name")
    public String name;
    @JsonProperty("url")
    public String url;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("introduction")
    public String introduction;
    @JsonProperty("avatar_url")
    public String avatarUrl;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public long id;
}
