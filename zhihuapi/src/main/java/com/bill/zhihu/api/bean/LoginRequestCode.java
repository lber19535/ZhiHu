package com.bill.zhihu.api.bean;

//import com.bill.jeson.annotation.JsonField;
//import com.bill.jeson.annotation.JsonObject;

/**
 * 登陆状态返回的json
 *
 * @author Bill Lv
 */
//@JsonObject
public class LoginRequestCode {

    /**
     * // login response {    "r": 1,    "errcode": 1991829,
     * "data": { "captcha":"\u9a8c\u8bc1\u7801\u9519\u8bef" },
     * "msg": "\u9a8c\u8bc1\u7801\u9519\u8bef"    }
     */

//    @JsonField(name = "r")
    private int requestCode;

//    @JsonField(name = "errcode")
    private int errorCode = -1;

//    @JsonField(name = "msg")
    private String reqMsg;

//    @JsonField(name = "data")
    private LoginRequestData data;

    private LoginRequestMsg msg;

    /**
     * 根据返回的json不同自动判断出需要提示用户的msg
     * @return
     */
    public String getReqMsg() {
        if (reqMsg == null || reqMsg.equals("null"))
            return data.getMsg();
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public LoginRequestData getData() {
        return data;
    }

    public void setData(LoginRequestData data) {
        this.data = data;
    }

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
