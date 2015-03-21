package com.bill.zhihu.home;

import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;

public class ActivityHome extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toggleFragment(new FragmentHome());
    }

}
