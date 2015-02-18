package com.bill.zhihu.api.cmd;

import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.net.ZhihuVolley;

public abstract class Command {

	protected final String TAG = getClass().getName();
	protected ZhihuVolley volley;
	protected static String xsrf;

	public Command() {
		volley = ZhihuVolley.getInstance(ZhihuApp.getContext());

	}

	abstract public void exec();

	abstract public <T extends CommandCallback> void setOnCmdCallBack(T callback);

	public interface CommandCallback {

	}

}
