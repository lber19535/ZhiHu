package com.bill.zhihu.api.cmd;

import com.bill.zhihu.ZhihuApp;
import com.bill.zhihu.api.ZhihuVolley;

public abstract class Command {

	protected String TAG = getClass().getName();
	protected ZhihuVolley volley;
	protected String xsrf;

	public Command() {
		volley = ZhihuVolley.getInstance(ZhihuApp.getContext());

	}

	abstract public void exec();

	abstract public <T extends CommandCallback> void setOnCmdCallBack(T callback);

	public interface CommandCallback {

	}

}
