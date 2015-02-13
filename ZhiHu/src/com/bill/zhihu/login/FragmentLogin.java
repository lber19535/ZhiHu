package com.bill.zhihu.login;

import android.app.Fragment;
import android.content.ComponentName;
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
import com.bill.zhihu.api.ZhihuLog;
import com.bill.zhihu.api.binder.aidl.ILogin;
import com.bill.zhihu.api.binder.aidl.ILoginServiceCallback;
import com.bill.zhihu.api.cmd.CmdLogin;

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
	private ILogin login;

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
			System.out.println("loginConnection onServiceDisconnected");
			login = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("loginConnection onServiceConnected");
			login = ILogin.Stub.asInterface(service);
			try {
				login.registerCallback(callback);
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
//		bindService();
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

			}
		});
	}

//	private void bindService() {
//		Intent intent = new Intent();
//		intent.setClassName("com.bill.zhihu",
//				"com.bill.zhihu.api.service.LoginService");
//		intent.setAction("com.bill.zhihu.api.service.login");
//		getActivity().bindService(intent, loginConnection,
//				Context.BIND_AUTO_CREATE);
//
//	}

}
