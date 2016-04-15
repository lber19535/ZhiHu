package com.bill.zhihu.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.bill.zhihu.ui.Theme;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.bugly.crashreport.BuglyLog;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/30.
 */
public class RichCcontentUtils {

    private static final String TAG = "RichCcontentUtils";

    private static final String HTML_LIGHT_HEADER = "<!doctype html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\">\n" +
            "\t\t<title>Zhihu Android WebView</title>\n" +
            "\t\t<link rel=\"stylesheet\" href=\"file:///android_asset/style.css\">\n" +
            "\t\t<meta name=\"android-mobile-web-app-capable\" content=\"yes\" />\n" +
            "\t\t<meta name=\"android-mobile-web-app-status-bar-style\" content=\"black\" />\n" +
            "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\" />\n" +
            "\t</head>\n" +
            "\t<body class=\"typo\" id=\"answer\" onload=\"onLoaded()\" >\n" +
            "\t\t<div id=\"content\">";

    private static final String HTML_DARK_HEADER =
            "<!doctype html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" +
                    "\t\t<title>Zhihu Android WebView</title>\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"file:///android_asset/style.css\">\n" +
                    "\t\t<meta name=\"android-mobile-web-app-capable\" content=\"yes\" />\n" +
                    "\t\t<meta name=\"android-mobile-web-app-status-bar-style\" content=\"black\" />\n" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\" />\n" +
                    "\t</head>\n" +
                    "\t<body class=\"typo dark\" id=\"answer\" onload=\"onLoaded()\" >\n" +
                    "\t\t<div id=\"content\">";


    private static final String HTML_FOOTER = "</div>\n" +
            "\t\t<script src=\"file:///android_asset/zepto.min.js\">\n" +
            "\t\t</script>\n" +
            "\t\t<script src=\"file:///android_asset/javascript.js\">\n" +
            "\t\t</script>\n" +
            "\t</body>\n" +
            "</html>";

    /**
     * Wrap the content for webview data
     *
     * @param content
     * @param theme
     * @return
     */
    public static String wrapContent(String content, Theme theme) {
        StringBuilder sb = new StringBuilder();
        if (theme == Theme.DARK) {
            sb.append(HTML_DARK_HEADER);
        } else {
            sb.append(HTML_LIGHT_HEADER);
        }
        sb.append(content);
        sb.append(HTML_FOOTER);
        return sb.toString();
    }

    public static Observable<String> getImageCacheUri(final String url) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        BuglyLog.d(TAG, "load img " + imageUri);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        subscriber.onError(new Throwable(failReason.getCause()));
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        File file = ImageLoader.getInstance().getDiskCache().get(imageUri);
                        subscriber.onNext(Uri.fromFile(file).toString());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        subscriber.onError(new Throwable("loading cancelled"));
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }
}
