package com.bill.zhihu.api;

import android.content.Context;
import android.content.res.Resources;

import com.bill.zhihu.api.bean.FeedsResponse;
import com.bill.zhihu.api.cookie.URLCookiesStore;
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
        URLCookiesStore.getInstance().removeAll();
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
     * @param id in last {@link com.bill.zhihu.api.bean.FeedsItem}
     * @return
     */
    public static Observable<FeedsResponse> loadMore(String id) {
        return ApiFactory.createFeedsApi().getFeedsById(id);
    }

    /**
     * 加载更多
     *
     * @param blockId
     * @param offset
     * @param listener
     */
//    @Deprecated
//    public static void loadMore(long blockId, int offset, CmdLoadMore.CallbackListener listener) {
//        CmdLoadMore loadMore = new CmdLoadMore(blockId, offset);
//        loadMore.setOnCmdCallBack(listener);
//        ZhihuApi.execCmd(loadMore);
//    }

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
     * 获取答案
     *
     * @param answerUrl
     * @param listener
     */
//    public static void loadAnswer(String answerUrl, CmdLoadAnswer.CallBackListener listener) {
//        CmdLoadAnswer loadAnswer = new CmdLoadAnswer(answerUrl);
//        loadAnswer.setOnCmdCallBack(listener);
//        ZhihuApi.execCmd(loadAnswer);
//    }
//
//    public static void loadQuestionPage(String url, CmdLoadQuestion.CallBackListener listener) {
//        CmdLoadQuestion loadQuestion = new CmdLoadQuestion(url);
//        loadQuestion.setOnCmdCallBack(listener);
//        ZhihuApi.execCmd(loadQuestion);
//    }

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
