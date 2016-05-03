package com.bill.zhihu.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by bill_lv on 2016/4/15.
 */
public class AnimeUtils {

    public static AnimatorSet createScaleShowAnime(View view) {
        ObjectAnimator showAlpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        ObjectAnimator showScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        ObjectAnimator showScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(showAlpha, showScaleX, showScaleY);
        return set;
    }

    public static AnimatorSet createScaleHideAnime(View view) {
        ObjectAnimator hideAlpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        ObjectAnimator hideScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
        ObjectAnimator hideScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(hideAlpha, hideScaleX, hideScaleY);
        return set;
    }
}
