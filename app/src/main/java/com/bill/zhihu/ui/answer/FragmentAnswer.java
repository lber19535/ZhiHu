package com.bill.zhihu.ui.answer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.databinding.PopWindowFabViewBinding;
import com.bill.zhihu.model.AnswerModel;
import com.bill.zhihu.util.FeedsItemUtils;
import com.bill.zhihu.util.IntentUtils;
import com.bill.zhihu.view.RichContentView;
import com.bill.zhihu.vm.answer.AnswerVM;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * 答案
 * <p/>
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment {

    private static final String TAG = "FragmentAnswer";
    // option菜单展开和收起时icon的角度
    private static final float NORMAL_OPTION_IC_DEGREE = 0;
    private static final float EXPAND_OPTION_IC_DEGREE = 45;
    private static final float EXPAND_OPTION_IC_ALPHA = 0.5f;
    private static final float NORMAL_OPTION_IC_ALPHA = 1f;
    private static final long OPTION_ANIM_TIME = 200;

    // 加号弹出窗
    private PopupWindow fabWindow;

    private AnswerViewBinding binding;
    private AnswerVM vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_answer, container, false);
        initView();

        Intent intent = getActivity().getIntent();
        FeedsItem item = intent.getParcelableExtra(IntentUtils.ITENT_NAME_FEEDS_ITEM);

        ZhihuLog.d(TAG, "answer id is " + item.target.id);

        vm = new AnswerVM(getActivity(),binding);

        vm.playLoadingAnim();
        vm.setAuthor(item);
        vm.loadAnswer(item.target.id);
        return binding.getRoot();
    }

    private void initView() {

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 悬浮按钮打开和关闭的动画
                ObjectAnimator plusToClose = ObjectAnimator.ofFloat(binding.fab, "rotation", NORMAL_OPTION_IC_DEGREE, EXPAND_OPTION_IC_DEGREE);
                plusToClose.setDuration(OPTION_ANIM_TIME);
                ObjectAnimator closeToTrans = ObjectAnimator.ofFloat(binding.fab, "alpha", NORMAL_OPTION_IC_ALPHA, EXPAND_OPTION_IC_ALPHA);
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

        // 加号弹出窗
        PopWindowFabViewBinding fabViewBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.popup_window_answer_fab, null, false);
        fabViewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 分享
        fabViewBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZhihuLog.dFlag(TAG, "share start");
                fabWindow.dismiss();

                // 分享答案
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                StringBuffer sb = new StringBuffer();
                sb.append("我在知乎看到了这个回答很有趣，分享给你\n");
//                sb.append("【" + questionTitle + "：" + answerUrl + "】\n");
                sb.append("----来自刘看山");
                intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                intent.putExtra(Intent.EXTRA_TITLE, "分享");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "请选择"));

//                ZhihuLog.dValue(TAG, "question title", questionTitle);
//                ZhihuLog.dValue(TAG, "answer Url", answerUrl);
                ZhihuLog.dFlag(TAG, "share end");
            }
        });
        // 收藏
        fabViewBinding.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 评论
        fabViewBinding.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 赞同
        fabViewBinding.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 感谢
        fabViewBinding.thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });
        // 没有帮助
        fabViewBinding.noHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabWindow.dismiss();
            }
        });

        this.fabWindow = new PopupWindow(binding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.fabWindow.setBackgroundDrawable(new BitmapDrawable());   //点击外部会让popwindow消失
        this.fabWindow.setOutsideTouchable(true);
        this.fabWindow.setAnimationStyle(R.style.PopWindowAnswerAnim);
        this.fabWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popwindow消失的时候还原option按钮
                ObjectAnimator closeToPlus = ObjectAnimator.ofFloat(binding.fab, "rotation", EXPAND_OPTION_IC_DEGREE, NORMAL_OPTION_IC_DEGREE);
                closeToPlus.setDuration(OPTION_ANIM_TIME);
                ObjectAnimator closeToNormal = ObjectAnimator.ofFloat(binding.fab, "alpha", EXPAND_OPTION_IC_ALPHA, NORMAL_OPTION_IC_ALPHA);
                closeToNormal.setDuration(OPTION_ANIM_TIME);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(closeToNormal, closeToPlus);
                animatorSet.start();
            }
        });
        // attach到自定义的webview上，加入滑动过程中的隐藏和现实
        binding.fab.attachToScrollView(binding.answer);
        // bind js
        binding.answer.bindJs(vm,"ZhihuAndroid");

    }

//    /**
//     * webview加载动画
//     */
//    private void playLoadingAnim() {
//        progressWheel.spin();
//    }
//
//    /**
//     * 加载动画消失
//     */
//    private void stopLoadingAnim() {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(progressWheel, "alpha", 1, 0);
//        animator.setDuration(PROGRESS_ANIM_DURATION);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                progressWheel.stopSpinning();
//            }
//        });
//        animator.start();
//    }

    //    @Override
//    public void callBack(AnswerContent content) {
//        vote.setText(content.getVote());
//        intro.setText(content.getIntro());
//        name.setText(content.getPeopleName());
//        answerWv.getWebView().getSettings().setJavaScriptEnabled(false);
//        // 加载css
//        answerWv.getWebView().loadDataWithBaseURL("file:///android_asset/", content.getAnswer(), "text/html; charset=UTF-8", null, null);
//
////        CmdLoadAvatarImage setAvatarImageUrl = new CmdLoadAvatarImage(content.getAvatarImgUrl());
////        setAvatarImageUrl.setOnCmdCallBack(this);
////        ZhihuApi.execCmd(setAvatarImageUrl);
//
//        stopLoadingAnim();
//
//    }

    //    @Override
//    public void callback(Bitmap captchaImg) {
//        avatar.setImageBitmap(captchaImg);
//    }
}
