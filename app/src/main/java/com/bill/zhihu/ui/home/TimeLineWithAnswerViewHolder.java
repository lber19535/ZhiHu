package com.bill.zhihu.ui.home;

import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

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


    private void initView(View itemView) {
        answerTv = (TextView) itemView.findViewById(R.id.answer);
        voteTv = (TextView) itemView.findViewById(R.id.vote);
    }

    public void setOnAnswerClickListener(OnClickListener listener) {
        answerTv.setOnClickListener(listener);
    }

}
