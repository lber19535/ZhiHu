package com.bill.zhihu.ui.answer;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.bill.zhihu.ui.SimpleBehavior;

/**
 * Created by bill_lv on 2016/4/14.
 */
public class ExpandSelectorBehavior extends SimpleBehavior {

    private ExpandSelectorCallback callback;

    public ExpandSelectorBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setCallback(ExpandSelectorCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            callback.onHide();
            callback.setHide(true);
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() == View.GONE) {
            callback.onShow();
            callback.setHide(false);
            child.show();
        }
    }
}
