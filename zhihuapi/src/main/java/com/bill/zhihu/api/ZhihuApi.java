package com.bill.zhihu.api;

import android.content.Context;
import android.content.res.Resources;

import com.bill.zhihu.api.bean.feeds.FeedsItem;
import com.bill.zhihu.api.bean.response.AnswersResponse;
import com.bill.zhihu.api.bean.response.FeedsResponse;
import com.bill.zhihu.api.bean.response.GetCaptchaResponse;
import com.bill.zhihu.api.bean.response.NoHelpResponse;
import com.bill.zhihu.api.bean.response.PeopleBasicResponse;
import com.bill.zhihu.api.bean.response.PostCaptchaResponse;
import com.bill.zhihu.api.bean.response.QuestionResponse;
import com.bill.zhihu.api.bean.response.ShowCaptchaResponse;
import com.bill.zhihu.api.bean.response.SingleAnswerResponse;
import com.bill.zhihu.api.bean.response.ThankResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
import com.bill.zhihu.api.cookie.PersistentCookiesStore;
import com.bill.zhihu.api.factory.ApiFactory;
import com.bill.zhihu.api.utils.XHeaders;

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
    public static void init(Context context) {
        mContext = context;
        // init restful api headers
        XHeaders.init(context);
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
     * 初次登陆之前必须调用，用来验证是否需要验证码
     *
     * @return
     */
    public static Observable<ShowCaptchaResponse> showCaptcha() {
        return ApiFactory.createLoginApi().showCaptcha();
    }

    /**
     * 初次登陆之前必须调用，用来获取验证码
     *
     * @return 如果请求成功则会正常 next，失败则会 error
     */
    public static Observable<GetCaptchaResponse> getCaptcha() {
        return ApiFactory.createLoginApi().getCaptcha();
    }


    /**
     * 初次登陆之前必须调用，用来获取验证码
     *
     * @return 如果请求成功则会正常 next，失败则会 error
     */
    public static Observable<PostCaptchaResponse> postCaptcha(String captcha) {
        return ApiFactory.createLoginApi().postCaptcha(captcha);
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
    public static Observable<QuestionResponse> getQuestion(String questionId) {
        return ApiFactory.createQuestionApi().getQuestion(questionId);
    }

    /**
     * 加载该问题下的最开始的 20 条答案，加载后续答案需要使用{@link #getNextAnswers(String, int)}
     *
     * @param questionId 问题 id
     * @return
     */
    public static Observable<AnswersResponse> getAnswers(String questionId) {
        return ApiFactory.createAnswerApi().getAnswers(questionId);
    }

    /**
     * 加载该问题下更多的答案
     *
     * @param questionId 问题 id
     * @param offset     已加载回答的个数
     * @return
     */
    public static Observable<AnswersResponse> getNextAnswers(String questionId, int offset) {
        return ApiFactory.createAnswerApi().nextPage(questionId, offset);
    }

    /**
     * 获取答案
     *
     * @param answerId
     * @return
     */
    public static Observable<SingleAnswerResponse> getAnswer(String answerId) {
        return ApiFactory.createAnswerApi().getAnswer(answerId);
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

    public static Observable<PeopleBasicResponse> getPeopleSelfBasic() {
        return ApiFactory.createPeopleApi().getSelfBasic();
    }

    /**
     * @param answerId
     * @param voteType must use {@link com.bill.zhihu.api.bean.common.VoteType}
     */
    public static Observable<VoteResponse> vote(String answerId, int voteType){
        return ApiFactory.createAnswerApi().vote(answerId, voteType);
    }

    public static Observable<NoHelpResponse> nohelp(String answerId){
        return ApiFactory.createAnswerApi().nohelp(answerId);
    }

    public static Observable<NoHelpResponse> cancelNohelp(String answerId){
        return ApiFactory.createAnswerApi().cancelNohelp(answerId);
    }


    public static Observable<ThankResponse> thanks(String answerId){
        return ApiFactory.createAnswerApi().thanks(answerId);
    }

    public static Observable<ThankResponse> cancelThanks(String answerId){
        return ApiFactory.createAnswerApi().cancelThanks(answerId);
    }
}
