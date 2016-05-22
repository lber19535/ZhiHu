package com.bill.zhihu.ui.answer;

import android.content.Intent;
import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.model.answer.AnswerIntentValue;
import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.transformer.ZhihuSwipeBackTransformer;
import com.bill.zhihu.util.SwipeBackUtils;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.orhanobut.logger.Logger;

/**
 * 答案
 * <p/>
 * Created by Bill-pc on 5/22/2015.
 */
public class ActivityAnswer extends BaseActivity {

    private static final String TAG = "ActivityAnswer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init the swipe back
        SwipeBackUtils.attach(this,R.layout.activity_answer);

        initToolBar();

        // 根据action判断title存放位置
        Intent intent = getIntent();
        AnswerIntentValue value = intent.getParcelableExtra(IntentConstant.INTENT_NAME_ANSWER_INTENT_VALUE);
        String questionTitle = value.getTitle();

        Logger.t(TAG).d(TAG, "question title is " + questionTitle);
        setTitle(questionTitle);

        toggleFragment(new FragmentAnswer());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
