package com.ivlaptev.myapplication.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.EncryptedStore;
import com.ivlaptev.myapplication.data.services.IdentityService;
import com.ivlaptev.myapplication.models.TokenResponse;
import com.ivlaptev.myapplication.models.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenFragment extends Fragment {
    IdentityService identityService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        identityService = IdentityService.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            UserCredentials user = EncryptedStore.getInstance(getContext()).getUser();
            identityService.login(
                    user.login,
                    user.password
            ).enqueue(tokenResponseCallback);
        } catch (Exception e) {
            e.printStackTrace();

            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_logout);
        }
    }

    Callback<TokenResponse> tokenResponseCallback = new Callback<TokenResponse>() {
        @Override
        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
            if (response.isSuccessful()) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_silent_login_successful);
            } else {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_logout);
            }
        }

        @Override
        public void onFailure(Call<TokenResponse> call, Throwable t) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_login_successful);
        }
    };
}