package com.bill.zhihu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bill.zhihu.vm.answer.RichContentChromeClient;
import com.bill.zhihu.vm.answer.RichContentWebClient;
import com.tencent.bugly.crashreport.BuglyLog;

/**
 * 为了使用 FloatActionButton 做成webview上下滑动也可以有隐藏的效果做了包装了
 * Created by Bill-pc on 2015/7/15.
 */
public class RichContentView extends FrameLayout {

    private WebView webView;


    public RichContentView(Context context) {
        super(context);
    }

    public RichContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        webView = new WebView(context, attrs);
        addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebChromeClient(new RichContentChromeClient());
        webView.setWebViewClient(new RichContentWebClient());

    }

    public RichContentView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @SuppressLint("JavascriptInterface")
    public void bindJs(Object obj, String name) {
        webView.addJavascriptInterface(obj, name);
    }

    public void setContent(String content) {
        webView.loadDataWithBaseURL("file:///android_asset/", content, "text/html; charset=UTF-8", "UTF-8", null);
    }

    public void executeJsMethod(String methodName, String... params) {

        StringBuffer sb = new StringBuffer();

        for (String s : params) {
            sb.append("'");
            sb.append(s);
            sb.append("'");
            sb.append(",");
        }
        int lastDot = sb.lastIndexOf(",");
        if (lastDot != -1)
            sb.deleteCharAt(lastDot);

        String cmd = "javascript:" + methodName + "(" + sb.toString() + ");";
        BuglyLog.d("JS ",cmd);

        webView.loadUrl(cmd);
    }

}
