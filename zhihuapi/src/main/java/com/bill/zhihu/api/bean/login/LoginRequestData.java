package com.bill.zhihu.api.bean.login;

//import com.bill.jeson.annotation.JsonField;
//import com.bill.jeson.annotation.JsonObject;

/**
 * 登陆时返回的json中有一个data对象
 * Created by Bill-pc on 2015/7/24.
 */
//@JsonObject
public class LoginRequestData {

//    @JsonField
    private String captcha;
//    @JsonField
    private String password;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取返回提示信息，可能会有邮箱密码错误，验证码错误等
     * <p/>
     * 如果问题未知会返回null
     *
     * @return
     */
    public String getMsg() {
        if (captcha != null && !captcha.isEmpty())
            return captcha;
        if (password != null && !password.isEmpty())
            return password;
        return null;
    }
}
