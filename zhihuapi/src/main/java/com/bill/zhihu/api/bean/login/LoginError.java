package com.bill.zhihu.api.bean.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginError {
    @JsonProperty("message")
    public String message;
    @JsonProperty("code")
    public String code;
    @JsonProperty("name")
    public String name;
}
