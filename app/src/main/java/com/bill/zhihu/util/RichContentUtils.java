package com.bill.zhihu.util;

import com.bill.zhihu.ui.Theme;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bill_lv on 2016/3/30.
 */
public class RichContentUtils {

    private static final String TAG = "RichContentUtils";

    private static final String HTML_CONTENT_LIGHT_HEADER = "<!doctype html>\n" +
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

    private static final String HTML_CONTENT_DARK_HEADER =
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

    private static final String HTML_TOPIC_LIGHT_HEADER = "<!doctype html>\n" +
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
            "\t\t<div id=\"topic\">";

    private static final String HTML_TOPIC_DARK_HEADER =
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
                    "\t\t<div id=\"topic\">";


    private static final String HTML_FOOTER = "</div>\n" +
            "\t\t<script src=\"file:///android_asset/zepto.min.js\">\n" +
            "\t\t</script>\n" +
            "\t\t<script src=\"file:///android_asset/javascript.js\">\n" +
            "\t\t</script>\n" +
            "\t</body>\n" +
            "</html>";

    private static final String DEFAULT_LOADING_IMAGE_URI = "file:///android_asset/default_pic_content_image_loading.gif";

    private static final String TIME_SAMPLE = "<time " +
            "onclick=\"this.innerHTML=(" +
            "this.innerHTML==&quot;" +
            "创建于 %s&quot;?&quot;" +
            "编辑于 %s&quot;:&quot;" +
            "创建于 %s&quot;)\">" +
            "编辑于 %s</time>";

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
            sb.append(HTML_CONTENT_DARK_HEADER);
        } else {
            sb.append(HTML_CONTENT_LIGHT_HEADER);
        }
        sb.append(content);
        sb.append(HTML_FOOTER);
        return sb.toString();
    }

    /**
     * Wrap the question detail for webview data
     *
     * @param content
     * @param theme
     * @return
     */
    public static String wrapTopic(String content, Theme theme) {
        StringBuilder sb = new StringBuilder();
        if (theme == Theme.DARK) {
            sb.append(HTML_TOPIC_DARK_HEADER);
        } else {
            sb.append(HTML_TOPIC_LIGHT_HEADER);
        }
        sb.append(content);
        sb.append(HTML_FOOTER);
        return sb.toString();
    }

    public static String replaceImage(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("img[src]");
        for (Element ele : elements) {
            ele.attr("onclick", "onImageClick(this)");
            ele.attr("original-src", ele.attributes().get("src"));
            ele.attr("src", DEFAULT_LOADING_IMAGE_URI);
        }
        return doc.toString();
    }

    public static String createTimeTag(long createTime, long updateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String createT = format.format(new Date(createTime * 1000L));
        String updateT = format.format(new Date(updateTime * 1000L));
        return String.format(TIME_SAMPLE, createT, updateT, createT, updateT);
    }
}
