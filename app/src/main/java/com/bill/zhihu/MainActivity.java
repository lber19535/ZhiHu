package com.bill.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cmd.CmdLoadXSRF;
import com.bill.zhihu.api.net.ZhihuCookieManager;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.home.ActivityHome;
import com.bill.zhihu.login.ActivityLogin;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.update.UmengUpdateAgent;

/**
 * 启动页，该Activity中会进行一些联网判断，登陆判断等处理来决定跳转页面
 *
 * @author Bill Lv
 */
public class MainActivity extends Activity {

    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // u meng auto update
        UmengUpdateAgent.update(this);
        // u meng push service
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();

        boolean haveLogin = ZhihuCookieManager.haveCookieName("z_c0");

        if (haveLogin) {
            ZhihuLog.d(TAG, "have login");
            Intent intent = new Intent(this, ActivityHome.class);
            startActivity(intent);
            finish();
            return;
        }

        // 未登录则初始化cookie，获取xsrf的过程中会初始化cookie
        CmdLoadXSRF cmdFetchXSRF = new CmdLoadXSRF();
        cmdFetchXSRF.setOnCmdCallBack(new CmdLoadXSRF.CallbackListener() {

            @Override
            public void callback(String xsrf) {
                ZhihuLog.d(TAG, "xsrf " + xsrf);
                Intent intent = new Intent(MainActivity.this,
                        ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        ZhihuApi.execCmd(cmdFetchXSRF);

    }

}
