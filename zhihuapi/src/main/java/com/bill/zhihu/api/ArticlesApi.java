package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.ArticleResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.ArticleService;

import rx.Observable;

/**
 * Created by bill_lv on 2016/5/19.
 */
public class ArticlesApi implements API {

    private ArticleService service;

    public ArticlesApi(ArticleService service) {
        this.service = service;
    }


    public Observable<ArticleResponse> getArticle(String articleId) {
        return service.articles(articleId);
    }

    public Observable<VoteResponse> isArticleVoted(String articleId) {
        return service.isArticleVoted(articleId);
    }
}
