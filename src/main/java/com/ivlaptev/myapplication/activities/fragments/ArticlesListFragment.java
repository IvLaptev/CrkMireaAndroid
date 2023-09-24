package com.ivlaptev.myapplication.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.CkmireaService;
import com.ivlaptev.myapplication.data.services.DbHelper;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.models.articles.ArticleCompactList;
import com.ivlaptev.myapplication.shared.PermanentRedirect;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesListFragment extends Fragment {
    CkmireaService ckmireaService;
    DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ckmireaService = CkmireaService.getInstance(getContext());
        dbHelper = new DbHelper(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView articlesList = getView().findViewById(R.id.articles_list);
        articlesList.setLayoutManager(layoutManager);

        ckmireaService.getAllPopularArticles().enqueue(articleCompactListCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles_list, container, false);
    }

    Callback<ArticleCompactList> articleCompactListCallback = new Callback<ArticleCompactList>() {
        @Override
        public void onResponse(Call<ArticleCompactList> call, Response<ArticleCompactList> response) {
            if (response.isSuccessful()) {
                List<ArticleCompact> articles = response.body().getResults();

                RecyclerView recyclerView = getView().findViewById(R.id.articles_list);
                ArticlesStateAdapter adapter = new ArticlesStateAdapter(getContext(), articles, getActivity());
                recyclerView.setAdapter(adapter);
                dbHelper.insertArticles(articles);
            } else {
                PermanentRedirect.toLoginActivity(getContext());
            }
        }

        @Override
        public void onFailure(Call<ArticleCompactList> call, Throwable t) {
            List<ArticleCompact> articles = dbHelper.getPopularArticles(null);

            RecyclerView recyclerView = getView().findViewById(R.id.articles_list);
            ArticlesStateAdapter adapter = new ArticlesStateAdapter(getContext(), articles, getActivity());
            recyclerView.setAdapter(adapter);
        }
    };
}
