package com.bill.zhihu.api;

import com.bill.zhihu.api.cmd.Command;

public class ZhihuApi {
	
	public static final String XSRF = "_xsrf";

	public static void execCmd(Command cmd) {
		cmd.exec();
	}

}
