package com.bill.zhihu.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdLoadAnswer;
import com.bill.zhihu.api.cmd.CmdLoadAvatarImage;
import com.bill.zhihu.home.TimeLineViewHolder;
import com.bill.zhihu.view.AnswerView;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment implements CmdLoadAnswer.CallBackListener, CmdLoadAvatarImage.CallbackListener {

    // option菜单展开和收起时icon的角度
    private static final float NORMAL_OPTION_IC_DEGREE = 0;
    private static final float EXPAND_OPTION_IC_DEGREE = 45;
    private static final long OPTION_ANIM_TIME = 500;
    private static final int OPTION_MENU_STATE_NORMAL = 0;
    private static final int OPTION_MENU_STATE_EXPAND = 1;

    private int optionMenuState = OPTION_MENU_STATE_NORMAL;

    private View rootView;
    private ImageView avatar;
    private TextView name;
    private TextView intro;
    private TextView vote;
    private AnswerView answerWv;
    private FloatingActionButton optionsFab;
    private ProgressWheel progressWheel;

    private View fabPopupView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer, null);

        initView();

        optionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (optionMenuState == OPTION_MENU_STATE_NORMAL) {
                    optionMenuState = OPTION_MENU_STATE_EXPAND;
                    ObjectAnimator plusToClose = ObjectAnimator.ofFloat(optionsFab, "rotation", NORMAL_OPTION_IC_DEGREE, EXPAND_OPTION_IC_DEGREE);
                    plusToClose.setDuration(OPTION_ANIM_TIME);
                    plusToClose.start();
                } else {
                    optionMenuState = OPTION_MENU_STATE_NORMAL;
                    ObjectAnimator closeToPlus = ObjectAnimator.ofFloat(optionsFab, "rotation", EXPAND_OPTION_IC_DEGREE, NORMAL_OPTION_IC_DEGREE);
                    closeToPlus.setDuration(OPTION_ANIM_TIME);
                    closeToPlus.start();
                }


//                PopupWindow fabWindow = new PopupWindow(fabPopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                fabWindow.setOutsideTouchable(true);
//                fabWindow.showAtLocation(v, Gravity.CENTER, 10, 10);
            }
        });

        Intent intent = getActivity().getIntent();
        TimeLineItem item = intent.getParcelableExtra(TimeLineViewHolder.EXSTRA_ITEM);

        String answerUrl = item.getAnswerUrl().getUrl();

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
        this.progressWheel = (ProgressWheel) rootView.findViewById(R.id.loading_img);
        // 加号弹出窗
        this.fabPopupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_answer_fab, null);
        // attach到自定义的webview上，加入滑动过程中的隐藏和现实
        optionsFab.attachToScrollView(answerWv);

    }

    /**
     * webview加载动画
     */
    private void playLoadingAnim() {
        progressWheel.spin();
    }

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
