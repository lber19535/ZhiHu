package com.bill.zhihu.login;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.api.binder.aidl.ILogin;

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
	private ILogin login;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			login = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			login = ILogin.Stub.asInterface(service);
		}
	};

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

		Intent intent = new Intent();
		intent.setClassName("com.bill.zhihu.api",
				"com.bill.zhihu.api.service.LoginService");
		intent.setAction("com.bill.zhihu.api.service.login");
		getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);

		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int code = login.login(accountEdt.getEditableText()
							.toString(), pwdEdt.getEditableText().toString(),
							"");
					System.out.println(code);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});

	}

}
