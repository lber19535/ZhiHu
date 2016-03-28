package com.bill.zhihu.ui.home;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.databinding.HomePageViewBinding;
import com.bill.zhihu.vm.HomeVM;

/**
 * 主页
 *
 * @author Bill Lv
 */
public class FragmentHome extends Fragment {

    private HomePageViewBinding binding;
    private HomeVM vm;

    private static final int[] SWIPE_COLOR_SCHEMA= {R.color.swipe_color1,
            R.color.swipe_color2, R.color.swipe_color3,
            R.color.swipe_color4};

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false);
        vm = new HomeVM(binding, getActivity());
        binding.setVm(vm);


        initView();

        vm.playLoadingAnim();

        vm.loadHomePage();

        return binding.getRoot();

    }

    // 初始化相关的view参数
    private void initView() {

        binding.fab.attachToRecyclerView(binding.timeLineList);

        // 设置下拉刷新圆圈的颜色
        binding.swipeToRefresh.setColorSchemeResources(SWIPE_COLOR_SCHEMA);

        // 设置layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        binding.timeLineList.setLayoutManager(layoutManager);
        // divider
        binding.timeLineList.addItemDecoration(new TimeLineItemDecoration());

    }


}
