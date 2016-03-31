package com.bill.zhihu.ui.answer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.util.FeedsItemUtils;
import com.bill.zhihu.util.IntentUtils;
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
        FeedsItem item = intent.getParcelableExtra(IntentUtils.ITENT_NAME_FEEDS_ITEM);
        String questionTitle = FeedsItemUtils.getTitle(item);

        Logger.t(TAG).d(TAG, "question title is " + questionTitle);
        setTitle(questionTitle);

        toggleFragment(new FragmentAnswer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_answer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
