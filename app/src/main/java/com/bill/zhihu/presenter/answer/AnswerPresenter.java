package com.bill.zhihu.presenter.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.common.VoteType;
import com.bill.zhihu.api.bean.response.NoHelpResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.bean.response.ThankResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.databinding.VoteBottomSheetBinding;
import com.bill.zhihu.model.FontSize;
import com.bill.zhihu.model.answer.VoteModel;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.AnimeUtils;
import com.bill.zhihu.util.RichContentUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.tramsun.libs.prefcompat.Pref;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerPresenter {

    private static final int PROGRESS_ANIM_DURATION = 500;

    private static final String TAG = "AnswerPresenter";

    private VoteModel voteModel;
    private Activity activity;
    private AnswerViewBinding answerViewBinding;
    private String answerId;
    private SingleAnswerResponse response;

    public AnswerPresenter(Activity activity, AnswerViewBinding answerViewBinding, String answerId) {
        this.activity = activity;
        this.answerViewBinding = answerViewBinding;
        this.answerId = answerId;
        this.voteModel = new VoteModel();
    }

    public void loadAnswer(String answerId) {
        ZhihuApi.getAnswer(answerId)
                .map(new Func1<SingleAnswerResponse, String>() {
                    @Override
                    public String call(SingleAnswerResponse singleAnswerResponse) {
                        // hold reponse
                        response = singleAnswerResponse;
                        // wrap the answer content
                        String content = RichContentUtils.wrapContent(
                                singleAnswerResponse.content
                                        + RichContentUtils.createTimeTag(singleAnswerResponse.createdTime, singleAnswerResponse.updatedTime)
                                , Theme.LIGHT);
                        // hold vote state
                        voteModel.setRelationship(response.relationship);

                        return RichContentUtils.replaceImage(content);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("load answer completed");
                        stopLoadingAnim();
                        answerViewBinding.expandSelector.setVisibility(View.VISIBLE);
                        AnimeUtils.createScaleShowAnime(answerViewBinding.expandSelector).start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("load answer error");
                        Logger.t(TAG).e(e.toString());
                        stopLoadingAnim();
                    }

                    @Override
                    public void onNext(String content) {
                        answerViewBinding.answer.setContent(content);
                    }
                });
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String title = String.format("【%s】", response.question.title);
        String content = String.format("%s:%s", response.question.author.name, response.excerpt);
        String url = String.format("http://www.zhihu.com/question/%s/answer/%s (分享自知乎)", response.question.id, response.id);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + content + url);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }

    public BottomSheetDialog createVoteBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        VoteBottomSheetBinding bottomSheetBinding = DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.layout_bottom_sheet_vote_up, null, false);
        bottomSheetBinding.setVoteModel(voteModel);
        // set vote state

        bottomSheetBinding.voteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                if (voteModel.isVoteUp.get()) {
                    vote(v, VoteType.CANCEL_VOTE);
                } else {
                    vote(v, VoteType.UP_VOTE);
                }
            }
        });

        bottomSheetBinding.voteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                if (voteModel.isVoteDown.get()) {
                    vote(v, VoteType.CANCEL_VOTE);
                } else {
                    vote(v, VoteType.DOWN_VOTE);
                }
            }
        });
        bottomSheetBinding.thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                if (voteModel.isThanked.get()) {
                    cancelThanks(v);
                } else {
                    thanks(v);
                }
            }
        });
        bottomSheetBinding.nohelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                if (voteModel.isNohelp.get()) {
                    cancelNohelp(v);
                } else {
                    nohelp(v);
                }
            }
        });
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());
        return bottomSheetDialog;
    }

    private void vote(final View view, int voteType) {
        ZhihuApi.vote(answerId, voteType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VoteResponse>() {
                    @Override
                    public void onCompleted() {
                        view.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.t(TAG).e(e.toString());
                        view.setClickable(true);
                    }

                    @Override
                    public void onNext(VoteResponse voteResponse) {
                        answerViewBinding.vote.setText(voteResponse.voteup_count + "");
                        voteModel.setVoteType(voteResponse.voteType);

                        if (voteResponse.voteType == VoteType.UP_VOTE) {
                            answerViewBinding.vote.setChecked(true);
                            answerViewBinding.voteTxt.setChecked(true);
                        } else {
                            answerViewBinding.vote.setChecked(false);
                            answerViewBinding.voteTxt.setChecked(false);
                        }
                    }
                });
    }

    public void nohelp(final View view) {
        System.out.println("no help");
        ZhihuApi.nohelp(answerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NoHelpResponse>() {
                    @Override
                    public void onCompleted() {
                        view.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.t(TAG).e(e.toString());
                        view.setClickable(true);
                    }

                    @Override
                    public void onNext(NoHelpResponse noHelpResponse) {
                        System.out.println(noHelpResponse.isNohelp);
                        voteModel.isNohelp.set(noHelpResponse.isNohelp);
                    }
                });
    }

    public void cancelNohelp(final View view) {
        ZhihuApi.cancelNohelp(answerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NoHelpResponse>() {
                    @Override
                    public void onCompleted() {
                        view.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setClickable(true);
                        Logger.t(TAG).e(e.toString());
                    }

                    @Override
                    public void onNext(NoHelpResponse noHelpResponse) {
                        voteModel.isNohelp.set(noHelpResponse.isNohelp);
                    }
                });
    }

    public void thanks(final View view) {
        ZhihuApi.thanks(answerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThankResponse>() {
                    @Override
                    public void onCompleted() {
                        view.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.t(TAG).e(e.toString());
                        view.setClickable(true);
                    }

                    @Override
                    public void onNext(ThankResponse thankResponse) {
                        voteModel.isThanked.set(thankResponse.isThanked);
                    }
                });
    }

    public void cancelThanks(final View view) {
        ZhihuApi.cancelThanks(answerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThankResponse>() {
                    @Override
                    public void onCompleted() {
                        view.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setClickable(true);
                        Logger.t(TAG).e(e.toString());
                    }

                    @Override
                    public void onNext(ThankResponse thankResponse) {
                        voteModel.isThanked.set(thankResponse.isThanked);
                    }
                });
    }

    public void setAuthor(Author author) {
        String avatarUrl = author.avatarUrl.replace("_s", "_l");
        ImageLoader.getInstance().displayImage(avatarUrl, answerViewBinding.avatar);
        answerViewBinding.name.setText(author.name);
        answerViewBinding.intro.setText(author.headline);
    }

    /**
     * chaneg the content font size
     *
     * @param fontSize {@link com.bill.zhihu.model.FontSize}
     */
    public void setAnswerFontSize(String fontSize) {
        answerViewBinding.answer.setFontSize(fontSize);
        Pref.putString(FontSize.RICH_CONTENT_VIEW_FONT_KEY, fontSize);
    }

    public void setVoteupCount(String voteupCount) {
        answerViewBinding.vote.setText(voteupCount);
    }

    /**
     * webview加载动画
     */
    public void playLoadingAnim() {
        answerViewBinding.loadingImg.spin();
    }

    /**
     * 加载动画消失
     */
    private void stopLoadingAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(answerViewBinding.loadingImg, "alpha", 1, 0);
        animator.setDuration(PROGRESS_ANIM_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                answerViewBinding.loadingImg.stopSpinning();
            }
        });
        animator.start();
    }

}
