package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCaptchaResponse {
    @JsonProperty("img_base64")
    public String imgbase64;
}
