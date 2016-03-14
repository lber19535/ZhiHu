package com.bill.zhihu.api.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/8.
 */

public class Paging {
    @JsonProperty("is_end")
    public boolean isEnd;
    @JsonProperty("next")
    public String nextPage;
    @JsonProperty("previous")
    public String previous;
}
