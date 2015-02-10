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
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.api.binder.aidl.ILogin;
import com.bill.zhihu.api.binder.aidl.ILoginService;
import com.bill.zhihu.api.binder.aidl.ILoginServiceCallback;

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
	private ILoginService loginService;

	private ILoginServiceCallback callback = new ILoginServiceCallback.Stub() {

		@Override
		public void valueChanged(String captchaImgFilePath)
				throws RemoteException {
			System.out.println(captchaImgFilePath);

		}
	};

	private ServiceConnection loginConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("onServiceDisconnected");
			login = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("onServiceConnected");
			login = ILogin.Stub.asInterface(service);
		}
	};
	private ServiceConnection callbackConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("onServiceDisconnected");
			loginService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("onServiceConnected");
			loginService = ILoginService.Stub.asInterface(service);
			try {
				loginService.registerCallback(callback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login, container, false);
		initView();
		bindService();
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
				try {
					String account = accountEdt.getEditableText().toString();
					String pwd = pwdEdt.getEditableText().toString();
					String captcha = captchaEdt.getEditableText().toString();

					if (account.isEmpty() || pwd.isEmpty()) {
						Toast.makeText(getActivity(), "请输入账号密码",
								Toast.LENGTH_SHORT).show();
					}

					int code = login.login(account, pwd, captcha);
					System.out.println(code);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});
	}
	
	private void bindService(){
		String[] classes = {ILogin.class.getName(), ILoginService.class.getName()};
//		for (String string : classes) {
			Intent intent1 = new Intent();
			intent1.setClassName("com.bill.zhihu.api",
					"com.bill.zhihu.api.service.LoginService");
			intent1.setAction("com.bill.zhihu.api.service.login");
			intent1.putExtra("CLASS", classes[0]);
			getActivity().bindService(intent1, loginConnection, Context.BIND_AUTO_CREATE);
			Intent intent2 = new Intent();
			intent2.setClassName("com.bill.zhihu.api",
					"com.bill.zhihu.api.service.LoginService");
			intent2.setAction("com.bill.zhihu.api.service.login");
			intent2.putExtra("CLASS", classes[1]);
			getActivity().bindService(intent2, callbackConnection, Context.BIND_AUTO_CREATE);
//		}

	}

}
