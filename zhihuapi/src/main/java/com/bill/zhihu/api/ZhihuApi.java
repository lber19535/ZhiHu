package com.bill.zhihu.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.bill.zhihu.api.cmd.CmdLoadAnswer;
import com.bill.zhihu.api.cmd.CmdLoadHomePage;
import com.bill.zhihu.api.cmd.CmdLoadMore;
import com.bill.zhihu.api.cmd.CmdLoadQuestion;
import com.bill.zhihu.api.cmd.CmdLogin;
import com.bill.zhihu.api.cmd.Command;
import com.bill.zhihu.api.net.URLCookiesStore;
import com.bill.zhihu.api.net.ZhihuCookieManager;

/**
 * 知乎api总的接口
 */
public class ZhihuApi {

    private static final String XSRF = "_xsrf";

    private static Context mContext;

    public static void execCmd(Command cmd) {
        cmd.exec();
    }

    public static void cancelCmd(Command cmd) {
        cmd.cancel();
    }

    /**
     * Use this in Application class to set the globel context to API
     *
     * @param context globel context
     */
    public static void registerContext(Context context){
        mContext = context;
    }

    public static Context getContext(){
        return mContext;
    }

    public static Resources getRes(){
        return mContext.getResources();
    }

    /**
     * 清除cookies
     */
    public static void clearCookies() {
        URLCookiesStore.getInstance().removeAll();
    }

    /**
     * if don't have xsrf or don't fetch xsrf value return null
     *
     * @return
     */
    public static String getXSRF() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (ZhihuCookieManager.haveCookieName(ZhihuApi.XSRF)) {
            return ZhihuCookieManager.getCookieValue(ZhihuApi.XSRF);
        } else {
            return sp.getString(ZhihuApi.XSRF, null);
        }
    }

    public static void setXSRF(String xsrf) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        sp.edit().putString(XSRF, xsrf).commit();
    }

    /**
     * 加载首页
     *
     * @param listener
     */
    public static void loadHomePage(CmdLoadHomePage.CallbackListener listener) {
        CmdLoadHomePage cmd = new CmdLoadHomePage();
        cmd.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(cmd);
    }

    /**
     * 加载更多
     *
     * @param start
     * @param offset
     * @param listener
     */
    public static void loadMore(int start, int offset, CmdLoadMore.CallbackListener listener) {
        CmdLoadMore loadMore = new CmdLoadMore(start, offset);
        loadMore.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(loadMore);
    }

    /**
     * 加载更多
     *
     * @param blockId
     * @param offset
     * @param listener
     */
    @Deprecated
    public static void loadMore(long blockId, int offset, CmdLoadMore.CallbackListener listener) {
        CmdLoadMore loadMore = new CmdLoadMore(blockId, offset);
        loadMore.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(loadMore);
    }


    /**
     * 登陆
     *
     * @param account
     * @param pwd
     * @param captcha
     * @param listener
     */
    public static void login(String account, String pwd, String captcha, CmdLogin.CallbackListener listener) {
        CmdLogin login = new CmdLogin(account, pwd, captcha);
        login.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(login);
    }

    /**
     * 获取答案
     *
     * @param answerUrl
     * @param listener
     */
    public static void loadAnswer(String answerUrl, CmdLoadAnswer.CallBackListener listener) {
        CmdLoadAnswer loadAnswer = new CmdLoadAnswer(answerUrl);
        loadAnswer.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(loadAnswer);
    }

    public static void loadQuestionPage(String url, CmdLoadQuestion.CallBackListener listener) {
        CmdLoadQuestion loadQuestion = new CmdLoadQuestion(url);
        loadQuestion.setOnCmdCallBack(listener);
        ZhihuApi.execCmd(loadQuestion);
    }
}
