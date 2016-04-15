package com.bill.zhihu.ui.answer;

/**
 * Created by bill_lv on 2016/4/15.
 */
public abstract class ExpandSelectorCallback {

    private boolean isHide;

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    abstract void onHide();

    abstract void onShow();
}
