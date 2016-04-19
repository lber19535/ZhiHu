package com.bill.zhihu.ui.answer;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.model.FontSize;
import com.bill.zhihu.model.answer.AnswerIntentValue;
import com.bill.zhihu.util.AnimeUtils;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_answer, container, false);

        initView();
        setHasOptionsMenu(true);

        vm = new AnswerVM(getActivity(), binding);

        Intent intent = getActivity().getIntent();

        vm.playLoadingAnim();

        if (intent.getAction().equals(IntentConstant.INTENT_ACTION_ANSWER_INTENT_VALUE)) {
            AnswerIntentValue value = intent.getParcelableExtra(IntentConstant.INTENT_NAME_ANSWER_INTENT_VALUE);
            vm.setAuthor(value.getAuthor());
            vm.setVoteupCount(value.getVoteupCount());
            vm.loadAnswer(value.getAnswerId());
        }

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

    private void showVoteBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View dialogContent = getActivity().getLayoutInflater().inflate(R.layout.layout_bottom_sheet_vote_up, null);
        dialogContent.findViewById(R.id.vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_answer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.font_size:
                createFontSizeDialog().show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public AlertDialog createFontSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("字体大小")
                .setItems(R.array.font_size_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                vm.setAnswerFontSize(FontSize.SMALL);
                                break;
                            case 1:
                                vm.setAnswerFontSize(FontSize.NORMAL);
                                break;
                            case 2:
                                vm.setAnswerFontSize(FontSize.LARGE);
                                break;
                            case 3:
                                vm.setAnswerFontSize(FontSize.HUGE);
                                break;
                        }
                    }
                });
        return builder.create();
    }
}
