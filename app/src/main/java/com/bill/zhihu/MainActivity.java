package com.bill.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.home.ActivityHome;
import com.bill.zhihu.login.ActivityLogin;

import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 启动页，该Activity中会进行一些联网判断，登陆判断等处理来决定跳转页面
 *
 * @author Bill Lv
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZhihuApi.haveLogin().observeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean login) {
                if (login) {
                    Intent intent = new Intent(MainActivity.this, ActivityHome.class);
                    startActivity(intent);
                    finish();
                } else {
                    ZhihuApi.captcha().observeOn(Schedulers.io()).subscribe();
                    Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

}
