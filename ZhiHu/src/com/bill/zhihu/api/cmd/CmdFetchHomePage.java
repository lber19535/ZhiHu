package com.bill.zhihu.api.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.api.net.ZhihuStringRequest;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.api.utils.ZhihuURL;

/**
 * 首页
 * 
 * @author Bill Lv
 *
 */
public class CmdFetchHomePage extends Command {

    private CallbackListener listener;
    private List<TimeLineItem> timelineItems;

    public CmdFetchHomePage() {

        timelineItems = new ArrayList<TimeLineItem>();
    }

    @Override
    public void exec() {
        String url = ZhihuURL.HOST;
        ZhihuStringRequest request = new ZhihuStringRequest(url,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ZhihuLog.d(TAG, response);
                        Document doc = Jsoup.parse(response);
                        // 获取问题列表
                        Elements feedListElements = doc
                                .select("div[id=js-home-feed-list]>div[id^=feed]");
                        int feedListLength = feedListElements.size();

                        System.out.println("feedListLength " + feedListLength);
                        System.out.println("------------");

                        for (Element element : feedListElements) {
                            // avatar
                            Elements avatarElements = element
                                    .select("div[class=feed-item-inner]>div[class=avatar]>a");
                            String avatarName = avatarElements.attr("title");
                            String avatarImgUrl = avatarElements.select("img")
                                    .attr("src");
                            String avatarHome = avatarElements.attr("href");
                            System.out.println("avatarName " + avatarName);
                            System.out.println("avatarImgUrl " + avatarImgUrl);
                            System.out.println("avatarHome " + avatarHome);

                            // 来源
                            Elements sourceElements = element
                                    .select("div[class=feed-main]>div[class=source]");
                            String source = sourceElements.select("a").text();
                            String sourceUrl = sourceElements.select("a").attr(
                                    "href");
                            String rawTime = sourceElements.select(
                                    "span[class=time]").attr("data-timestamp");
                            String timeTips = sourceElements.select(
                                    "span[class=time]").text();
                            // 首页中 来自话题的item是没有时间的
                            boolean isTopic = sourceElements.select("a")
                                    .hasAttr("data-topicid");
                            String typeString = sourceElements.get(0)
                                    .textNodes().get(1).text();
                            System.out.println("source " + source);
                            System.out.println("sourceUrl " + sourceUrl);
                            System.out.println("rawTime " + rawTime);
                            System.out.println("timeTips " + timeTips);
                            System.out.println("typeString " + typeString);
                            System.out.println("isTopic " + isTopic);

                            // 内容
                            Elements contentElements = element
                                    .select("div[class=content]");
                            String question = contentElements.select(
                                    "a[class=question_link]").text();
                            String questionUrl = contentElements.select(
                                    "a[class=question_link]").attr("href");
                            String voteCount = contentElements.select(
                                    "[class=zm-item-vote]>a").text();
                            System.out.println("question " + question);
                            System.out.println("questionUrl " + questionUrl);
                            System.out.println("voteCount " + voteCount);
                            
                            // 是否包含feed-question-detail-item 来确定是否有是否只有问题还是也有回答
                            boolean onlyQuestion = !contentElements.select("div").hasClass("feed-question-detail-item");
                            System.out.println("onlyQuestion " + onlyQuestion);
                            String answer = contentElements.select("div[class=zh-summary summary clearfix]").text();

                            TimeLineItem item = new TimeLineItem();
                            item.setAvatarHome(avatarHome);
                            item.setAvatarImgUrl(avatarImgUrl);
                            item.setAvatarName(avatarName);
                            item.setSource(source);
                            item.setSourceUrl(sourceUrl);
                            item.setTimeStamp(rawTime);
                            item.setTimeTips(timeTips);
                            item.setTopic(isTopic);
                            item.setTypeString(typeString);
                            item.setVoteCount(voteCount);
                            item.setQuestion(question);
                            item.setQuestionUrl(questionUrl);
                            item.setOnlyQuestion(onlyQuestion);
                            item.setAnswerSummary(answer);

                            timelineItems.add(item);
                            System.out.println("------------");
                            System.out.println("time line size is  " + timelineItems.size());
                        }
                        listener.callback(timelineItems);

                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ZhihuLog.d(TAG, error);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                headers.put("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                headers.put("Referer", "http://www.zhihu.com/");
                headers.put("Host", "www.zhihu.com");
                return headers;
            }

        };
        volley.addQueue(request);

    }

    @Override
    public <T extends CommandCallback> void setOnCmdCallBack(T callback) {
        listener = (CallbackListener) callback;
    }

    public interface CallbackListener extends CommandCallback {
        void callback(List<TimeLineItem> timelineItems);
    }

}
