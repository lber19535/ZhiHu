package com.bill.zhihu.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
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
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.view.SwipyRefreshLayout;
import com.bill.zhihu.view.SwipyRefreshLayout.OnRefreshListener;
import com.bill.zhihu.view.SwipyRefreshLayoutDirection;

/**
 * 主页
 * 
 * @author Bill Lv
 *
 */
public class FragmentHome extends Fragment {

    private RecyclerView timelineRv;
    private SwipyRefreshLayout refreshLayout;

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

        ZhihuLog.Debug = false;
        initView();

        loadHomePage();

        return rootView;
    }

    private void initView() {
        timelineRv = (RecyclerView) rootView.findViewById(R.id.time_line_list);
        refreshLayout = (SwipyRefreshLayout) rootView
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
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                // TODO Auto-generated method stub
                // loadMoreNews();
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    loadHomePage();
                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    loadMore();
                }
            }

        });
    }

    /**
     * 发出更多动态的请求，解析成功后清空之前的数据重新赋值
     */
    private void loadMoreNews() {
        // CmdMoreNews cmdMoreNews = new CmdMoreNews();
        // cmdMoreNews.setOnCmdCallBack(new CmdMoreNews.CallbackListener() {
        //
        // @Override
        // public void callback(List<TimeLineItem> items) {
        // if (items != null && !items.isEmpty()) {
        // timelineItems.clear();
        // timelineItems.addAll(items);
        // }
        // adapter.notifyDataSetChanged();
        // refreshLayout.setRefreshing(false);
        // }
        // });
        // ZhihuApi.execCmd(cmdMoreNews);
    }

    /**
     * 获取首页
     */
    private void loadHomePage() {

        CmdFetchHomePage homePage = new CmdFetchHomePage();
        homePage.setOnCmdCallBack(new CmdFetchHomePage.CallbackListener() {

            @Override
            public void callback(List<TimeLineItem> timelineitems) {
                if (timelineitems != null && !timelineitems.isEmpty()) {
                    timelineItems.clear();
                    timelineItems.addAll(timelineitems);
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

        });
        ZhihuApi.execCmd(homePage);
    }

    /**
     * 上拉加载更多
     */
    private void loadMore() {
        TimeLineItem item = timelineItems.get(timelineItems.size() - 1);
        long blockId = Long.valueOf(item.getDataBlock());
        int offset = Integer.valueOf(item.getDataOffset());
        CmdLoadMore loadMore = new CmdLoadMore(blockId, offset);
        loadMore.setOnCmdCallBack(new CmdLoadMore.CallbackListener() {

            @Override
            public void callback(List<TimeLineItem> timelineitems) {
                int scrollToPosition = timelineItems.size();
                if (timelineitems != null && !timelineitems.isEmpty()) {
                    timelineItems.addAll(timelineitems);
                }
                adapter.notifyDataSetChanged();
                timelineRv.smoothScrollToPosition(scrollToPosition);
                refreshLayout.setRefreshing(false);
            }

        });
        ZhihuApi.execCmd(loadMore);
    }

}
