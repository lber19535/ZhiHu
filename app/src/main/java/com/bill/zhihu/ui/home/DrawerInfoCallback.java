package com.bill.zhihu.ui.home;

import android.graphics.drawable.Drawable;

/**
 * Created by bill_lv on 2016/4/20.
 */
public interface DrawerInfoCallback {

    void setHeadImage(Drawable drawable);

    void setHeadName(String name);

    void setHeadLine(String headline);
}
