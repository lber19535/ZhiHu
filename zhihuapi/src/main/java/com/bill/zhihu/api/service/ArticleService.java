package com.bill.zhihu.api.service;

import com.bill.zhihu.api.bean.response.ArticleResponse;
import com.bill.zhihu.api.bean.response.VoteResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bill_lv on 2016/5/3.
 */
public interface ArticleService {

    @GET("/articles/{id}")
    Observable<ArticleResponse> articles(@Path("id") String articleId);

    @GET("/articles/{id}/is_voted")
    Observable<VoteResponse> isArticleVoted(@Path("id") String articleId);
}
