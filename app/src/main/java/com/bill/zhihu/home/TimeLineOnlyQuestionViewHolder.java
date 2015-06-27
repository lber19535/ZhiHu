package com.bill.zhihu.home;

import android.view.View;

/**
 * 只显示问题的item
 *
 * @author Bill Lv
 */
public class TimeLineOnlyQuestionViewHolder extends TimeLineViewHolder {

    public TimeLineOnlyQuestionViewHolder(View itemView) {
        super(itemView);

    }

    public TimeLineOnlyQuestionViewHolder(View itemView,
                                          TimeLineItemOnClickListener onClickListener,
                                          TimeLineItemOnLongClickListener onLongClickListener) {
        super(itemView, onClickListener, onLongClickListener);

    }

}
