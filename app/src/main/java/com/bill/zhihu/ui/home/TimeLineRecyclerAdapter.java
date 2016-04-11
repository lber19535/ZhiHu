package com.bill.zhihu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.BuildConfig;
import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.feeds.FeedsItemTargetType;
import com.bill.zhihu.api.bean.feeds.FeedsItemVerb;
import com.bill.zhihu.api.utils.TextUtils;
import com.bill.zhihu.ui.answer.ActivityAnswer;
import com.bill.zhihu.util.FeedsItemUtils;
import com.bill.zhihu.util.IntentUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

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
        return timelineItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        FeedsItem item = timelineItems.get(position);

        // record json in log
        if (BuildConfig.DEBUG) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(item);
                Logger.t(TAG).json(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        switch (item.target.type) {
            case FeedsItemTargetType.ANSWER:
            case FeedsItemTargetType.ARTICLE:
            case FeedsItemVerb.VOTE_UP_ARTICLE:
                return VIEW_TYPE_ANSWER_QUESTION;
            case FeedsItemTargetType.QUESTION:
            case FeedsItemTargetType.COLUMN:
                return VIEW_TYPE_ONLY_QUESTION;
            default:
                Logger.t(TAG).d("new type " + item.target.type + " maybe cause crash");
                CrashReport.postCatchedException(new Throwable("new type " + item.target.type + " maybe cause crash"));
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        final FeedsItem item = timelineItems.get(position);
        // source string, before the title
        holder.fromTv.setText(FeedsItemUtils.formatSourceString(item));

        // question or article title in target or question, avatar image
        holder.questionTv.setText(FeedsItemUtils.getTitle(item));
        holder.setAvatarImageUrl(FeedsItemUtils.getAvatarUrl(item));

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
//                Intent intent = new Intent(mContext, ActivityQuestion.class);
//                intent.putExtra(TimeLineItem.KEY, item);
//                mContext.startActivity(intent);
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
                        intent.putExtra(IntentUtils.ITENT_NAME_FEEDS_ITEM, item);
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
//        System.out.println("onDetachedFromRecyclerView");
    }

    @Override
    public void onViewAttachedToWindow(TimeLineViewHolder holder) {
        // TODO Auto-generated method stub
        super.onViewAttachedToWindow(holder);
//        System.out.println("onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(TimeLineViewHolder holder) {
        // TODO Auto-generated method stub
        super.onViewDetachedFromWindow(holder);
//        System.out.println("onViewDetachedFromWindow");
    }

    @Override
    public void onViewRecycled(TimeLineViewHolder holder) {
        super.onViewRecycled(holder);
//        System.out.println("onViewRecycled");
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
