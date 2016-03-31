package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CanComment {
    @JsonProperty("status")
    public Boolean status;
    @JsonProperty("reason")
    public String reason;
}
