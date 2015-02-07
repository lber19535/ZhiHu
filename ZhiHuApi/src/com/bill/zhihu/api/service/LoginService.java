package com.bill.zhihu.api.service;

import com.bill.zhihu.api.binder.aidl.ILogin;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 登录服务 1.登录 2.登出 3.验证码
 *
 * 验证码可以通过aidl传递（Bitmap继承了parcelable接口可以直接传递）， 也可以存在本地传递地址，实现的时候分别验证下效率
 * 
 * @author Bill Lv
 *
 */
public class LoginService extends Service {

	private ILogin.Stub mBinder;

	public LoginService() {
		System.out.println("create service");
	}

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("on bind");
		mBinder = new ILogin.Stub() {

			@Override
			public int login(String account, String pwd, String captcha)
					throws RemoteException {
				System.out.println("service " + account + " " + pwd + " "
						+ captcha);
				return 2333;
			}

			@Override
			public boolean haveCaptcha() throws RemoteException {
				return true;
			}
		};

		return mBinder;
	}

}
