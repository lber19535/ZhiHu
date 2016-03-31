package com.bill.zhihu.api;

import android.content.Context;
import android.content.res.Resources;

import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.bean.response.QuestionResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.cookie.PersistentCookiesStore;
import com.bill.zhihu.api.factory.ApiFactory;

import rx.Observable;

/**
 * 知乎api总的接口
 */
public class ZhihuApi {

    private static Context mContext;

    /**
     * Use this in Application class to set the globel context to API
     *
     * @param context globel context
     */
    public static void registerContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        if (mContext == null)
            try {
                throw new Throwable("Please register global context in Application");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return mContext;
    }

    public static Resources getRes() {
        return mContext.getResources();
    }

    /**
     * 清除cookies
     */
    public static void clearCookies() {
        PersistentCookiesStore.getInstance().removeAll();
    }

    /**
     * 加载首页
     *
     * @return
     */
    public static Observable<FeedsResponse> loadHomePage() {
        return ApiFactory.createFeedsApi().getFeeds();
    }

    /**
     * 加载更多
     *
     * @param id in last {@link FeedsItem}
     * @return
     */
    public static Observable<FeedsResponse> loadMore(int id) {
        return ApiFactory.createFeedsApi().nextPage(id);
    }

    /**
     * 初次登陆之前必须调用，用来去掉验证码
     *
     * @return 如果请求成功则会正常 next，失败则会 error
     */
    public static Observable<Void> captcha() {
        return ApiFactory.createLoginApi().captcha();
    }

    /**
     * 登陆
     *
     * @param username
     * @param pwd
     */
    public static Observable<Boolean> login(String username, String pwd) {
        return ApiFactory.createLoginApi().login(username, pwd);
    }

    /**
     * 加载问题，包含问题详细描述，topic，作者等信息{@link QuestionResponse}
     *
     * @param questionId 问题  id
     * @return
     */
    public static Observable<QuestionResponse> getQuestion(long questionId) {
        return ApiFactory.createQuestionApi().getQuestion(String.valueOf(questionId));
    }

    /**
     * 加载该问题下的最开始的 20 条答案，加载后续答案需要使用{@link #getNextAnswers(long, int)}
     *
     * @param questionId 问题 id
     * @return
     */
    public static Observable<AnswersResponse> getAnswers(long questionId) {
        return ApiFactory.createAnswerApi().getAnswers(String.valueOf(questionId));
    }

    /**
     * 加载该问题下更多的答案
     *
     * @param questionId 问题 id
     * @param offset 已加载回答的个数
     * @return
     */
    public static Observable<AnswersResponse> getNextAnswers(long questionId, int offset) {
        return ApiFactory.createAnswerApi().nextPage(String.valueOf(questionId), offset);
    }

    /**
     * 获取答案
     *
     * @param answerId
     * @return
     */
    public static Observable<SingleAnswerResponse> getAnswer(long answerId) {
        return ApiFactory.createAnswerApi().getAnswer(String.valueOf(answerId));
    }

    /**
     * 是否已登陆
     *
     * @return
     */
    public static Observable<Boolean> haveLogin() {

        return ApiFactory.createLoginApi().haveLogin();
    }

    /**
     * 注销登陆状态
     *
     * @return
     */
    public static Observable<Boolean> logout() {
        return null;
    }

    public static void getPeopleSelfBasic() {
        ApiFactory.createPeopleApi().getSelfBasic();
    }
}
