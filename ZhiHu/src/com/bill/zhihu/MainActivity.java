package com.bill.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cmd.CmdFetchXSRF;
import com.bill.zhihu.api.net.ZhihuCookieManager;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.home.ActivityHome;
import com.bill.zhihu.login.ActivityLogin;

/**
 * 启动页，该Activity中会进行一些联网判断，登陆判断等处理来决定跳转页面
 * 
 * @author Bill Lv
 *
 */
public class MainActivity extends Activity {

    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean haveLogin = ZhihuCookieManager.haveCookieName("z_c0");

        if (haveLogin) {
            ZhihuLog.d(TAG, "have login");
            Intent intent = new Intent(this, ActivityHome.class);
            startActivity(intent);
            finish();
            return;
        }

        CmdFetchXSRF cmdFetchXSRF = new CmdFetchXSRF();
        cmdFetchXSRF.setOnCmdCallBack(new CmdFetchXSRF.CallbackListener() {

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
