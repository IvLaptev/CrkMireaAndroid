package com.ivlaptev.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("refresh_token")
    public String refreshToken;

    @SerializedName("expires_in")
    public Integer expiresIn;

    @SerializedName("refresh_expires_in")
    public Integer refreshExpiresIn;

    @Override
    public String toString() {
        return(accessToken.substring(0, 10) + "...");
    }
}
