package com.bill.zhihu.api.binder.aidl;

oneway interface ILoginServiceCallback {
    void valueChanged(String captchaImgFilePath);
}
