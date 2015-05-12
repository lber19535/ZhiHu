package com.bill.zhihu.home;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.cmd.CmdFetchAvatarImage;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * item之间相同的东西(来源，avatar，问题)放到这个父类中
 * 
 * @author Bill Lv
 *
 */
public class TimeLineViewHolder extends ViewHolder implements OnClickListener,
        OnLongClickListener {

    private static final String TAG = "TimeLineViewHolder";

    public TextView questionTv;
    public TextView fromTv;
    public ImageView avatarIv;

    protected TimeLineItemOnClickListener onClickListener;
    protected TimeLineItemOnLongClickListener onLongClickListener;

    private CmdFetchAvatarImage avatarImage;

    public TimeLineViewHolder(View itemView) {
        super(itemView);
        questionTv = (TextView) itemView.findViewById(R.id.question);
        fromTv = (TextView) itemView.findViewById(R.id.from);
        avatarIv = (ImageView) itemView.findViewById(R.id.header);

    }

    public TimeLineViewHolder(View itemView,
            TimeLineItemOnClickListener onClickListener,
            TimeLineItemOnLongClickListener onLongClickListener) {
        this(itemView);
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        if (onLongClickListener != null) {
            onLongClickListener.onItemLongClickListener(v, getPosition());
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onItemClickListener(v, getPosition());
        }
    }

    public void loadImage(String url) {
        ZhihuLog.dValue(url, "avatar img url", url);
        avatarImage = new CmdFetchAvatarImage(url);
        avatarImage
                .setOnCmdCallBack(new CmdFetchAvatarImage.CallbackListener() {

                    @Override
                    public void callback(Bitmap captchaImg) {
                        avatarIv.setImageBitmap(captchaImg);
                    }
                });
        avatarImage.exec();

    }

    public void cancelImageLoad() {
        avatarImage.cancel();
    }

}
