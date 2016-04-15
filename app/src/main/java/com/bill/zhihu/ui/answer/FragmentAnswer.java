package com.bill.zhihu.ui.answer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.util.IntentUtils;
import com.bill.zhihu.vm.answer.AnswerVM;
import com.karumi.expandableselector.ExpandableItem;
import com.karumi.expandableselector.OnExpandableItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 答案
 * <p/>
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment {

    private static final String TAG = "FragmentAnswer";

    private AnswerViewBinding binding;
    private AnswerVM vm;
    private BottomSheetDialog bottomSheetDialog;

    private AnimatorSet showSet = new AnimatorSet();
    private AnimatorSet hideSet = new AnimatorSet();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_answer, container, false);
        initView();

        Intent intent = getActivity().getIntent();
        FeedsItem item = intent.getParcelableExtra(IntentUtils.ITENT_NAME_FEEDS_ITEM);

        ZhihuLog.d(TAG, "answer id is " + item.target.id);

        vm = new AnswerVM(getActivity(), binding);

        vm.playLoadingAnim();
        vm.setAuthor(item);
        vm.loadAnswer(item.target.id);
        return binding.getRoot();
    }

    private void initView() {
        final List<ExpandableItem> expandableItems = new ArrayList<>();
        ExpandableItem plusItem = new ExpandableItem();
        plusItem.setResourceId(R.drawable.ic_plus);
        expandableItems.add(plusItem);
        ExpandableItem voteItem = new ExpandableItem();
        voteItem.setResourceId(R.drawable.ic_vote);
        expandableItems.add(voteItem);
        ExpandableItem shareItem = new ExpandableItem();
        shareItem.setResourceId(R.drawable.ic_share_white);
        expandableItems.add(shareItem);
        ExpandableItem starItem = new ExpandableItem();
        starItem.setResourceId(R.drawable.ic_star);
        expandableItems.add(starItem);

        binding.expandSelector.showExpandableItems(expandableItems);

        binding.expandSelector.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(int index, View view) {
                if (binding.expandSelector.isExpanded())
                    binding.expandSelector.collapse();

                switch (index) {
                    case 1:
                        showVoteBottomSheet();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
            }
        });


        final ObjectAnimator hideAlpha = ObjectAnimator.ofFloat(binding.expandSelector, "alpha", 1, 0);
        final ObjectAnimator showAlpha = ObjectAnimator.ofFloat(binding.expandSelector, "alpha", 0, 1);
        final ObjectAnimator hideScaleX = ObjectAnimator.ofFloat(binding.expandSelector, "scaleX", 1, 0);
        final ObjectAnimator showScaleX = ObjectAnimator.ofFloat(binding.expandSelector, "scaleX", 0, 1);
        final ObjectAnimator hideScaleY = ObjectAnimator.ofFloat(binding.expandSelector, "scaleY", 1, 0);
        final ObjectAnimator showScaleY = ObjectAnimator.ofFloat(binding.expandSelector, "scaleY", 0, 1);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.fab.getLayoutParams();
        ExpandSelectorBehavior behavior = (ExpandSelectorBehavior) params.getBehavior();
        behavior.setCallback(new ExpandSelectorCallback() {

            @Override
            void onHide() {
                if (!isHide()) {
                    if (binding.expandSelector.isExpanded()) {
                        binding.expandSelector.collapse();
                    }
                    hideSet = new AnimatorSet();
                    hideSet.playTogether(hideAlpha, hideScaleX, hideScaleY);
                    hideSet.start();
                }
            }

            @Override
            void onShow() {
                if (isHide()) {
                    showSet = new AnimatorSet();
                    showSet.playTogether(showAlpha, showScaleX, showScaleY);
                    showSet.start();
                }
            }
        });

    }

    private void showVoteBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View dialogContent = getActivity().getLayoutInflater().inflate(R.layout.layout_bottom_sheet_vote_up, null);
        dialogContent.findViewById(R.id.vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("xzxzxzcasdsa");
            }
        });
        bottomSheetDialog.setContentView(dialogContent);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });

        bottomSheetDialog.show();

    }

}
