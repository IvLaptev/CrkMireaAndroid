package com.ivlaptev.myapplication.data;

import com.ivlaptev.myapplication.models.UserinfoResponse;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.models.articles.ArticleCompactList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CkmireaAPI {
    String BASE_URL = "https://ck.mirea.ru/";

    @GET("mock/userinfo")
    Call<UserinfoResponse> getUserinfo();

    @GET("api/blog/articles/?page_size=5&page=1&order=-created_date")
    Call<ArticleCompactList> getMostPopularArticles();

    @GET("api/blog/articles/?order=-created_date")
    Call<ArticleCompactList> getAllPopularArticles();

    @GET("api/blog/articles/{article_id}")
    Call<ArticleCompact> getArticleBy(@Path("article_id") Integer article_id);
}
