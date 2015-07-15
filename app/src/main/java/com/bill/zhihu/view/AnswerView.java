package com.bill.zhihu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.melnykov.fab.ObservableScrollView;

/**
 * 为了使用 FloatActionButton 做成webview上下滑动也可以有隐藏的效果做了包装了
 * Created by Bill-pc on 2015/7/15.
 */
public class AnswerView extends ObservableScrollView {

    private WebView webView;

    public AnswerView(Context context) {
        super(context);
    }

    public AnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        webView = new WebView(context, attrs);
        addView(webView);
    }

    public AnswerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取webview
     *
     * @return
     */
    public WebView getWebView() {
        return webView;
    }

}
