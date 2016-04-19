package com.bill.zhihu.api.utils;

import com.orhanobut.logger.Logger;

/**
 * 头像工具类
 * <p/>
 * Created by Bill Lv on 2015/8/12.
 */
public class AvatarUtils {

    private static final String TAG = "AvatarUtils";

    /**
     * 通常avatar的地址都是缩略图，该方法将缩略图的地址转换为原尺寸的地址
     * <p/>
     * 原理是缩略图中结尾包含一个_s，而原图没有
     *
     * @return
     */
    public static String small2normal(String avatarUrl) {
        if (avatarUrl.contains("_s")) {
            return avatarUrl.replace("_s", "_l");
        } else {
            Logger.t(TAG).d("avatar url is not a small pic url");
            Logger.t(TAG).d("avatarUrl " + avatarUrl);
            return avatarUrl;
        }
    }
}
