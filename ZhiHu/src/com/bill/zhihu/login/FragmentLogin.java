package com.bill.zhihu.login;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.cmd.CmdLogin;
import com.bill.zhihu.api.utils.ZhihuLog;

/**
 * 登录
 * 
 * @author Bill Lv
 *
 */

public class FragmentLogin extends Fragment {

	private static final String TAG = "FragmentLogin";

	private Button loginBtn;
	private EditText accountEdt;
	private EditText pwdEdt;
	private EditText captchaEdt;
	private LinearLayout captchaLayout;
	private RelativeLayout loginLayout;

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login, container, false);
		initView();
		return rootView;
	}

	private void initView() {
		accountEdt = (EditText) rootView.findViewById(R.id.login_account);
		pwdEdt = (EditText) rootView.findViewById(R.id.login_pwd);
		captchaEdt = (EditText) rootView.findViewById(R.id.login_captcha);
		loginBtn = (Button) rootView.findViewById(R.id.login_btn);
		loginLayout = (RelativeLayout) rootView.findViewById(R.id.login_layout);

		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = accountEdt.getEditableText().toString();
				String pwd = pwdEdt.getEditableText().toString();
				String captcha = captchaEdt.getEditableText().toString();

				if (account.isEmpty() || pwd.isEmpty()) {
					Toast.makeText(getActivity(), "请输入账号密码", Toast.LENGTH_SHORT)
							.show();
				}

				CmdLogin login = new CmdLogin(account, pwd);
				login.setOnCmdCallBack(new CmdLogin.CallbackListener() {

					@Override
					public void callback(String code) {
						ZhihuLog.d(TAG, code);
					}
				});
				ZhihuApi.execCmd(login);

			}
		});
	}

}
