package com.bill.zhihu.api.bean;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class MoreNewsParams {

    @JsonField
    private String action = "more";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
