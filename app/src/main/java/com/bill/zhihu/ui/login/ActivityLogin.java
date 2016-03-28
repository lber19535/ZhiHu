package com.bill.zhihu.ui.login;

import android.app.Fragment;
import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;

/**
 * 主界面
 * 
 * @author Bill Lv
 *
 */
public class ActivityLogin extends BaseActivity {

    private Fragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initToolBar();

    }

}
