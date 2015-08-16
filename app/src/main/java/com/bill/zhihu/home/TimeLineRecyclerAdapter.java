package com.bill.zhihu.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.answer.ActivityAnswer;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TimeLineItem.ContentType;
import com.bill.zhihu.api.utils.TextUtils;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.question.ActivityQuestion;

import java.util.List;

/**
 * recycler的适配器
 *
 * @author Bill Lv
 */
public class TimeLineRecyclerAdapter extends Adapter<TimeLineViewHolder> {

    private static final String TAG = "TimeLineRecyclerAdapter";
    private static final int DEFAULT_HIGH_LIGHT_TEXT_COLOR = ZhihuApp
            .getContext().getResources().getColor(R.color.blue_light);
    private static int highLightTextColor = DEFAULT_HIGH_LIGHT_TEXT_COLOR;

    // 只有问题的item和问题+答案预览
    // private static final int VIEW_TYPE_COUNT = 2;

    private static final int VIEW_TYPE_ONLY_QUESTION = 0x00;
    private static final int VIEW_TYPE_ANSWER_QUESTION = 0x01;

    private List<TimeLineItem> timelineItems;
    private LayoutInflater mInflater;

    private Context mContext;

    public TimeLineRecyclerAdapter(Context context,
                                   List<TimeLineItem> timelineItems) {
        this.timelineItems = timelineItems;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return timelineItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (timelineItems.get(position).getContentType() == ContentType.QUESTION) {
            return VIEW_TYPE_ONLY_QUESTION;
        } else {
            return VIEW_TYPE_ANSWER_QUESTION;
        }
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        final TimeLineItem item = timelineItems.get(position);
        String sourceText = item.getSourceText();
        SpannableStringBuilder ssb = TextUtils.getColorString(sourceText,
                highLightTextColor, item.getSource());

        holder.fromTv.setText(ssb);
        holder.questionTv.setText(item.getQuestion());
        holder.loadAvatarImage(item.getAvatarImgUrl());

        int type = getItemViewType(position);

        ZhihuLog.dValue(TAG, "type", type);

        View.OnClickListener fromOrAvatarListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener questionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 问题界面
                Intent intent = new Intent(mContext, ActivityQuestion.class);
                intent.putExtra(TimeLineItem.KEY, item);
                mContext.startActivity(intent);
            }
        };

        switch (type) {
            // 只有问题或者文章标题
            case VIEW_TYPE_ONLY_QUESTION: {
                TimeLineOnlyQuestionViewHolder questionViewHolder = (TimeLineOnlyQuestionViewHolder) holder;
                questionViewHolder.setOnFromOrAvatarClickListener(fromOrAvatarListener);
                questionViewHolder.setOnQuestionClickListener(questionListener);
                break;
            }
            // 问题+答案，发布的文章
            case VIEW_TYPE_ANSWER_QUESTION: {
                TimeLineWithAnswerViewHolder answerViewHolder = (TimeLineWithAnswerViewHolder) holder;
                ZhihuLog.dValue(TAG, "answerViewHolder", answerViewHolder);
                ZhihuLog.dValue(TAG, "answerTv", answerViewHolder.answerTv);
                ZhihuLog.dValue(TAG, "item.getAnswerSummary()", item.getAnswerSummary());
                answerViewHolder.answerTv.setText(item.getAnswerSummary());
                answerViewHolder.voteTv.setText(TextUtils.getSummaryNumber(item
                        .getVoteCount()));

                answerViewHolder.setOnFromOrAvatarClickListener(fromOrAvatarListener);
                answerViewHolder.setOnQuestionClickListener(questionListener);
                answerViewHolder.setOnAnswerClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ActivityAnswer.class);
                        intent.setAction(TimeLineItem.KEY);
                        intent.putExtra(TimeLineItem.KEY, item);
                        mContext.startActivity(intent);
                    }
                });
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        System.out.println("onAttachedToRecyclerView");
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        // TODO Auto-generated method stub
        super.onDetachedFromRecyclerView(recyclerView);
        System.out.println("onDetachedFromRecyclerView");
    }

    @Override
    public void onViewAttachedToWindow(TimeLineViewHolder holder) {
        // TODO Auto-generated method stub
        super.onViewAttachedToWindow(holder);
        System.out.println("onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(TimeLineViewHolder holder) {
        // TODO Auto-generated method stub
        super.onViewDetachedFromWindow(holder);
        System.out.println("onViewDetachedFromWindow");
    }

    @Override
    public void onViewRecycled(TimeLineViewHolder holder) {
        super.onViewRecycled(holder);
        System.out.println("onViewRecycled");
        holder.avatarIv.setImageBitmap(null);
        holder.cancelImageLoad();
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup container,
                                                 int viewType) {
        View itemView;
        TimeLineViewHolder holder = null;
        switch (viewType) {
            case VIEW_TYPE_ONLY_QUESTION:
                itemView = mInflater.inflate(R.layout.list_item_tl_only_question,
                        container, false);
                holder = new TimeLineOnlyQuestionViewHolder(itemView);
                break;
            case VIEW_TYPE_ANSWER_QUESTION:
                itemView = mInflater.inflate(
                        R.layout.list_item_tl_question_with_answer, container, false);
                holder = new TimeLineWithAnswerViewHolder(itemView);
                break;

            default:
                break;
        }
        return holder;
    }


}
