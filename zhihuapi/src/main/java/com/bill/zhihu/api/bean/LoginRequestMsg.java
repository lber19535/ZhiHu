package com.bill.zhihu.api.bean;

//import com.bill.jeson.annotation.JsonField;
//import com.bill.jeson.annotation.JsonObject;

//@JsonObject
public class LoginRequestMsg {

//    @JsonField(defaultValue = "")
    private String captcha;
//    @JsonField(defaultValue = "")
    private String email;

    public String getMsg() {
        if (!captcha.isEmpty()) {
            return captcha;
        } else if (!email.isEmpty()) {
            return email;
        } else {
            return null;
        }
    }

}
