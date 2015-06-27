package com.bill.zhihu.answer;

import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;

/**
 * Created by Bill-pc on 5/22/2015.
 */
public class ActivityAnswer extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        toggleFragment(new FragmentAnswer());
    }
}
