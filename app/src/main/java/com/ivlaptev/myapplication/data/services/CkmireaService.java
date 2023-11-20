package com.ivlaptev.myapplication.data.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivlaptev.myapplication.data.CkmireaAPI;
import com.ivlaptev.myapplication.models.UserinfoResponse;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.models.articles.ArticleCompactList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CkmireaService {
    private static CkmireaAPI ckmireaAPI = null;
    private static CkmireaService instance = null;

    private CkmireaService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CkmireaAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ckmireaAPI = retrofit.create(CkmireaAPI.class);
    }

    public static CkmireaService getInstance(Context context) {
        if (instance == null) {
            synchronized(CkmireaService.class) {
                EncryptedStore.getInstance(context);
                instance = new CkmireaService();
            }
        }
        return instance;
    }

    public Call<UserinfoResponse> getUserinfo() {
        return ckmireaAPI.getUserinfo();
    }

    public Call<ArticleCompactList> getMostPopularArticles() {
        return ckmireaAPI.getMostPopularArticles();
    }

    public Call<ArticleCompactList> getAllPopularArticles() {
        return ckmireaAPI.getAllPopularArticles();
    }

    public Call<ArticleCompact> getArticleById(Integer id) { return ckmireaAPI.getArticleBy(id); }
}
