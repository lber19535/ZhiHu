package com.bill.zhihu.vm.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.model.AnswerModel;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichCcontentUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

import java.net.URLEncoder;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerVM {

    // option菜单展开和收起时icon的角度
    private static final float NORMAL_OPTION_IC_DEGREE = 0;
    private static final float EXPAND_OPTION_IC_DEGREE = 45;
    private static final float EXPAND_OPTION_IC_ALPHA = 0.5f;
    private static final float NORMAL_OPTION_IC_ALPHA = 1f;
    private static final long OPTION_ANIM_TIME = 200;
    private static final int PROGRESS_ANIM_DURATION = 500;

    private AnswerModel model;
    private Activity activity;
    private AnswerViewBinding binding;

    public AnswerVM(Activity activity, AnswerViewBinding binding) {
        this.activity = activity;
        this.binding = binding;
        this.model = new AnswerModel();
        binding.answer.bindJs(this, "ZhihuAndroid");
    }

    public void loadAnswer(long id) {
        model.getAnswer(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SingleAnswerResponse>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("load answer completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("load answer error");
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(SingleAnswerResponse singleAnswerResponse) {
                        binding.answer.setContent(RichCcontentUtils.wrapContent(singleAnswerResponse.content, Theme.LIGHT));
                        stopLoadingAnim();
                    }
                });
    }

    public void setAuthor(FeedsItem item) {
        String avatarUrl = item.target.author.avatarUrl.replace("_s", "_l");
        binding.avatar.setImageURI(Uri.parse(avatarUrl));
        binding.name.setText(item.target.author.name);
        binding.intro.setText(item.target.author.headline);
    }

    /**
     * webview加载动画
     */
    public void playLoadingAnim() {
        binding.loadingImg.spin();
    }

    /**
     * 加载动画消失
     */
    private void stopLoadingAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.loadingImg, "alpha", 1, 0);
        animator.setDuration(PROGRESS_ANIM_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                binding.loadingImg.stopSpinning();
            }
        });
        animator.start();
    }

    @JavascriptInterface
    public void loadImage(String url){
        Logger.d(url);
    }

    @JavascriptInterface
    public String getFontSize(){
        return "14";
    }
}
