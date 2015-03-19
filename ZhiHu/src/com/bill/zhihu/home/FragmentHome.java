package com.bill.zhihu.home;

import java.util.List;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdFetchHomePage;
import com.bill.zhihu.api.cmd.CmdTopFeed;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 主页
 * 
 * @author Bill Lv
 *
 */
public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_page,
                container, false);

        CmdFetchHomePage homePage = new CmdFetchHomePage();
        homePage.setOnCmdCallBack(new CmdFetchHomePage.CallbackListener() {

            @Override
            public void callback(List<TimeLineItem> timelineItems) {

            }

        });
        ZhihuApi.execCmd(homePage);

        return rootView;
    }

}
