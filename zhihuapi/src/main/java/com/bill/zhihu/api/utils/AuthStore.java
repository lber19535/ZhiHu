package com.bill.zhihu.api.utils;

import java.util.HashMap;

/**
 * Created by bill_lv on 2016/2/17.
 */
public class AuthStore {

    private static final HashMap<String, String> store = new HashMap<>();

    private static final String ACCESS_TOKEN = "access_token";
    private static final String UNLOCK_TICKET = "unlock_ticket";
    private static final String TOKEN_TYPE = "token_type";
    private static final String UID = "uid";

    public static void setAccessToken(String toekn) {
        store.put(ACCESS_TOKEN, toekn);
    }

    public static void setUnlockTicket(String unlockTicket) {
        store.put(UNLOCK_TICKET, unlockTicket);
    }

    public static void setTokenType(String type) {
        store.put(TOKEN_TYPE, type);
    }

    public static void setUID(String uid) {
        store.put(UID, uid);
    }


    public static String getAccessToken() {
        return store.get(ACCESS_TOKEN);
    }

    public static String getUnlockTicket() {
        return store.get(UNLOCK_TICKET);
    }

    public static String getTokenType() {
        return store.get(TOKEN_TYPE);
    }

    public static String getUID() {
        return store.get(UID);
    }


}
