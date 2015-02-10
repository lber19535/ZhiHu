package com.bill.zhihu.api.binder.aidl;

import com.bill.zhihu.api.binder.aidl.ILoginServiceCallback;

interface ILoginService {

    void registerCallback(ILoginServiceCallback cb);

    void unregisterCallback(ILoginServiceCallback cb);
}
