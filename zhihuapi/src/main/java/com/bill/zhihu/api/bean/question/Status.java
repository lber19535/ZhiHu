package com.bill.zhihu.api.bean.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    @JsonProperty("is_locked")
    public boolean isLocked;
}
