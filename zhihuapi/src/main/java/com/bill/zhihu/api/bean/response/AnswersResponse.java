package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.answer.AnswerItem;
import com.bill.zhihu.api.bean.common.Paging;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bill_lv on 2016/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswersResponse {

    @JsonProperty("paging")
    public Paging paging;
    @JsonProperty("data")
    public List<AnswerItem> data = new ArrayList<>();
}
