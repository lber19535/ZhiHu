package com.bill.zhihu.api.utils;

import android.widget.Toast;

import com.bill.zhihu.api.ZhihuApi;

public class ToastUtil {

    public static void showShortToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    private static void showToast(String text, int duration) {
        Toast.makeText(ZhihuApi.getContext(), text, duration).show();
    }
}
