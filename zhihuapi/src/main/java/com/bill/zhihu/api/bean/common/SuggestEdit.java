package com.bill.zhihu.api.bean.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestEdit {
    @JsonProperty("status")
    public Boolean status;
    @JsonProperty("reason")
    public String reason;
}
