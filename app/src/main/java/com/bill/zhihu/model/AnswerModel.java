package com.bill.zhihu.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichCcontentUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.BuglyLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/3/29.
 */
public class AnswerModel {
    private static final String TAG = "AnswerModel";
    private static final String DEFAULT_LOADING_IMAGE_URI = "file:///android_asset/default_pic_content_image_loading.gif";

    private static final String TIME_SAMPLE = "<time " +
            "onclick=\"this.innerHTML=(" +
            "this.innerHTML==&quot;" +
            "创建于 %s&quot;?&quot;" +
            "编辑于 %s&quot;:&quot;" +
            "创建于 %s&quot;)\">" +
            "编辑于 %s</time>";

    public Observable<String> getAnswer(String id) {
        return ZhihuApi.getAnswer(id)
                .map(new Func1<SingleAnswerResponse, String>() {
                    @Override
                    public String call(SingleAnswerResponse singleAnswerResponse) {
                        StringBuffer sb = new StringBuffer(singleAnswerResponse.content);
                        sb.append(createTimeTag(singleAnswerResponse.createdTime, singleAnswerResponse.updatedTime));

                        String content = RichCcontentUtils.wrapContent(sb.toString(), Theme.LIGHT);

                        Document doc = Jsoup.parse(content);
                        Elements elements = doc.select("img[src]");
                        for (Element ele : elements) {
                            ele.attr("onclick", "onImageClick(this)");
                            ele.attr("original-src", ele.attributes().get("src"));
                            ele.attr("src", DEFAULT_LOADING_IMAGE_URI);
                        }

                        return doc.toString();
                    }
                }).subscribeOn(Schedulers.io());
    }


    public Observable<String> getImageCacheUri(final String url) {
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

    private String createTimeTag(long createTime, long updateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String createT = format.format(new Date(createTime * 1000L));
        String updateT = format.format(new Date(updateTime * 1000L));
        return String.format(TIME_SAMPLE, createT, updateT, createT, updateT);
    }
}
