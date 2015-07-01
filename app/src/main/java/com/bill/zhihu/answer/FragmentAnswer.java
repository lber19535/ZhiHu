package com.bill.zhihu.answer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.home.TimeLineViewHolder;

/**
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment {

    private View rootView;
    private ImageView avatar;
    private RelativeLayout answerhead;
    private TextView share;
    private TextView useless;
    private TextView thank;
    private TextView comment;
    private LinearLayout answerbottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer, null);
        this.answerbottom = (LinearLayout) rootView.findViewById(R.id.answer_bottom);
        this.comment = (TextView) rootView.findViewById(R.id.comment);
        this.thank = (TextView) rootView.findViewById(R.id.thank);
        this.useless = (TextView) rootView.findViewById(R.id.useless);
        this.share = (TextView) rootView.findViewById(R.id.share);
        this.answerhead = (RelativeLayout) rootView.findViewById(R.id.answer_head);
        this.avatar = (ImageView) rootView.findViewById(R.id.avatar);

        Intent intent = getActivity().getIntent();
        TimeLineItem item = intent.getParcelableExtra(TimeLineViewHolder.EXSTRA_ITEM);

        return rootView;
    }
}
