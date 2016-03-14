package com.bill.zhihu.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsActor {
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
    /**
     * topic or people
     * <p/>
     * {@link FeedsActorType}
     */
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;

}
