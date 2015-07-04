package com.bill.zhihu.answer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.AnswerContent;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.cmd.CmdLoadAnswer;
import com.bill.zhihu.api.cmd.CmdLoadAvatarImage;
import com.bill.zhihu.home.TimeLineViewHolder;

/**
 * Created by Bill-pc on 5/22/2015.
 */
public class FragmentAnswer extends Fragment implements CmdLoadAnswer.CallBackListener, CmdLoadAvatarImage.CallbackListener {

    private View rootView;
    private ImageView avatar;
    private TextView name;
    private TextView intro;
    private TextView vote;
    private WebView answerWv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer, null);

        this.vote = (TextView) rootView.findViewById(R.id.vote);
        this.intro = (TextView) rootView.findViewById(R.id.intro);
        this.name = (TextView) rootView.findViewById(R.id.name);
        this.avatar = (ImageView) rootView.findViewById(R.id.avatar);
        this.answerWv = (WebView) rootView.findViewById(R.id.answer);

        Intent intent = getActivity().getIntent();
        TimeLineItem item = intent.getParcelableExtra(TimeLineViewHolder.EXSTRA_ITEM);

        String answerUrl = item.getAnswerUrl().getUrl();

        ZhihuApi.loadAnswer(answerUrl, this);

        return rootView;
    }

    @Override
    public void callBack(AnswerContent content) {
        vote.setText(content.getVote());
        intro.setText(content.getIntro());
        name.setText(content.getPeopleName());
        answerWv.getSettings().setJavaScriptEnabled(false);
        answerWv.getSettings().setTextZoom(120);
        answerWv.loadData(content.getAnswer(), "text/html; charset=UTF-8", null);

        CmdLoadAvatarImage loadAvatarImage = new CmdLoadAvatarImage(content.getAvatarImgUrl());
        loadAvatarImage.setOnCmdCallBack(this);
        ZhihuApi.execCmd(loadAvatarImage);

    }

    @Override
    public void callback(Bitmap captchaImg) {
        avatar.setImageBitmap(captchaImg);
    }
}
