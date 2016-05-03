package com.bill.zhihu.transformer;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bill.zhihu.R;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SwipeBackTransformer;

/**
 * Created by bill_lv on 2016/4/20.
 */
public class ZhihuSwipeBackTransformer implements SwipeBackTransformer {

    private AppCompatImageView backIv;

    @Override
    public void onSwipeBackViewCreated(SwipeBack swipeBack, Activity activity, View swipeBackView) {
        backIv = (AppCompatImageView) swipeBackView.findViewById(R.id.back_arrow);
    }

    @Override
    public void onSwipeBackCompleted(SwipeBack swipeBack, Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.swipeback_slide_left_in, R.anim.swipeback_slide_right_out);
    }

    @Override
    public void onSwipeBackReseted(SwipeBack swipeBack, Activity activity) {

    }

    @Override
    public void onSwiping(SwipeBack swipeBack, float openRatio, int pixelOffset) {
        float scaleFactor = 0.9f + openRatio * 0.2f;
        backIv.setScaleX(scaleFactor);
        backIv.setScaleY(scaleFactor);
    }
}
