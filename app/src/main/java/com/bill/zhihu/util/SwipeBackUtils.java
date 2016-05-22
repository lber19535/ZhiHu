package com.bill.zhihu.util;

import android.app.Activity;

import com.bill.zhihu.R;
import com.bill.zhihu.transformer.ZhihuSwipeBackTransformer;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by bill_lv on 2016/5/6.
 */
public class SwipeBackUtils {

    public static void attach(Activity activity,int contentViewId) {
        SwipeBack.attach(activity, SwipeBack.Type.BEHIND, Position.LEFT, SwipeBack.DRAG_CONTENT)
                .setContentView(contentViewId)
                .setSwipeBackTransformer(new ZhihuSwipeBackTransformer())
                .setSwipeBackView(R.layout.layout_swipeback);
    }
}
