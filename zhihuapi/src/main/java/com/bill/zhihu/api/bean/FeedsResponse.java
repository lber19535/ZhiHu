package com.bill.zhihu.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bill_lv on 2016/3/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsResponse {

    @JsonProperty("paging")
    public Paging paging;
    @JsonProperty("data")
    public List<FeedsItem> items;
}
