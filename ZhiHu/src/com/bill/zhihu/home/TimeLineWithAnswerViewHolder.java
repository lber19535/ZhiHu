package com.bill.zhihu.home;

import android.view.View;

import com.bill.zhihu.view.TimeLineItemOnClickListener;
import com.bill.zhihu.view.TimeLineItemOnLongClickListener;

/**
 * 显示问题+答案摘要的item
 * 
 * @author Bill Lv
 *
 */
public class TimeLineWithAnswerViewHolder extends TimeLineViewHolder {

    public TimeLineWithAnswerViewHolder(View itemView) {
        super(itemView);

    }

    public TimeLineWithAnswerViewHolder(View itemView,
            TimeLineItemOnClickListener onClickListener,
            TimeLineItemOnLongClickListener onLongClickListener) {
        super(itemView, onClickListener, onLongClickListener);

    }

}
