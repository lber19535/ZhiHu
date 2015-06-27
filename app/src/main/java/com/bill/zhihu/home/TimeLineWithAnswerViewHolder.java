package com.bill.zhihu.home;

import android.view.View;
import android.widget.TextView;

import com.bill.zhihu.R;

/**
 * 显示问题+答案摘要的item
 *
 * @author Bill Lv
 */
public class TimeLineWithAnswerViewHolder extends TimeLineViewHolder {

    public TextView answerTv;
    public TextView voteTv;

    public TimeLineWithAnswerViewHolder(View itemView) {
        super(itemView);
        initView(itemView);

    }

    public TimeLineWithAnswerViewHolder(View itemView,
                                        TimeLineItemOnClickListener onClickListener,
                                        TimeLineItemOnLongClickListener onLongClickListener) {
        super(itemView, onClickListener, onLongClickListener);
        initView(itemView);
    }

    private void initView(View itemView) {
        answerTv = (TextView) itemView.findViewById(R.id.answer);
        voteTv = (TextView) itemView.findViewById(R.id.vote);
    }

}
