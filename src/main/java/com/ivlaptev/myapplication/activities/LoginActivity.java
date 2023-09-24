package com.ivlaptev.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.EncryptedStore;
import com.ivlaptev.myapplication.data.services.IdentityService;
import com.ivlaptev.myapplication.models.TokenResponse;
import com.ivlaptev.myapplication.models.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private IdentityService identityService;
    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            login = ((EditText) findViewById(R.id.login)).getText().toString();
            password = ((EditText) findViewById(R.id.password)).getText().toString();

            login(view);
        });

        identityService = IdentityService.getInstance(this);
    }

    private void login(View view) {
        identityService.login(
                login,
                password
        ).enqueue(tokenResponseCallback);
    }

    Callback<TokenResponse> tokenResponseCallback = new Callback<TokenResponse>() {
        @Override
        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
            if (response.isSuccessful()) {
                EncryptedStore.getInstance(LoginActivity.this).setUser(new UserCredentials(
                        login,
                        password,
                        response.body().accessToken,
                        response.body().refreshToken
                ));

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<TokenResponse> call, Throwable t) {
            Log.e("ERROR", "onFailure: Login failed", t);
            Toast.makeText(LoginActivity.this, "Проверьте подключение к интернету", Toast.LENGTH_LONG).show();
        }
    };
}