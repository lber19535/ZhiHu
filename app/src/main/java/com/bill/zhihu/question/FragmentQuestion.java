package com.bill.zhihu.question;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.QuestionContent;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdLoadQuestion;
import com.bill.zhihu.view.SwipyRefreshLayout;
import com.bill.zhihu.view.SwipyRefreshLayoutDirection;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 问题界面 包括问题列表、问题、问题详情、话题
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class FragmentQuestion extends Fragment {

    @Bind(R.id.answer_list)
    RecyclerView answerListRv;
    @Bind(R.id.swipe_to_refresh)
    SwipyRefreshLayout swipeToRefreshBtn;
    @Bind(R.id.loading_img)
    ProgressWheel progressWheel;
    @Bind(R.id.fab)
    FloatingActionButton fabBtn;

    private View rootView;
    private QuestionRecyclerAdapter recyclerAdapter;

    private QuestionContent questionContent = new QuestionContent();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, rootView);

        TimeLineItem item = getActivity().getIntent().getParcelableExtra(TimeLineItem.KEY);

        ZhihuApi.loadQuestionPage(item.getQuestionUrl().getUrl(), new CmdLoadQuestion.CallBackListener() {
            @Override
            public void callBack(QuestionContent content) {
                stopLoadingAnim();
                questionContent.setQuestionDetail(content.getQuestionDetail());
                questionContent.setQuestionTitle(content.getQuestionTitle());
                questionContent.setAnswers(content.getAnswers());
                questionContent.setQuestionId(content.getQuestionId());
                questionContent.setTopics(content.getTopics());
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        // adapter
        recyclerAdapter = new QuestionRecyclerAdapter(getActivity(), questionContent);
        answerListRv.setAdapter(recyclerAdapter);
        // 设置layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        answerListRv.setLayoutManager(layoutManager);
        // divider
        answerListRv.addItemDecoration(new QuestionItemDecoration());

        swipeToRefreshBtn.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeToRefreshBtn.setRefreshing(false);
            }
        });

        fabBtn.attachToRecyclerView(answerListRv);

        playLoadingAnim();

        return rootView;
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

    @OnClick(R.id.fab)
    public void backToTop(View v){
        answerListRv.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
