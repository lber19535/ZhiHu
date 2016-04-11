package com.bill.zhihu.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.databinding.StartPageViewBinding;
import com.bill.zhihu.ui.home.ActivityHome;
import com.bill.zhihu.ui.login.ActivityLogin;
import com.bill.zhihu.vm.StartPageVM;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * 启动页，该Activity中会进行一些联网判断，登陆判断等处理来决定跳转页面
 *
 * @author Bill Lv
 */
public class StartPageActivity extends BaseActivity {

    private StartPageViewBinding viewBinding;
    private StartPageVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_page);

        vm = new StartPageVM(viewBinding, this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        vm.initStartPage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {

                    @Override
                    public void call(Boolean login) {
                        if (login) {
                            Intent intent = new Intent(StartPageActivity.this, ActivityHome.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(StartPageActivity.this, ActivityLogin.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
