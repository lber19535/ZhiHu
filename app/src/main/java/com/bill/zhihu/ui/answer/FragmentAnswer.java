package com.bill.zhihu.ui.answer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.databinding.AnswerViewBinding;
import com.bill.zhihu.util.IntentUtils;
import com.bill.zhihu.vm.answer.AnswerVM;

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

    private AnswerViewBinding binding;
    private AnswerVM vm;

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

            }
        });

        // bind js
        binding.answer.bindJs(vm, "ZhihuAndroid");

    }


}
