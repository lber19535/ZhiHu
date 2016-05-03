package com.bill.zhihu.api.bean.response;

import com.bill.zhihu.api.bean.login.LoginError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/2/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    @JsonProperty("error")
    public LoginError error;
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("uid")
    public String UID;
    @JsonProperty("user_id")
    public String userId;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("unlock_ticket")
    public String unlockTicket;
    @JsonProperty("refresh_token")
    public String refreshToken;

}
