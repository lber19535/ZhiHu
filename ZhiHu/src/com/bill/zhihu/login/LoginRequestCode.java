package com.bill.zhihu.login;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

/**
 * 登陆状态返回的json
 * 
 * @author Bill Lv
 *
 */
@JsonObject
public class LoginRequestCode {

	@JsonField(name = "r")
	private int requestCode;

	@JsonField(name = "errcode")
	private int errorCode = -1;

	@JsonField
	private LoginRequestMsg msg;

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public LoginRequestMsg getMsg() {
		return msg;
	}

	public void setMsg(LoginRequestMsg msg) {
		this.msg = msg;
	}

}
