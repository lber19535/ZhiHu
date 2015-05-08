package com.bill.zhihu.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdFetchHomePage;
import com.bill.zhihu.api.cmd.CmdLoadMore;
import com.bill.zhihu.api.cmd.CmdMoreNews;

/**
 * 主页
 * 
 * @author Bill Lv
 *
 */
public class FragmentHome extends Fragment {

    private RecyclerView timelineRv;
    private SwipeRefreshLayout refreshLayout;

    private List<TimeLineItem> timelineItems;
    private TimeLineRecyclerAdapter adapter;
    private View rootView;
    private Handler mHandler = new Handler();

    // 刷新完毕
    private final Runnable mRefreshDone = new Runnable() {

        @Override
        public void run() {
            refreshLayout.setRefreshing(false);
        }
    };

    public FragmentHome() {
        timelineItems = new ArrayList<>();

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_page, container,
                false);

        initView();

        // 获取首页
        CmdFetchHomePage homePage = new CmdFetchHomePage();
        homePage.setOnCmdCallBack(new CmdFetchHomePage.CallbackListener() {

            @Override
            public void callback(List<TimeLineItem> timelineitems) {
                timelineItems.addAll(timelineitems);
                adapter.notifyDataSetChanged();
            }

        });
        ZhihuApi.execCmd(homePage);

        return rootView;
    }

    private void initView() {
        timelineRv = (RecyclerView) rootView.findViewById(R.id.time_line_list);
        refreshLayout = (SwipeRefreshLayout) rootView
                .findViewById(R.id.swipe_to_refresh);
        // 设置下拉刷新圆圈的颜色
        refreshLayout.setColorSchemeResources(R.color.swipe_color1,
                R.color.swipe_color2, R.color.swipe_color3,
                R.color.swipe_color4);

        adapter = new TimeLineRecyclerAdapter(getActivity(), timelineItems);
        // adapter
        timelineRv.setAdapter(adapter);
        // 设置layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        timelineRv.setLayoutManager(layoutManager);
        // divider
        timelineRv.addItemDecoration(new TimeLineItemDecoration());

        // 下拉刷新监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadMoreNews();
                // mHandler.removeCallbacks(mRefreshDone);
                // mHandler.postDelayed(mRefreshDone, 1000);
            }
        });
    }

    /**
     * 发出更多动态的请求，解析成功后清空之前的数据重新赋值
     */
    private void loadMoreNews() {
        CmdMoreNews cmdMoreNews = new CmdMoreNews();
        cmdMoreNews.setOnCmdCallBack(new CmdMoreNews.CallbackListener() {

            @Override
            public void callback(List<TimeLineItem> items) {
                if (items != null && !items.isEmpty()) {
                    timelineItems.clear();
                    timelineItems.addAll(items);
                }
                refreshLayout.setRefreshing(false);
            }
        });
        ZhihuApi.execCmd(cmdMoreNews);
    }

}
