package com.bill.zhihu.question;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.bill.zhihu.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 问题界面的问题详情，作为list中的第一项
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class QuestionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.question_title)
    TextView answerTv;    // 问题标题
    @Bind(R.id.question_detail)
    WebView questionDetailWv;    // 问题详情

    public QuestionViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

    }

    @OnClick(R.id.question_title)
    public void onAnswerTitleClick(View v) {

    }

}
