package com.bill.zhihu.api.net;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

/**
 * 更换为新的cookiestore后对原有的json组织方式进了调整，采用一个uri对于一个cookie list的做法
 * <p/>
 * Created by Bill Lv on 2015/8/30.
 */
@JsonObject
public class CookiesJson {

    @JsonField(defaultValue = "")
    private String uri;
    @JsonField(name = "cookie_list")
    private List<CookieJson> cookieJsonList = new ArrayList<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 添加一个 CookieJson 对象，如果已经有同名的对象，则刷新他的值
     *
     * @param cookie
     */
    public void addCookie(CookieJson cookie) {
        if (!cookieJsonList.isEmpty()) {
            for (int i = 0; i < cookieJsonList.size(); i++) {
                CookieJson cookieJson = cookieJsonList.get(i);
                if (cookieJson.getName() == cookie.getName()) {
                    cookieJson.setComment(cookie.getComment());
                    cookieJson.setCommentUrl(cookie.getCommentUrl());
                    cookieJson.setDomain(cookie.getDomain());
                    cookieJson.setExpiryDate(cookie.getExpiryDate());
                    cookieJson.setPath(cookie.getPath());
                    cookieJson.setValue(cookie.getValue());
                    cookieJson.setVersion(cookie.getVersion());
                } else {
                    cookieJsonList.add(cookie);
                }
            }
        } else {
            cookieJsonList.add(cookie);
        }
    }


    public List<CookieJson> getCookieJsonList() {
        return cookieJsonList;
    }

    public void setCookieJsonList(List<CookieJson> cookieJsonList) {
        this.cookieJsonList = cookieJsonList;
    }
}
