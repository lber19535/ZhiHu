package com.bill.zhihu.api.bean.login;

import io.realm.RealmObject;

/**
 * Created by bill_lv on 2016/3/9.
 */
public class LoginState extends RealmObject {

    private boolean haveLogin;

    public boolean isHaveLogin() {
        return haveLogin;
    }

    public void setHaveLogin(boolean haveLogin) {
        this.haveLogin = haveLogin;
    }
}
