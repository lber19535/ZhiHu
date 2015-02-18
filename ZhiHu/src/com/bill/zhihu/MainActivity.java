package com.bill.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cmd.CmdFetchXSRF;
import com.bill.zhihu.login.ActivityHome;

/**
 * 首页
 * 
 * @author Bill Lv
 *
 */
public class MainActivity extends Activity {

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sp = PreferenceManager.getDefaultSharedPreferences(this);

		CmdFetchXSRF cmdFetchXSRF = new CmdFetchXSRF();
		cmdFetchXSRF.setOnCmdCallBack(new CmdFetchXSRF.CallbackListener() {

			@Override
			public void callback(String xsrf) {
				Intent intent = new Intent(MainActivity.this,
						ActivityHome.class);
				startActivity(intent);
				sp.edit().putString("_xsrf", xsrf);
				finish();
			}
		});

		ZhihuApi.execCmd(cmdFetchXSRF);

	}

}
