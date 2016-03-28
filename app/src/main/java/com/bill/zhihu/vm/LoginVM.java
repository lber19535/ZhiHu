package com.bill.zhihu.vm;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.databinding.LoginViewBinding;
import com.bill.zhihu.model.login.LoginModel;
import com.bill.zhihu.model.login.User;
import com.bill.zhihu.ui.home.ActivityHome;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class LoginVM {

    private LoginViewBinding binding;
    private Activity activity;
    private LoginModel model;

    public LoginVM(LoginViewBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        this.model = new LoginModel(activity);
    }

    public void avoidCaptcha() {
        model.avoidCaptcha().subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                Logger.d("avoid captcha complete");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("avoid captcha error " + e.getMessage());
            }

            @Override
            public void onNext(Void aVoid) {
                Logger.d("avoid captcha success");
            }
        });
    }

    public void onClickLogin(View v) {
        User user = binding.getUser();
        binding.loginBtn.setClickable(false);

        if (TextUtils.isEmpty(user.getAccount()) || TextUtils.isEmpty(user.getPassword())) {
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            binding.loginBtn.setClickable(true);
            return;
        }

        model.login(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("log in complete");
                        binding.loginBtn.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("log in failed");
                        Logger.d(e.toString());
                        binding.loginBtn.setClickable(true);
                    }

                    @Override
                    public void onNext(Boolean bool) {
                        Logger.d("log in " + bool);
                        ZhihuApi.getPeopleSelfBasic();
                        Intent intent = new Intent(activity, ActivityHome.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
    }
}
