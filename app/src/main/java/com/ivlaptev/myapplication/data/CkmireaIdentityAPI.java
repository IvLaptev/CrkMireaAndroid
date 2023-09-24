package com.ivlaptev.myapplication.data;

import com.ivlaptev.myapplication.models.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CkmireaIdentityAPI {
    String BASE_URL = "https://identity.ckmirea.rtuitlab.dev/";

    @FormUrlEncoded
    @POST("realms/master/protocol/openid-connect/token")
    Call<TokenResponse> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret);

    @FormUrlEncoded
    @POST("realms/master/protocol/openid-connect/token")
    Call<TokenResponse> refreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );
}
