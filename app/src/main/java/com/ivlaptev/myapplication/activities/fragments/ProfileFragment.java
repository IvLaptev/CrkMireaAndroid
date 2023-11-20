package com.ivlaptev.myapplication.activities.fragments;

import android.content.res.Resources;
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
import android.widget.TextView;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.data.services.CkmireaService;
import com.ivlaptev.myapplication.models.UserinfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private CkmireaService ckmireaService;

    private TextView email;
    private TextView username;
    private TextView name;
    private TextView middlename;
    private TextView lastname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ckmireaService = CkmireaService.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = getView().findViewById(R.id.profile_email);
        username = getView().findViewById(R.id.profile_username);
        name = getView().findViewById(R.id.profile_name);
        middlename = getView().findViewById(R.id.profile_middlename);
        lastname = getView().findViewById(R.id.profile_lastname);

        ckmireaService.getUserinfo().enqueue(userinfoCallback);
    }

    Callback<UserinfoResponse> userinfoCallback = new Callback<UserinfoResponse>() {
        @Override
        public void onResponse(Call<UserinfoResponse> call, Response<UserinfoResponse> response) {
            if (response.isSuccessful()) {
                UserinfoResponse userinfo = response.body();

                Resources res = getResources();
                email.setText(userinfo.email);
                username.setText(userinfo.username);
                name.setText(userinfo.firstname);
                middlename.setText(userinfo.middlename);
                lastname.setText(userinfo.lastname);
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
}