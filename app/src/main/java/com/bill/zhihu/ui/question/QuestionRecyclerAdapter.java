package com.bill.zhihu.ui.question;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.answer.AnswerItem;
import com.bill.zhihu.api.bean.response.QuestionResponse;
import com.bill.zhihu.api.utils.TextUtils;
import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.model.FontSize;
import com.bill.zhihu.model.question.QuestionItem;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.ui.answer.ActivityAnswer;
import com.bill.zhihu.util.IntentUtils;
import com.bill.zhihu.util.RichContentUtils;

import java.util.List;

/**
 * 问题页面的adapter
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class QuestionRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QuestionRecyclerAdapter";

    private static final int QUESTION_DETAIL = 1;
    private static final int ANSWER_ITEM = 2;


    private Context mContext;
    private List<QuestionItem> questionItems;

    public QuestionRecyclerAdapter(Context mContext, List<QuestionItem> answerItems) {
        this.questionItems = answerItems;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ANSWER_ITEM) {
            viewHolder = new QuestionAnswerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_question_answer, parent, false));
        } else if (viewType == QUESTION_DETAIL) {
            viewHolder = new QuestionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_question, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == QUESTION_DETAIL) {
            QuestionViewHolder viewHolder = (QuestionViewHolder) holder;
            QuestionResponse response = questionItems.get(position).getQuestionResponse();
            viewHolder.answerTv.setText(response.title);

            if (response.detail.isEmpty()) {
                viewHolder.questionDetailWv.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.questionDetailWv.setContent(RichContentUtils.replaceImage(RichContentUtils.wrapTopic(response.detail, Theme.LIGHT)));
                viewHolder.questionDetailWv.setFontSize(FontSize.SMALL);
            }


        } else if (getItemViewType(position) == ANSWER_ITEM) {
            QuestionAnswerViewHolder viewHolder = (QuestionAnswerViewHolder) holder;
            final AnswerItem item = questionItems.get(position).getAnswerItem();
            // 头像
            viewHolder.loadAvatarImage(item.author.avatarUrl);
            // 答案缩略
            viewHolder.answerSummaryTv.setText(item.excerpt);
            // name
            viewHolder.nameTv.setText(item.author.name);
            // 赞同
            viewHolder.voteTv.setText(TextUtils.getSummaryNumber(item.voteupCount));
            viewHolder.answerSummaryTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityAnswer.class);
                    intent.setAction(IntentConstant.INTENT_ACTION_ANSWER_INTENT_VALUE);
                    intent.putExtra(IntentConstant.INTENT_NAME_ANSWER_INTENT_VALUE, IntentUtils.convert(item));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return QUESTION_DETAIL;
        } else {
            return ANSWER_ITEM;
        }
    }

//    @Override
//    public void onViewRecycled(RecyclerView.ViewHolder holder) {
//        super.onViewRecycled(holder);
//        if (holder instanceof QuestionAnswerViewHolder) {
//            ((QuestionAnswerViewHolder) holder).headerIv.setImageBitmap(null);
//            ((QuestionAnswerViewHolder) holder).cancelImageLoad();
//        }
//    }

    @Override
    public int getItemCount() {
        // 出了要显示回答列表外，还要在显示一个包含问题详情的item在开头
        return questionItems.size();
    }
}
