package com.bill.zhihu.ui.question;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.utils.ZhihuLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问题界面的答案item
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "QuestionAnswerViewHolder";

    @Bind(R.id.header)
    ImageView headerIv;  // 头像
    @Bind(R.id.vote)
    TextView voteTv;     // 赞同
    @Bind(R.id.name)
    TextView nameTv;   //people name
    @Bind(R.id.answer_summary)
    TextView answerSummaryTv;  // 答案缩略

//    private CmdLoadAvatarImage avatarImage;

    public QuestionAnswerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 加载图片，这里是加载avatar的图片
     *
     * @param url
     */
    public void loadAvatarImage(String url) {
        ZhihuLog.dValue(TAG, "avatar img url", url);
//        avatarImage = new CmdLoadAvatarImage(url);
//        avatarImage
//                .setOnCmdCallBack(new CmdLoadAvatarImage.CallbackListener() {
//
//                    @Override
//                    public void callback(Bitmap captchaImg) {
//                        headerIv.setImageBitmap(captchaImg);
//                    }
//                });
//        avatarImage.exec();

    }

    /**
     * 取消加载，可以用于list滚动的时候取消加载已经看不到的item
     */
    public void cancelImageLoad() {
//        avatarImage.cancel();
    }

}
