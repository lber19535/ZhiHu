package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowCaptchaResponse {
    @JsonProperty("show_captcha")
    public boolean showCaptcha;
}
