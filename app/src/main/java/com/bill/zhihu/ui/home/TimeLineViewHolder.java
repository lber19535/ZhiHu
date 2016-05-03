package com.bill.zhihu.ui.home;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;

/**
 * item之间相同的东西(来源，avatar，问题)放到这个父类中
 *
 * @author Bill Lv
 */
public class TimeLineViewHolder extends ViewHolder {

    private static final String TAG = "TimeLineViewHolder";

    public TextView questionTv;
    public TextView fromTv;
    public ImageView avatarIv;

    protected TimeLineItemOnClickListener onClickListener;
    protected TimeLineItemOnLongClickListener onLongClickListener;

    public TimeLineViewHolder(View itemView) {
        super(itemView);
        if (itemView != null && itemView.getVisibility() != View.GONE) {
            questionTv = (TextView) itemView.findViewById(R.id.question);
            fromTv = (TextView) itemView.findViewById(R.id.from);
            avatarIv = (ImageView) itemView.findViewById(R.id.header);
        }
    }


    /**
     * 加载图片，这里是加载avatar的图片
     *
     * @param url
     */
    public void setAvatarImageUrl(String url) {
        Logger.d("avatar img url", url);
        ImageLoader.getInstance().displayImage(url, avatarIv);

    }

    public void cancelImageLoad() {
//        avatarImage.cancel();
    }

    /**
     * 由于avatar和from点击实现的效果一样所以用了同一个listener
     *
     * @param listener
     */
    public void setOnFromOrAvatarClickListener(OnClickListener listener) {
        fromTv.setOnClickListener(listener);
        avatarIv.setOnClickListener(listener);
    }

    /**
     * 点击问题触发的事件
     *
     * @param listener
     */
    public void setOnQuestionClickListener(OnClickListener listener) {
        questionTv.setOnClickListener(listener);
    }

}
