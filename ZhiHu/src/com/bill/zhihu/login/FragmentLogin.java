package com.bill.zhihu.login;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bill.zhihu.R;

/**
 * 登录
 * 
 * @author Bill Lv
 *
 */

public class FragmentLogin extends Fragment {

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
	}

}
