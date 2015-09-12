package com.bill.zhihu.api.bean;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

/**
 * 下拉刷新 接口的post数据
 * 
 * @author Lv Beier
 *
 */
@JsonObject
public class TopRefreshParams {

    @JsonField
    private final String action = "live";
    @JsonField(name = "limit")
    private final long limit = 1;

    public String getAction() {
        return action;
    }

    public long getLimit() {
        return limit;
    }

}
