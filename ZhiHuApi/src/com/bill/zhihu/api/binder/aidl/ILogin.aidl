package com.bill.zhihu.api.binder.aidl;

interface ILogin{
    boolean haveCaptcha();
    int login(String account, String pwd, String captcha);
}