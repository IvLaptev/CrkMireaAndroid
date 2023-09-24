package com.ivlaptev.myapplication.data.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivlaptev.myapplication.BuildConfig;
import com.ivlaptev.myapplication.data.CkmireaIdentityAPI;
import com.ivlaptev.myapplication.models.TokenResponse;
import com.ivlaptev.myapplication.models.UserCredentials;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentityService {
    private static CkmireaIdentityAPI identityAPI = null;
    private static IdentityService instance = null;

    private IdentityService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CkmireaIdentityAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        identityAPI = retrofit.create(CkmireaIdentityAPI.class);
    }

    public static IdentityService getInstance(Context context) {
        if (instance == null) {
            synchronized(IdentityService.class) {
                EncryptedStore.getInstance(context);
                instance = new IdentityService();
            }
        }
        return instance;
    }

    public Call<TokenResponse> login(String login, String password) {
        return identityAPI.login(
                login,
                password,
                "password",
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET
        );
    }
}
