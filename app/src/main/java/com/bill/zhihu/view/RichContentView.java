package com.bill.zhihu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.model.FontSize;
import com.bill.zhihu.ui.ActivityLargeImage;
import com.bill.zhihu.util.ImageLoadUtils;
import com.bill.zhihu.presenter.answer.RichContentChromeClient;
import com.bill.zhihu.presenter.answer.RichContentWebClient;
import com.bill.zhihu.util.ImageUrlUtils;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tramsun.libs.prefcompat.Pref;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 为了使用 FloatActionButton 做成webview上下滑动也可以有隐藏的效果做了包装了
 * Created by Bill-pc on 2015/7/15.
 */
public class RichContentView extends WebView {

    private static String TAG = "RichContentView";

    private ArrayList<CharSequence> imgHDUrl = new ArrayList<>();

    public RichContentView(Context context) {
        super(context);
    }

    public RichContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSettings().setAppCacheEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowContentAccess(true);
        getSettings().setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setWebContentsDebuggingEnabled(false);
        }
        setWebChromeClient(new RichContentChromeClient());
        setWebViewClient(new RichContentWebClient());
        addJavascriptInterface(this, "ZhihuAndroid");
    }

    public RichContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(String content) {
        loadDataWithBaseURL("file:///android_asset/", content, "text/html; charset=UTF-8", "UTF-8", null);
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

        loadUrl(cmd);
    }

    @JavascriptInterface
    public void loadImage(final String url) {

        imgHDUrl.add(url);

        ImageLoadUtils.getImageCacheUri(url)
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

    @JavascriptInterface
    public String getFontSize() {

        String fontSize = Pref.getString(FontSize.RICH_CONTENT_VIEW_FONT_KEY, FontSize.NORMAL);

        return fontSize;
    }

    @JavascriptInterface
    public void setFontSize(String fontSize) {
        executeJsMethod("setFontSize", fontSize);
    }

    @JavascriptInterface
    public void openImage(String url) {
        Intent intent = new Intent(getContext(), ActivityLargeImage.class);
        intent.setAction(IntentConstant.INTENT_ACTION_LARGE_IMAGE);
        intent.putExtra(IntentConstant.INTENT_LARGE_IMAGE_URL, ImageUrlUtils.webimgSmall2Normal(url));
        intent.putCharSequenceArrayListExtra(IntentConstant.INTENT_LARGE_IMAGE_URL_ARRAY, imgHDUrl);
        getContext().startActivity(intent);
        Logger.d(url);
    }

    @JavascriptInterface
    public void debug(String msg) {
        Logger.d(msg);
    }

}
