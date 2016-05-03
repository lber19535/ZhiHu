package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThankResponse {
    @JsonProperty("is_thanked")
    public boolean isThanked;


}
