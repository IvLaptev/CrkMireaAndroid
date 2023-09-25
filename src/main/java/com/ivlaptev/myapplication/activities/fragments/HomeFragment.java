package com.ivlaptev.myapplication.activities.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.CkmireaService;
import com.ivlaptev.myapplication.data.services.DbHelper;
import com.ivlaptev.myapplication.data.services.EncryptedStore;
import com.ivlaptev.myapplication.models.UserinfoResponse;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.models.articles.ArticleCompactList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    CkmireaService ckmireaService;
    DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ckmireaService = CkmireaService.getInstance(getContext());
        dbHelper = new DbHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        getView().findViewById(R.id.logout_btn).setOnClickListener(clickedView -> {
            EncryptedStore.getInstance(context).clear();

            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_logout);
        });

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView articlesList = getView().findViewById(R.id.popular_articles_list);
        articlesList.setLayoutManager(layoutManager);

        ckmireaService.getUserinfo().enqueue(userinfoCallback);
        ckmireaService.getMostPopularArticles().enqueue(articleCompactListCallback);
    }

    Callback<UserinfoResponse> userinfoCallback = new Callback<UserinfoResponse>() {
        @Override
        public void onResponse(Call<UserinfoResponse> call, Response<UserinfoResponse> response) {
            if (response.isSuccessful()) {
                TextView tv = getView().findViewById(R.id.welcome_name);

                Resources res = getResources();
                tv.setText(res.getString(R.string.welcome, response.body().firstname));
            } else {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_logout);
            }
        }

        @Override
        public void onFailure(Call<UserinfoResponse> call, Throwable t) {
            Log.i("ERROR", t.toString());
        }
    };

    Callback<ArticleCompactList> articleCompactListCallback = new Callback<ArticleCompactList>() {
        @Override
        public void onResponse(Call<ArticleCompactList> call, Response<ArticleCompactList> response) {
            if (response.isSuccessful()) {
                List<ArticleCompact> articles = response.body().getResults();

                RecyclerView recyclerView = getView().findViewById(R.id.popular_articles_list);
                ArticlesStateAdapter adapter = new ArticlesStateAdapter(getContext(), articles, getActivity());
                recyclerView.setAdapter(adapter);
                dbHelper.insertArticles(articles);
            } else {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_logout);
            }
        }

        @Override
        public void onFailure(Call<ArticleCompactList> call, Throwable t) {
            List<ArticleCompact> articles = dbHelper.getPopularArticles(5);

            RecyclerView recyclerView = getView().findViewById(R.id.popular_articles_list);
            ArticlesStateAdapter adapter = new ArticlesStateAdapter(getContext(), articles, getActivity());
            recyclerView.setAdapter(adapter);
        }
    };
}

