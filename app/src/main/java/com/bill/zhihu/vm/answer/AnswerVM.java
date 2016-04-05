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
import com.tencent.bugly.crashreport.BuglyLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

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

    private static final String TAG = "AnswerVM";

    private AnswerModel model;
    private Activity activity;
    private AnswerViewBinding binding;

    public AnswerVM(Activity activity, AnswerViewBinding binding) {
        this.activity = activity;
        this.binding = binding;
        this.model = new AnswerModel();
        binding.answer.bindJs(this, "ZhihuAndroid");
    }

    public void loadAnswer(String id) {
        model.getAnswer(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("load answer completed");
                        stopLoadingAnim();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("load answer error");
                        BuglyLog.d(TAG, e.toString());
                    }

                    @Override
                    public void onNext(String content) {
                        binding.answer.setContent(content);
                    }
                });
    }

    public void setAuthor(FeedsItem item) {
        String avatarUrl = item.target.author.avatarUrl.replace("_s", "_l");
        binding.avatar.setImageURI(Uri.parse(avatarUrl));
        binding.name.setText(item.target.author.name);
        binding.intro.setText(item.target.author.headline);
        binding.vote.setText(item.target.voteupCount + "");
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

    public void changeWebviewFontSize(String size) {
        binding.answer.executeJsMethod("setFontSize", size);
    }

    @JavascriptInterface
    public void loadImage(final String url) {

        model.getImageCacheUri(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        BuglyLog.d(TAG, "image load complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        BuglyLog.d(TAG, e.toString());
                        binding.answer.executeJsMethod("onImageLoadingFailed", url);
                    }

                    @Override
                    public void onNext(String uri) {
                        BuglyLog.d(TAG, "load image " + url + " in cache " + uri);
                        try {
                            binding.answer.executeJsMethod("onImageLoadingComplete", URLEncoder.encode(url, "utf-8"), uri);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @JavascriptInterface
    public String getFontSize() {
        return "14";
    }

    @JavascriptInterface
    public void openImage(String url) {
        Logger.d(url);
    }

    @JavascriptInterface
    public void debug(String msg) {
        Logger.d(msg);
    }

}
