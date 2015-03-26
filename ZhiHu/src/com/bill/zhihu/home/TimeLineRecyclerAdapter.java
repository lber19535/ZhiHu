package com.bill.zhihu.home;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.bean.TimeLineItem.ContentType;
import com.bill.zhihu.api.bean.TimeLineItem.SourceType;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * recycler的适配器
 * 
 * @author Bill Lv
 *
 */
public class TimeLineRecyclerAdapter extends Adapter<TimeLineViewHolder> {

    private static final String TAG = "TimeLineRecyclerAdapter";

    // 只有问题的item和问题+答案预览
    // private static final int VIEW_TYPE_COUNT = 2;

    private static final int VIEW_TYPE_ONLY_QUESTION = 0x00;
    private static final int VIEW_TYPE_ANSWER_QUESTION = 0x01;

    private List<TimeLineItem> timelineItems;
    private LayoutInflater mInflater;

    public TimeLineRecyclerAdapter(Context context,
            List<TimeLineItem> timelineItems) {
        this.timelineItems = timelineItems;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TimeLineItem item = timelineItems.get(position);
        String sourceText = item.getSourceText();
        String source = item.getSource();
        int start = 0;
        int end = 0;
        if (item.getSourceType() == SourceType.LEFT) {
            start = 0;
            end = source.length();
        } else {
            if (source.contains(" ")) {
                start = sourceText.indexOf(source.split(" ")[0]);
            } else {
                start = sourceText.indexOf(source);
            }
            end = sourceText.length();
        }
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.CYAN);
        SpannableStringBuilder ssb = new SpannableStringBuilder(sourceText);
        ssb.setSpan(fcs, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        holder.fromTv.setText(ssb);
        holder.questionTv.setText(item.getQuestion());

        int type = getItemViewType(position);
        ZhihuLog.dValue(TAG, "type", type);
        switch (type) {
        case VIEW_TYPE_ONLY_QUESTION:
            TimeLineOnlyQuestionViewHolder questionViewHolder = (TimeLineOnlyQuestionViewHolder) holder;

            break;
        case VIEW_TYPE_ANSWER_QUESTION:
            TimeLineWithAnswerViewHolder answerViewHolder = (TimeLineWithAnswerViewHolder) holder;

            break;

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
        // TODO Auto-generated method stub
        super.onViewRecycled(holder);
        System.out.println("onViewRecycled");
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup container,
            int viewType) {
        View itemView = null;
        TimeLineViewHolder holder = null;
        switch (viewType) {
        case VIEW_TYPE_ONLY_QUESTION:
            itemView = mInflater.inflate(R.layout.list_item_only_question,
                    container, false);
            holder = new TimeLineOnlyQuestionViewHolder(itemView);
            break;
        case VIEW_TYPE_ANSWER_QUESTION:
            itemView = mInflater.inflate(
                    R.layout.list_item_question_with_answer, container, false);
            holder = new TimeLineWithAnswerViewHolder(itemView);
            break;

        default:
            break;
        }
        return holder;
    }

}
