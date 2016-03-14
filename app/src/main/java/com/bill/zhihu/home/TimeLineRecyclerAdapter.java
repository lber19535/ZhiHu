package com.bill.zhihu.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.answer.ActivityAnswer;
import com.bill.zhihu.api.bean.FeedsItem;
import com.bill.zhihu.api.bean.FeedsItemVerb;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.utils.TextUtils;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.question.ActivityQuestion;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * recycler的适配器
 *
 * @author Bill Lv
 */
public class TimeLineRecyclerAdapter extends Adapter<TimeLineViewHolder> {

    private static final String TAG = "TimeLineRecyclerAdapter";

    // 只有问题的item和问题+答案预览
    // private static final int VIEW_TYPE_COUNT = 2;

    private static final int VIEW_TYPE_ONLY_QUESTION = 0x01;
    private static final int VIEW_TYPE_ANSWER_QUESTION = 0x02;

    private List<FeedsItem> timelineItems;
    private LayoutInflater mInflater;

    private Context mContext;

    public TimeLineRecyclerAdapter(Context context,
                                   List<FeedsItem> timelineItems) {
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
        switch (timelineItems.get(position).verb) {
            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
            case FeedsItemVerb.PROMOTION_ANSWER:
            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
            case FeedsItemVerb.VOTE_UP_ANSWER:
            case FeedsItemVerb.VOTE_UP_ARTICLE:
                return VIEW_TYPE_ANSWER_QUESTION;
            case FeedsItemVerb.MEMBER_ASK_QUESTION:
            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                return VIEW_TYPE_ONLY_QUESTION;
            default:
                Logger.t(TAG).d("new type " + timelineItems.get(position).verb + " maybe cause crash");
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        final FeedsItem item = timelineItems.get(position);
        // source string, before the title
        holder.fromTv.setText(formatSourceString(item));

        // question or article title in target or question, avatar image
        switch (timelineItems.get(position).verb) {

            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
                holder.questionTv.setText(item.target.question.title);
                holder.setAvatarImageUrl(item.target.author.avatarUrl);
                break;
            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.PROMOTION_ANSWER:
                holder.questionTv.setText(item.target.title);
                break;
            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
                holder.questionTv.setText(item.target.question.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.VOTE_UP_ANSWER:
                holder.questionTv.setText(item.target.question.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.VOTE_UP_ARTICLE:
                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.MEMBER_ASK_QUESTION:

                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
                holder.questionTv.setText(item.target.title);
                holder.setAvatarImageUrl(item.actors.get(0).avatarUrl);
                break;
            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
                holder.questionTv.setText(item.target.column.title);
                holder.setAvatarImageUrl(item.target.author.avatarUrl);
                break;
            default:
                Logger.t(TAG).d("Time line adapter, none of specific verb ", timelineItems.get(position).verb);
                break;
        }

        int type = getItemViewType(position);

        Logger.t(TAG).d("type", type);

        // load source picture of people
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

                Logger.t(TAG).d(TAG, "answerViewHolder " + answerViewHolder);
                Logger.t(TAG).d(TAG, "answerTv " + answerViewHolder.answerTv);
                Logger.t(TAG).d(TAG, "item.getAnswerSummary() " + item.target.excerpt);

                answerViewHolder.answerTv.setText(item.target.excerpt);
                answerViewHolder.voteTv.setText(TextUtils.getSummaryNumber(item.target.voteupCount));

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

    private CharSequence formatSourceString(FeedsItem item) {
        int highLightTextColor = mContext.getResources().getColor(R.color.blue_light);

        String sourceText;
        String highLightText;
        switch (item.verb) {
            case FeedsItemVerb.COLUMN_POPULAR_ARTICLE:
                highLightText = item.target.column.title;
                sourceText = highLightText + " " + "中很多人赞同了该文章";
                break;
            case FeedsItemVerb.MEMBER_ANSWER_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "回答了该问题";
                break;
            case FeedsItemVerb.MEMBER_ASK_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "提了一个问题";
                break;
            case FeedsItemVerb.MEMBER_CREATE_ARTICLE:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "发布了文章";
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_COLUMN:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "关注了专栏";
                break;
            case FeedsItemVerb.MEMBER_FOLLOW_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "关注了该问题";
                break;
            case FeedsItemVerb.PROMOTION_ANSWER:
                highLightText = "";
                sourceText = "热门回答";
                break;
            case FeedsItemVerb.TOPIC_ACKNOWLEDGED_ANSWER:
                highLightText = item.actors.get(0).name;
                sourceText = "来自" + " " + highLightText;
                break;
            case FeedsItemVerb.TOPIC_POPULAR_QUESTION:
                highLightText = item.actors.get(0).name;
                sourceText = "来自" + " " + highLightText;
                break;
            case FeedsItemVerb.VOTE_UP_ANSWER:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "赞同了该回答";
                break;
            case FeedsItemVerb.VOTE_UP_ARTICLE:
                highLightText = item.actors.get(0).name;
                sourceText = highLightText + " " + "赞同了该文章";
                break;
            default:
                sourceText = "";
                highLightText = "";
                break;
        }

        Logger.t(TAG).d("source text", sourceText);
        Logger.t(TAG).d("high Light Text", highLightText);

        SpannableStringBuilder ssb = TextUtils.getColorString(sourceText,
                highLightTextColor, highLightText);

        return ssb;
    }


}
