package com.bill.zhihu.ui.question;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.ui.answer.ActivityAnswer;
import com.bill.zhihu.api.bean.AnswerItemInQuestion;
import com.bill.zhihu.api.bean.QuestionContent;
import com.bill.zhihu.api.utils.TextUtils;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * 问题页面的adapter
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class QuestionRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QuestionRecyclerAdapter";

    // 问题详情放在第一个
    private static final int LIST_HEADER_QUESTION_INDEX = 0;

    // view type
    private static final int VIEW_TYPE_QUESTION = 0;
    private static final int VIEW_TYPE_ANSWER = 1;

    // 问题界面的内容 包括问题回答列表等
    private QuestionContent questionContent;

    private Context mContext;


    public QuestionRecyclerAdapter(Context mContext, QuestionContent questionContent) {
        this.questionContent = questionContent;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ANSWER: {
                viewHolder = new QuestionAnswerViewHolder(View.inflate(mContext, R.layout.list_item_question_answer, null));
                break;
            }
            case VIEW_TYPE_QUESTION: {
                viewHolder = new QuestionViewHolder(View.inflate(mContext, R.layout.list_item_question, null));
                break;
            }
            default:
                break;
        }
        if (viewHolder == null) {
            ZhihuLog.d(TAG, "view holder is null, type is " + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // header设置问题详情的item
        if (position == LIST_HEADER_QUESTION_INDEX) {
            QuestionViewHolder viewHolder = (QuestionViewHolder) holder;
            // 问题详情
            viewHolder.questionDetailWv.getSettings().setJavaScriptEnabled(false);
            viewHolder.questionDetailWv.loadDataWithBaseURL("file:///android_asset/", questionContent.getQuestionDetail(), "text/html; charset=UTF-8", null, null);
            ZhihuLog.dValue(TAG, "detail ", questionContent.getQuestionDetail());
            viewHolder.questionDetailWv.reload();
            // 问题标题
            viewHolder.answerTv.setText(questionContent.getQuestionTitle());
        } else {
            QuestionAnswerViewHolder viewHolder = (QuestionAnswerViewHolder) holder;
            final AnswerItemInQuestion item = questionContent.getAnswers().get(position - 1);
            // 头像
            viewHolder.loadAvatarImage(item.getAvatarUrl());
            // 答案缩略
            viewHolder.answerSummaryTv.setText(item.getAnswerSummary());
            // name
            viewHolder.nameTv.setText(item.getPeopleName());
            // 赞同
            viewHolder.voteTv.setText(TextUtils.getSummaryNumber(item.getVoteCount()));

            viewHolder.answerSummaryTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityAnswer.class);
                    intent.setAction(AnswerItemInQuestion.KEY);
                    intent.putExtra(AnswerItemInQuestion.KEY, item);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof QuestionAnswerViewHolder) {
            ((QuestionAnswerViewHolder) holder).headerIv.setImageBitmap(null);
            ((QuestionAnswerViewHolder) holder).cancelImageLoad();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == LIST_HEADER_QUESTION_INDEX)
            return VIEW_TYPE_QUESTION;
        else
            return VIEW_TYPE_ANSWER;
    }

    @Override
    public int getItemCount() {
        // 出了要显示回答列表外，还要在显示一个包含问题详情的item在开头
        return questionContent.getAnswers().size() + 1;
    }
}
