package com.bill.zhihu.question;

import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;

/**
 * 问题界面 包括问题列表、问题、问题详情、话题
 * 主要显示在fragment中
 * <p/>
 * Created by Bill Lv on 2015/8/15.
 */
public class ActivityQuestion extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        toggleFragment(new FragmentQuestion());
    }
}
