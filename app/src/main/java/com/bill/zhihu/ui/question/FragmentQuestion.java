package com.bill.zhihu.ui.question;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.constant.ColorConstant;
import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.databinding.QuestionViewBinding;
import com.bill.zhihu.ui.answer.ExpandSelectorBehavior;
import com.bill.zhihu.ui.answer.ExpandSelectorCallback;
import com.bill.zhihu.util.AnimeUtils;
import com.bill.zhihu.vm.QuestionVM;
import com.karumi.expandableselector.ExpandableItem;
import com.karumi.expandableselector.OnExpandableItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题界面 包括问题列表、问题、问题详情、话题
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class FragmentQuestion extends Fragment {

    private QuestionViewBinding binding;
    private QuestionVM vm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        FeedsItem item = getActivity().getIntent().getParcelableExtra(IntentConstant.INTENT_NAME_FEEDS_ITEM);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        if (item.target.question != null)
            vm = new QuestionVM(binding, getActivity(), item.target.question.id);
        else
            vm = new QuestionVM(binding, getActivity(), item.target.id);

        binding.setVm(vm);

        initView();

        vm.playLoadingAnim();
        vm.loadQuestion();
//        vm.loadAnswers();

        return binding.getRoot();
    }

    private void initView() {
        // 设置layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        binding.answerList.setLayoutManager(layoutManager);
        // divider
        binding.answerList.addItemDecoration(new QuestionItemDecoration());
        binding.answerList.setHasFixedSize(true);

        binding.swipeToRefresh.setColorSchemeResources(ColorConstant.SWIPE_COLOR_SCHEMA);

        final List<ExpandableItem> expandableItems = new ArrayList<>();
        ExpandableItem plusItem = new ExpandableItem();
        plusItem.setResourceId(R.drawable.ic_plus);
        expandableItems.add(plusItem);
        ExpandableItem voteItem = new ExpandableItem();
        voteItem.setResourceId(R.drawable.ic_message_white);
        expandableItems.add(voteItem);
        ExpandableItem shareItem = new ExpandableItem();
        shareItem.setResourceId(R.drawable.ic_share_white);
        expandableItems.add(shareItem);
        ExpandableItem starItem = new ExpandableItem();
        starItem.setResourceId(R.drawable.ic_visibility_white);
        expandableItems.add(starItem);

        binding.expandSelector.showExpandableItems(expandableItems);
        binding.expandSelector.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(int index, View view) {
                switch (index){
                    case 0:
                    case 1:
                    case 2:
                    case 4:
                }
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.fab.getLayoutParams();
        ExpandSelectorBehavior behavior = (ExpandSelectorBehavior) params.getBehavior();
        behavior.setCallback(new ExpandSelectorCallback() {

            @Override
            public void onHide() {
                if (!isHide()) {
                    if (binding.expandSelector.isExpanded()) {
                        binding.expandSelector.collapse();
                    }
                    AnimeUtils.createScaleHideAnime(binding.expandSelector).start();
                }
            }

            @Override
            public void onShow() {
                if (isHide()) {
                    AnimeUtils.createScaleShowAnime(binding.expandSelector).start();
                }
            }
        });
    }

}
