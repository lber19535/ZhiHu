package com.bill.zhihu.home;

import java.util.ArrayList;
import java.util.List;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdFetchHomePage;
import com.bill.zhihu.api.cmd.CmdTopFeed;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 主页
 * 
 * @author Bill Lv
 *
 */
public class FragmentHome extends Fragment {

	private ListView timelineLv;
	private List<TimeLineItem> timelineItems;
	private TimeLineAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		timelineItems = new ArrayList<>();

		View rootView = inflater.inflate(R.layout.fragment_home_page,
				container, false);
		adapter = new TimeLineAdapter(getActivity(), timelineItems);
		timelineLv = (ListView) rootView.findViewById(R.id.time_line_list);
		
		timelineLv.setAdapter(adapter);
		
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

	class TimeLineAdapter extends BaseAdapter {

		// 只有问题的item和问题+答案预览
		private static final int VIEW_TYPE_COUNT = 2;

		private List<TimeLineItem> timelineItems;
		private LayoutInflater mInflater;

		public TimeLineAdapter(Context context, List<TimeLineItem> timelineItems) {
			this.timelineItems = timelineItems;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return timelineItems.size();
		}

		@Override
		public Object getItem(int position) {
			return timelineItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE_COUNT;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TimeLineItem item = timelineItems.get(position);
			View itemView = mInflater.inflate(R.layout.list_item_only_question, null);
			TextView questionTv = (TextView) itemView.findViewById(R.id.question);
			questionTv.setText(item.toString());
			return itemView;
		}

	}
}
