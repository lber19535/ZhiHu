package com.bill.zhihu.answer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.api.bean.AnswerItemInQuestion;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * 答案
 * <p/>
 * Created by Bill-pc on 5/22/2015.
 */
public class ActivityAnswer extends BaseActivity {

    private static final String TAG = "ActivityAnswer";

    private TextView questionTv;
    private ImageView moreIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        this.moreIv = (ImageView) findViewById(R.id.more);
        this.questionTv = (TextView) findViewById(R.id.question);

        Intent intent = getIntent();
        String action = intent.getAction();
        String questionTitle = null;
        switch (action) {
            case TimeLineItem.KEY: {
                TimeLineItem item = intent.getParcelableExtra(TimeLineItem.KEY);
                questionTitle = item.getQuestion();
                break;
            }
            case AnswerItemInQuestion.KEY: {
                AnswerItemInQuestion item = intent.getParcelableExtra(AnswerItemInQuestion.KEY);
                questionTitle = item.getQuestionTitle();
                break;
            }
            default:
                break;
        }
        ZhihuLog.d(TAG, "question title is " + questionTitle);
        questionTv.setText(questionTitle);

        moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreMenu();
            }
        });

        toggleFragment(new FragmentAnswer());
    }

    private void showMoreMenu() {
        PopupMenu menu = new PopupMenu(this, moreIv);
        menu.getMenuInflater().inflate(R.menu.activity_answer_menu, menu.getMenu());
        menu.show();
    }
}
