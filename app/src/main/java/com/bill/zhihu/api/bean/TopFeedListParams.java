package com.bill.zhihu.api.bean;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

/**
 * top feed list 接口的post数据
 * 
 * @author Lv Beier
 *
 */
@JsonObject
public class TopFeedListParams {

    @JsonField
    private final String action = "next";
    @JsonField(name = "block_id")
    private long blockId;
    @JsonField
    private int offset;

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
