package com.bill.zhihu.presenter;

import android.app.Activity;

import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.common.Author;
import com.bill.zhihu.api.bean.response.ArticleResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
import com.bill.zhihu.databinding.ArticlesViewBinding;
import com.bill.zhihu.model.FontSize;
import com.bill.zhihu.ui.Theme;
import com.bill.zhihu.util.RichContentUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.tramsun.libs.prefcompat.Pref;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/5/19.
 */
public class ArticlesPresenter {

    ArticlesViewBinding binding;
    Activity activity;

    public ArticlesPresenter(ArticlesViewBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
    }

    public void setAuthor(Author author) {
        String avatarUrl = author.avatarUrl.replace("_s", "_l");
        ImageLoader.getInstance().displayImage(avatarUrl, binding.avatar);
        binding.name.setText(author.name);
        binding.intro.setText(author.headline);
    }


    public void setVoteupCount(String voteupCount) {
        binding.vote.setText(voteupCount);
    }

    public void playLoadingAnim() {
        binding.loadingImg.spin();
    }

    public void loadArticle(String articleId) {
        ZhihuApi.getArticle(articleId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ArticleResponse, String>() {
                    @Override
                    public String call(ArticleResponse articleResponse) {
                        String content = RichContentUtils.wrapContent(
                                articleResponse.content
                                        + RichContentUtils.createTimeTag(articleResponse.created, articleResponse.updated)
                                , Theme.LIGHT);
                        return RichContentUtils.replaceImage(content);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        binding.loadingImg.stopSpinning();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        binding.loadingImg.stopSpinning();
                    }

                    @Override
                    public void onNext(String content) {
                        binding.answer.setContent(content);
                    }
                });
    }

    public void setAnswerFontSize(String fontSize) {
        binding.answer.setFontSize(fontSize);
        Pref.putString(FontSize.RICH_CONTENT_VIEW_FONT_KEY, fontSize);
    }

    public void getArticleVoting(String articleId) {
        ZhihuApi.getArticleVoting(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VoteResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VoteResponse articleResponse) {
                        binding.vote.setText(articleResponse.voteup_count);
                    }
                });
    }

}
