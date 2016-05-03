package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.login.LoginError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCaptchaResponse {
    @JsonProperty("error")
    public LoginError error;
    @JsonProperty("success")
    public boolean success = false;
}
