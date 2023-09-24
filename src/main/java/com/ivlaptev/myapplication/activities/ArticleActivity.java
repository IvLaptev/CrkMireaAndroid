package com.ivlaptev.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.CkmireaService;
import com.ivlaptev.myapplication.data.services.DbHelper;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.shared.PermanentRedirect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends AppCompatActivity {
    private Integer article_id;

    private CkmireaService ckmireaService;
    private DbHelper dbHelper;

    private TextView articleView;
    private TextView articleTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        article_id = getIntent().getExtras().getInt("article_id");
        articleView = findViewById(R.id.article_content);
        articleTitleView = findViewById(R.id.article_title);

        ckmireaService = CkmireaService.getInstance(this);
        ckmireaService.getArticleById(article_id).enqueue(articleCompactCallback);

        dbHelper = new DbHelper(this);
    }

    Callback<ArticleCompact> articleCompactCallback = new Callback<ArticleCompact>() {
        @Override
        public void onResponse(Call<ArticleCompact> call, Response<ArticleCompact> response) {
            if (response.isSuccessful()) {
                ArticleCompact article = response.body();

                articleTitleView.setText(article.getTitle());
                articleView.setText(Html.fromHtml(article.getContent(), Html.FROM_HTML_MODE_COMPACT));
                dbHelper.updateArticle(article);
            } else {
                PermanentRedirect.toMainActivity(ArticleActivity.this);
            }
        }

        @Override
        public void onFailure(Call<ArticleCompact> call, Throwable t) {
            ArticleCompact article = dbHelper.getArticleById(article_id);

            articleTitleView.setText(article.getTitle());
            articleView.setText(Html.fromHtml(article.getContent(), Html.FROM_HTML_MODE_COMPACT));
        }
    };
}