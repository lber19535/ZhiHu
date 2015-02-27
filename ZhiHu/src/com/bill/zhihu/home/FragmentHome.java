package com.bill.zhihu.home;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cmd.CmdFetchHomePage;
import com.bill.zhihu.api.cmd.CmdTopFeed;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		CmdFetchHomePage homePage = new CmdFetchHomePage();
		ZhihuApi.execCmd(homePage);

		CmdTopFeed cmdTopFeed = new CmdTopFeed(
				System.currentTimeMillis() / 1000, 20);
		ZhihuApi.execCmd(cmdTopFeed);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
