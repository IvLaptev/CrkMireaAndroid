package com.ivlaptev.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ivlaptev.myapplication.data.services.EncryptedStore;
import com.ivlaptev.myapplication.data.services.IdentityService;
import com.ivlaptev.myapplication.models.TokenResponse;
import com.ivlaptev.myapplication.models.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    IdentityService identityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);

        identityService = IdentityService.getInstance(this);

        try {
            UserCredentials user = EncryptedStore.getInstance(this).getUser();
            identityService.login(
                    user.login,
                    user.password
            ).enqueue(tokenResponseCallback);
        } catch (Exception e) {
            e.printStackTrace();
            toLogin();
        }
    }

    Callback<TokenResponse> tokenResponseCallback = new Callback<TokenResponse>() {
        @Override
        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
            if (response.isSuccessful()) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            } else {
                toLogin();
            }
        }

        @Override
        public void onFailure(Call<TokenResponse> call, Throwable t) {
            toLogin();
        }
    };

    private void toLogin() {
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }
}