package com.bill.zhihu.model.login;

import android.content.Context;

import com.bill.zhihu.api.ZhihuApi;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class LoginModel {

    private Context context;

    public LoginModel(Context context) {
        this.context = context;
    }

    public Observable<Void> avoidCaptcha(){
        return ZhihuApi.captcha().subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> login(User user){
        return ZhihuApi.login(user.getAccount(), user.getPassword()).subscribeOn(Schedulers.io());
    }
}
