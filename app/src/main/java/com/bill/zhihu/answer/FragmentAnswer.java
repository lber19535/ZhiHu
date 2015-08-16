package com.bill.zhihu.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.bean.AnswerItemInQuestion;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdLoadAnswer;
import com.bill.zhihu.api.cmd.CmdLoadAvatarImage;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.view.AnswerView;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * 答案
 * <p/>
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment implements CmdLoadAnswer.CallBackListener, CmdLoadAvatarImage.CallbackListener {

    private static final String TAG = "FragmentAnswer";
    // option菜单展开和收起时icon的角度
    private static final float NORMAL_OPTION_IC_DEGREE = 0;
    private static final float EXPAND_OPTION_IC_DEGREE = 45;
    private static final float EXPAND_OPTION_IC_ALPHA = 0.5f;
    private static final float NORMAL_OPTION_IC_ALPHA = 1f;
    private static final long OPTION_ANIM_TIME = 200;

    private View rootView;
    private ImageView avatar;
    private TextView name;
    private TextView intro;
    private TextView vote;
    private AnswerView answerWv;
    private FloatingActionButton optionsFab;

    // loading icon
    private ProgressWheel progressWheel;
    // 加号弹出窗
    private PopupWindow fabWindow;

    private View fabPopupView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer, null);

        initView();

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        String answerUrl = null;
        switch (action) {
            case TimeLineItem.KEY: {
                TimeLineItem item = intent.getParcelableExtra(TimeLineItem.KEY);
                answerUrl = item.getAnswerUrl().getUrl();
                break;
            }
            case AnswerItemInQuestion.KEY: {
                AnswerItemInQuestion item = intent.getParcelableExtra(AnswerItemInQuestion.KEY);
                answerUrl = item.getAnswerUrl();
                break;
            }
            default:
                break;
        }
        ZhihuLog.d(TAG, "answer url is " + answerUrl);

        ZhihuApi.loadAnswer(answerUrl, this);

        playLoadingAnim();

        return rootView;
    }

    private void initView() {
        this.vote = (TextView) rootView.findViewById(R.id.vote);
        this.intro = (TextView) rootView.findViewById(R.id.intro);
        this.name = (TextView) rootView.findViewById(R.id.name);
        this.avatar = (ImageView) rootView.findViewById(R.id.avatar);
        this.answerWv = (AnswerView) rootView.findViewById(R.id.answer);
        this.optionsFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        this.optionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator plusToClose = ObjectAnimator.ofFloat(optionsFab, "rotation", NORMAL_OPTION_IC_DEGREE, EXPAND_OPTION_IC_DEGREE);
                plusToClose.setDuration(OPTION_ANIM_TIME);
                ObjectAnimator closeToTrans = ObjectAnimator.ofFloat(optionsFab, "alpha", NORMAL_OPTION_IC_ALPHA, EXPAND_OPTION_IC_ALPHA);
                closeToTrans.setDuration(OPTION_ANIM_TIME);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(closeToTrans, plusToClose);
                animatorSet.start();

                if (fabWindow.isShowing()) {
                    fabWindow.dismiss();
                } else {
                    fabWindow.showAtLocation(v, Gravity.NO_GRAVITY, (int) v.getX(), (int) v.getY());
                }
            }
        });
        this.progressWheel = (ProgressWheel) rootView.findViewById(R.id.loading_img);
        // 加号弹出窗
        this.fabPopupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_answer_fab, null);
        this.fabPopupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 分享
        this.fabPopupView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 收藏
        this.fabPopupView.findViewById(R.id.favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 评论
        this.fabPopupView.findViewById(R.id.comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 赞同
        this.fabPopupView.findViewById(R.id.vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 感谢
        this.fabPopupView.findViewById(R.id.thank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 没有帮助
        this.fabPopupView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        this.fabWindow = new PopupWindow(fabPopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.fabWindow.setBackgroundDrawable(new BitmapDrawable());   //点击外部会让popwindow消失
        this.fabWindow.setOutsideTouchable(true);
        this.fabWindow.setAnimationStyle(R.style.PopWindowAnswerAnim);
        this.fabWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popwindow消失的时候还原option按钮
                ObjectAnimator closeToPlus = ObjectAnimator.ofFloat(optionsFab, "rotation", EXPAND_OPTION_IC_DEGREE, NORMAL_OPTION_IC_DEGREE);
                closeToPlus.setDuration(OPTION_ANIM_TIME);
                ObjectAnimator closeToNormal = ObjectAnimator.ofFloat(optionsFab, "alpha", EXPAND_OPTION_IC_ALPHA, NORMAL_OPTION_IC_ALPHA);
                closeToNormal.setDuration(OPTION_ANIM_TIME);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(closeToNormal, closeToPlus);
                animatorSet.start();
            }
        });
        // attach到自定义的webview上，加入滑动过程中的隐藏和现实
        optionsFab.attachToScrollView(answerWv);

    }

    /**
     * webview加载动画
     */
    private void playLoadingAnim() {
        progressWheel.spin();
    }

    /**
     * 加载动画消失
     */
    private void stopLoadingAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(progressWheel, "alpha", 1, 0);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressWheel.stopSpinning();
            }
        });
        animator.start();
    }

    @Override
    public void callBack(AnswerContent content) {
        vote.setText(content.getVote());
        intro.setText(content.getIntro());
        name.setText(content.getPeopleName());
        answerWv.getWebView().getSettings().setJavaScriptEnabled(false);
        // 加载css
        answerWv.getWebView().loadDataWithBaseURL("file:///android_asset/", content.getAnswer(), "text/html; charset=UTF-8", null, null);

        CmdLoadAvatarImage loadAvatarImage = new CmdLoadAvatarImage(content.getAvatarImgUrl());
        loadAvatarImage.setOnCmdCallBack(this);
        ZhihuApi.execCmd(loadAvatarImage);

        stopLoadingAnim();

    }

    @Override
    public void callback(Bitmap captchaImg) {
        avatar.setImageBitmap(captchaImg);
    }
}
