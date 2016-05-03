package com.bill.zhihu.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.GetCaptchaResponse;
import com.bill.zhihu.api.bean.response.PostCaptchaResponse;
import com.bill.zhihu.api.bean.response.ShowCaptchaResponse;
import com.bill.zhihu.databinding.LoginViewBinding;
import com.bill.zhihu.model.login.UserModel;
import com.bill.zhihu.ui.home.ActivityHome;
import com.bill.zhihu.util.ToastUtil;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class LoginPresenter {

    private LoginViewBinding binding;
    private Activity activity;
    private boolean needCaptcha = false;

    public LoginPresenter(LoginViewBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        this.binding.setUser(new UserModel());
    }

    public void showCaptcha() {
        ZhihuApi.showCaptcha()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShowCaptchaResponse>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("show capthca complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("show capthca error");
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(ShowCaptchaResponse showCaptchaResponse) {
                        needCaptcha = showCaptchaResponse.showCaptcha;
                        showCaptcha(needCaptcha);
                    }
                });
    }

    private void showCaptcha(boolean show) {
        if (show) {
            binding.captchaLayout.setVisibility(View.VISIBLE);
            ZhihuApi.getCaptcha()
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<GetCaptchaResponse, Drawable>() {
                        @Override
                        public Drawable call(GetCaptchaResponse getCaptchaResponse) {
                            byte[] byteCode = Base64.decode(getCaptchaResponse.imgbase64, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);
                            bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 1.5), (int) (bitmap.getHeight() * 1.5), true);
                            return new BitmapDrawable(null, bitmap);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Drawable>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.d(e.toString());
                        }

                        @Override
                        public void onNext(Drawable captchaImg) {
                            binding.captchaLayout.getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, captchaImg, null);
                        }
                    });
        } else {
            binding.captchaLayout.setVisibility(View.GONE);
        }

    }

    public void onClickLogin(View v) {
        UserModel user = binding.getUser();
        binding.loginBtn.setClickable(false);

        if (TextUtils.isEmpty(user.getAccount()) || TextUtils.isEmpty(user.getPassword())) {
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            binding.loginBtn.setClickable(true);
            return;
        }

        if (needCaptcha) {
            System.out.println(user.getCaptcha());
            Logger.d(user.getCaptcha());
            ZhihuApi.postCaptcha(user.getCaptcha())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PostCaptchaResponse>() {
                        @Override
                        public void onCompleted() {
                            Logger.d("post captcha complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.d(e.toString());
                            showCaptcha(true);
                            ToastUtil.showShortToast("验证码错误");
                            binding.loginBtn.setClickable(true);
                        }

                        @Override
                        public void onNext(PostCaptchaResponse postCaptchaResponse) {
                            if (postCaptchaResponse.success) {
                                login();
                            } else {
                                ToastUtil.showShortToast(postCaptchaResponse.error.message);
                            }
                        }
                    });
        } else {
            login();
        }


    }

    private void login() {
        UserModel user = binding.getUser();
        ZhihuApi.login(user.getAccount(), user.getPassword())
                .subscribeOn(Schedulers.io())
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
