package com.bill.zhihu.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.bugly.crashreport.BuglyLog;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/4/20.
 */
public class ImageLoadUtils {
    private static final String TAG = "ImageLoadUtils";

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

    public static Observable<Drawable> getImageDrawable(final String url) {
        return Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(final Subscriber<? super Drawable> subscriber) {
                ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        subscriber.onError(new Throwable(failReason.getCause()));
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        BitmapDrawable drawable = new BitmapDrawable(null, loadedImage);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }
}
