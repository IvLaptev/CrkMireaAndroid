package com.ivlaptev.myapplication.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class UserCredentials {
    public String login;

    public String password;

    public String accessToken;

    public String refreshToken;

    public UserCredentials(@NonNull String login, @NotNull String password, @NotNull String accessToken, @NotNull String refreshToken) {
        this.login = login;
        this.password = password;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
