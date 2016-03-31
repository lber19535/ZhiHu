package com.bill.zhihu.util;

import com.bill.zhihu.ui.Theme;

/**
 * Created by bill_lv on 2016/3/30.
 */
public class RichCcontentUtils {

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
        StringBuffer sb = new StringBuffer();
        if (theme == Theme.DARK) {
            sb.append(HTML_DARK_HEADER);
        } else {
            sb.append(HTML_LIGHT_HEADER);
        }
        sb.append(content);
        sb.append(HTML_FOOTER);
        return sb.toString();
    }
}
