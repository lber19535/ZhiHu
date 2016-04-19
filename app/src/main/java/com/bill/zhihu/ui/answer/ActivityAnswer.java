package com.bill.zhihu.ui.answer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.model.answer.AnswerIntentValue;
import com.bill.zhihu.constant.IntentConstant;
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
        setContentView(R.layout.activity_answer);

        initToolBar();

        // 根据action判断title存放位置
        Intent intent = getIntent();
        AnswerIntentValue value = intent.getParcelableExtra(IntentConstant.INTENT_NAME_ANSWER_INTENT_VALUE);
        String questionTitle = value.getTitle();

        Logger.t(TAG).d(TAG, "question title is " + questionTitle);
        setTitle(questionTitle);

        toggleFragment(new FragmentAnswer());
    }
}
