package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementResponse {
    @JsonProperty("content")
    String content;
    @JsonProperty("url")
    String url;
    @JsonProperty("id")
    String id;
}
