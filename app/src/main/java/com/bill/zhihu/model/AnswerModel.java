package com.bill.zhihu.model;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichCcontentUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
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
                        StringBuilder sb = new StringBuilder(singleAnswerResponse.content);
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

    private String createTimeTag(long createTime, long updateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String createT = format.format(new Date(createTime * 1000L));
        String updateT = format.format(new Date(updateTime * 1000L));
        return String.format(TIME_SAMPLE, createT, updateT, createT, updateT);
    }
}
