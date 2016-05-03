package com.bill.zhihu.presenter.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.bean.response.PeopleBasicResponse;
import com.bill.zhihu.constant.AnimeConstant;
import com.bill.zhihu.databinding.HomePageViewBinding;
import com.bill.zhihu.ui.home.DrawerInfoCallback;
import com.bill.zhihu.ui.home.TimeLineRecyclerAdapter;
import com.bill.zhihu.util.ImageLoadUtils;
import com.bill.zhihu.util.ImageUrlUtils;
import com.bill.zhihu.view.SwipyRefreshLayoutDirection;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class HomePresenter {

    private static final String TAG = "HomePresenter";

    private HomePageViewBinding binding;
    private Activity activity;

    private List<FeedsItem> timelineItems;
    private TimeLineRecyclerAdapter adapter;

    private DrawerInfoCallback callback;

    public HomePresenter(HomePageViewBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        this.timelineItems = new ArrayList<>();
        this.adapter = new TimeLineRecyclerAdapter(activity, timelineItems);

        binding.timeLineList.setAdapter(adapter);
    }

    public void setCallback(DrawerInfoCallback callback) {
        this.callback = callback;
    }

    /**
     * bind in layout
     *
     * @param v
     */
    public void onClickUptoTop(View v) {
        binding.timeLineList.smoothScrollToPosition(0);
    }

    /**
     * bind in layout
     *
     * @param direction
     */
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (timelineItems.isEmpty()) {
            binding.swipeToRefresh.setRefreshing(false);
            return;
        }
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            loadHomePage();
        } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            loadMore();
        }
    }

    /**
     * 获取首页
     */
    public void loadHomePage() {
        ZhihuApi.loadHomePage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FeedsResponse>() {
                    @Override
                    public void onCompleted() {
                        if (binding.swipeToRefresh.isRefreshing())
                            binding.swipeToRefresh.setRefreshing(false);
                        else
                            stopLoadingAnim();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.t(TAG).d("load home page failed");
                        Logger.t(TAG).d(e.toString());
                    }

                    @Override
                    public void onNext(FeedsResponse feedsResponse) {
                        timelineItems.addAll(feedsResponse.items);
                        adapter.notifyDataSetChanged();

                        Logger.t(TAG).d("load home success");
                    }
                });
    }

    /**
     * 首页初始加载动画
     */
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

    /**
     * 上拉加载更多
     */
    public void loadMore() {

        if (timelineItems.isEmpty())
            return;

        ZhihuApi.loadMore(timelineItems.get(timelineItems.size() - 1).id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FeedsResponse>() {
                    @Override
                    public void onCompleted() {
                        stopLoadingAnim();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.t(TAG).d("load more home page failed");
                        Logger.t(TAG).d(e.toString());
                    }

                    @Override
                    public void onNext(FeedsResponse feedsResponse) {
                        int targetPosition = timelineItems.size();
                        timelineItems.addAll(feedsResponse.items);
                        adapter.notifyDataSetChanged();
                        binding.swipeToRefresh.setRefreshing(false);
                        binding.timeLineList.smoothScrollToPosition(targetPosition);
                    }
                });
    }

    public void loadPeopleInfo() {
        ZhihuApi.getPeopleSelfBasic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PeopleBasicResponse>() {
                               @Override
                               public void onCompleted() {
                                   Logger.t(TAG).d("people info load complete");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Logger.t(TAG).e(e.toString());
                               }

                               @Override
                               public void onNext(PeopleBasicResponse peopleBasicResponse) {
                                   callback.setHeadLine(peopleBasicResponse.headline);
                                   callback.setHeadName(peopleBasicResponse.name);
                                   String avatarHDUrl = ImageUrlUtils.avatarSmall2Normal(peopleBasicResponse.avatarUrl);
                                   ImageLoadUtils.getImageDrawable(avatarHDUrl)
                                           .observeOn(AndroidSchedulers.mainThread())
                                           .subscribe(new Subscriber<Drawable>() {
                                               @Override
                                               public void onCompleted() {
                                                   Logger.t(TAG).d("laod avatar img complete");
                                               }

                                               @Override
                                               public void onError(Throwable e) {
                                                   Logger.t(TAG).e(e.toString());
                                               }

                                               @Override
                                               public void onNext(Drawable drawable) {
                                                   callback.setHeadImage(drawable);
                                               }
                                           });
                               }
                           }

                );
    }
}
