package com.bill.zhihu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.util.RichCcontentUtils;
import com.bill.zhihu.vm.answer.RichContentChromeClient;
import com.bill.zhihu.vm.answer.RichContentWebClient;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.BuglyLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 为了使用 FloatActionButton 做成webview上下滑动也可以有隐藏的效果做了包装了
 * Created by Bill-pc on 2015/7/15.
 */
public class RichContentView extends FrameLayout {

    private static String TAG = "RichContentView";

    private WebView webView;


    public RichContentView(Context context) {
        super(context);
        if (isInEditMode())
            return;
    }

    public RichContentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

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

        StringBuilder sb = new StringBuilder();

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
        BuglyLog.d("JS ", cmd);

        webView.loadUrl(cmd);
    }

    @JavascriptInterface
    public void loadImage(final String url) {

        RichCcontentUtils.getImageCacheUri(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        BuglyLog.d(TAG, "image load complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        BuglyLog.d(TAG, e.toString());
                        executeJsMethod("onImageLoadingFailed", url);
                    }

                    @Override
                    public void onNext(String uri) {
                        BuglyLog.d(TAG, "load image " + url + " in cache " + uri);
                        try {
                            executeJsMethod("onImageLoadingComplete", URLEncoder.encode(url, "utf-8"), uri);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void changeWebviewFontSize(String size) {
        executeJsMethod("setFontSize", size);
    }

    @JavascriptInterface
    public String getFontSize() {

        float fontSize = getContext().getResources().getDimension(R.dimen.default_answer_font);

        return fontSize + "";
    }

    @JavascriptInterface
    public void openImage(String url) {
        Logger.d(url);
    }

    @JavascriptInterface
    public void debug(String msg) {
        Logger.d(msg);
    }

}
