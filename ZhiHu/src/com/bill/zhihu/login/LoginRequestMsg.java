package com.bill.zhihu.login;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class LoginRequestMsg {

	@JsonField(defaultValue = "null")
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
