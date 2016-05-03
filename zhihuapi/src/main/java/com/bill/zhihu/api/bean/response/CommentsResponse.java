package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.common.CommentItem;
import com.bill.zhihu.api.bean.common.Paging;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bill_lv on 2016/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsResponse {

    @JsonProperty("paging")
    public Paging paging;
    @JsonProperty("data")
    public List<CommentItem> data = new ArrayList<>();
}
