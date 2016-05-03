package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteResponse {

    /**
     * {@link com.bill.zhihu.api.bean.common.VoteType}
     */
    @JsonProperty("voting")
    public int voteType;
    @JsonProperty("voteup_count")
    public int voteup_count;
}
