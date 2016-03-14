package com.bill.zhihu.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bill.zhihu.api.ZhihuApi;

import java.util.HashMap;

/**
 * Created by bill_lv on 2016/2/17.
 */
public class AuthStore {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String UNLOCK_TICKET = "unlock_ticket";
    private static final String TOKEN_TYPE = "token_type";
    private static final String UID = "uid";

    public static void setAccessToken(String toekn) {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        sp.edit().putString(ACCESS_TOKEN, toekn).commit();
    }

    public static void setUnlockTicket(String unlockTicket) {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        sp.edit().putString(UNLOCK_TICKET, unlockTicket).commit();
    }

    public static void setTokenType(String type) {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        sp.edit().putString(TOKEN_TYPE, type).commit();
    }

    public static void setUID(String uid) {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        sp.edit().putString(UID, uid).commit();
    }


    public static String getAccessToken() {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        return sp.getString(ACCESS_TOKEN, "");
    }

    public static String getUnlockTicket() {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        return sp.getString(UNLOCK_TICKET, "");
    }

    public static String getTokenType() {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        return sp.getString(TOKEN_TYPE, "");
    }

    public static String getUID() {
        SharedPreferences sp = ZhihuApi.getContext().getSharedPreferences("auth_store", Context.MODE_PRIVATE);
        return sp.getString(UID, "");
    }


}
