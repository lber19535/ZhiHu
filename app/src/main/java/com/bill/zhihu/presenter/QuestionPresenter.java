package com.bill.zhihu.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.answer.AnswerItem;
import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.QuestionResponse;
import com.bill.zhihu.constant.AnimeConstant;
import com.bill.zhihu.databinding.QuestionViewBinding;
import com.bill.zhihu.model.question.QuestionItem;
import com.bill.zhihu.ui.question.QuestionRecyclerAdapter;
import com.bill.zhihu.util.ToastUtil;
import com.bill.zhihu.view.SwipyRefreshLayoutDirection;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class QuestionPresenter {

    private QuestionViewBinding binding;
    private Activity activity;
    private QuestionRecyclerAdapter adapter;
    private boolean isAnswersEnd;
    private List<QuestionItem> questionItems;
    private String questionId;
    private QuestionResponse response;

    public QuestionPresenter(QuestionViewBinding binding, Activity activity, String questionId) {
        this.binding = binding;
        this.activity = activity;
        this.questionId = questionId;

        questionItems = new ArrayList<>();
        adapter = new QuestionRecyclerAdapter(activity, questionItems);
        binding.answerList.setAdapter(adapter);

    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String url = String.format("http://www.zhihu.com/question/%s (分享自知乎)", response.id);
        sendIntent.putExtra(Intent.EXTRA_TEXT, response.title + " " + url);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }

    public void loadQuestionAndAnswer() {
        ZhihuApi.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .zipWith(ZhihuApi.getAnswers(questionId).subscribeOn(Schedulers.io()), new Func2<QuestionResponse, AnswersResponse, Void>() {
                    @Override
                    public Void call(QuestionResponse questionResponse, AnswersResponse answersResponse) {
                        ArrayList<QuestionItem> items = new ArrayList<>();

                        QuestionItem detail = new QuestionItem();
                        response = questionResponse;
                        detail.setQuestionResponse(questionResponse);
                        items.add(detail);

                        for (AnswerItem item : answersResponse.data) {
                            QuestionItem newItem = new QuestionItem();
                            newItem.setAnswerItem(item);
                            items.add(newItem);
                        }
                        questionItems.clear();
                        questionItems.addAll(items);

                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        binding.swipeToRefresh.setRefreshing(false);
                        stopLoadingAnim();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(Void question) {
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * bind in layout
     *
     * @param direction
     */
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (questionItems.isEmpty()) {
            binding.swipeToRefresh.setRefreshing(false);
            return;
        }
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            loadQuestionAndAnswer();
        } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            loadMoreAnswers();
        }
    }

    public void loadMoreAnswers() {
        if (isAnswersEnd)
            ToastUtil.showShortToast("没有更多");

        ZhihuApi.getNextAnswers(questionId, questionItems.size()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnswersResponse>() {
                    @Override
                    public void onCompleted() {
                        stopLoadingAnim();
                        Logger.d("load answer complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("load answer error", e.toString());
                    }

                    @Override
                    public void onNext(AnswersResponse answersResponse) {
                        int position = questionItems.size();
                        isAnswersEnd = answersResponse.paging.isEnd;

                        ArrayList<QuestionItem> items = new ArrayList<>();
                        for (AnswerItem item : answersResponse.data) {
                            QuestionItem newItem = new QuestionItem();
                            newItem.setAnswerItem(item);
                            items.add(newItem);
                        }

                        questionItems.addAll(items);

                        adapter.notifyDataSetChanged();
                        binding.swipeToRefresh.setRefreshing(false);
                        binding.answerList.smoothScrollToPosition(position);
                    }
                });
    }

    public void playLoadingAnim() {
        binding.loadingImg.spin();
    }

    public void stopLoadingAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.loadingImg, "alpha", 1, 0);
        animator.setDuration(AnimeConstant.PROGRESS_ANIM_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                binding.loadingImg.stopSpinning();
            }
        });
        animator.start();
    }
}
