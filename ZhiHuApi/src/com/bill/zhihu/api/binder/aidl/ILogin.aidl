package com.bill.zhihu.api.binder.aidl;

import com.bill.zhihu.api.binder.aidl.ILoginServiceCallback;

interface ILogin{
    boolean haveCaptcha();

    void login(String account, String pwd, String captcha);

    void registerCallback(ILoginServiceCallback cb);

    void unregisterCallback(ILoginServiceCallback cb);
    
}