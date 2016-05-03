package com.bill.zhihu.util;

import com.orhanobut.logger.Logger;

/**
 * Created by bill_lv on 2016/4/20.
 */
public class ImageUrlUtils {

    private static final String TAG = "ImageUrlUtils";

    /**
     * 通常avatar的地址都是缩略图，该方法将缩略图的地址转换为原尺寸的地址
     * <p/>
     * 原理是缩略图中结尾包含一个_s，而原图没有
     *
     * @return
     */
    public static String avatarSmall2Normal(String avatarUrl) {
        if (avatarUrl.contains("_s")) {
            return avatarUrl.replace("_s", "_l");
        } else {
            Logger.t(TAG).d("avatar url is not a small pic url");
            Logger.t(TAG).d("avatarUrl " + avatarUrl);
            return avatarUrl;
        }
    }

    /**
     * 通常avatar的地址都是缩略图，该方法将缩略图的地址转换为原尺寸的地址
     * <p/>
     * 原理是缩略图中结尾包含一个_s，而原图没有
     *
     * @return
     */
    public static String webimgSmall2Normal(String url) {
        if (url.contains("_b")) {
            return url.replace("_b", "_r");
        } else {
            Logger.t(TAG).d("web img url is not a small pic url");
            Logger.t(TAG).d("web img " + url);
            return url;
        }
    }
}
