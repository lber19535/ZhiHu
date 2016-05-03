package com.bill.zhihu.model.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by bill_lv on 2016/3/25.
 */
public class UserModel extends BaseObservable {

    private String account;
    private String password;
    private String captcha;

    private SimpleTextWatcher accountWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(String s) {
            setAccount(s);
        }
    };

    private SimpleTextWatcher pwdWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(String s) {
            setPassword(s);
        }
    };
    private SimpleTextWatcher captchaWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(String s) {
            setCaptcha(s);
        }
    };

    @Bindable
    public SimpleTextWatcher getAccountWatcher() {
        return accountWatcher;
    }

    public void setAccountWatcher(SimpleTextWatcher accountWatcher) {
        this.accountWatcher = accountWatcher;
    }

    @Bindable
    public SimpleTextWatcher getPwdWatcher() {
        return pwdWatcher;
    }

    public void setPwdWatcher(SimpleTextWatcher pwdWatcher) {
        this.pwdWatcher = pwdWatcher;
    }

    @Bindable
    public SimpleTextWatcher getCaptchaWatcher() {
        return captchaWatcher;
    }

    public void setCaptchaWatcher(SimpleTextWatcher captchaWatcher) {
        this.captchaWatcher = captchaWatcher;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    @Bindable
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
