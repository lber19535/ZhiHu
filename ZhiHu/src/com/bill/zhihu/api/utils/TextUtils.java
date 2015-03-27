package com.bill.zhihu.api.utils;

import java.util.List;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * 
 * text 工具类
 * 
 * @author Bill Lv
 *
 */
public class TextUtils {

    /**
     * 给String着色
     * 
     * @param src
     *            源字符串
     * @param color
     *            着色color
     * @param target
     *            着色字段
     * @return
     */
    public static SpannableStringBuilder getColorString(String src, int color,
            List<String> targets) {
        return getColorString(src, color, targets.toArray(new String[] {}));
    }

    /**
     * 给String着色
     * 
     * @param src
     *            源字符串
     * @param color
     *            着色color
     * @param target
     *            着色字段
     * @return
     */
    public static SpannableStringBuilder getColorString(String src, int color,
            String... targets) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(src);
        for (String string : targets) {
            int start = src.indexOf(string);
            int end = start + string.length();
            ForegroundColorSpan fcs = new ForegroundColorSpan(color);
            ssb.setSpan(fcs, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return ssb;
    }

}
