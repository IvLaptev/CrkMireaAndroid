package com.ivlaptev.myapplication.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.EncryptedStore;
import com.ivlaptev.myapplication.data.services.IdentityService;
import com.ivlaptev.myapplication.models.TokenResponse;
import com.ivlaptev.myapplication.models.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private IdentityService identityService;
    private String login;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        Button loginBtn = getView().findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            login = ((EditText) getView().findViewById(R.id.login)).getText().toString();
            password = ((EditText) getView().findViewById(R.id.password)).getText().toString();

            login(view);
        });

        identityService = IdentityService.getInstance(getContext());
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
                EncryptedStore.getInstance(getActivity()).setUser(new UserCredentials(
                        login,
                        password,
                        response.body().accessToken,
                        response.body().refreshToken
                ));

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_login_successful);
            } else {
                Toast.makeText(getActivity(), "Ошибка входа", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<TokenResponse> call, Throwable t) {
            Log.e("ERROR", "onFailure: Login failed", t);
            Toast.makeText(getActivity(), "Проверьте подключение к интернету", Toast.LENGTH_LONG).show();
        }
    };
}