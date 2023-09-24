package com.ivlaptev.myapplication.data.services;

import android.content.Context;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.ivlaptev.myapplication.models.UserCredentials;

public class EncryptedStore {
    private static EncryptedSharedPreferences sharedPreferences = null;
    private static EncryptedStore instance = null;

    private EncryptedStore(Context context) {
        try {
            String masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    "identity",
                    masterKey,
                    context.getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EncryptedStore getInstance(Context context) {
        if (instance == null) {
            synchronized(EncryptedStore.class) {
                instance = new EncryptedStore(context);
            }
        }
        return instance;
    }

    public UserCredentials getUser() {
        return new UserCredentials(
                sharedPreferences.getString("login", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("accessToken", null),
                sharedPreferences.getString("refreshToken", null));
    }

    public void setUser(UserCredentials user) {
        sharedPreferences.edit().putString("login", user.login).apply();
        sharedPreferences.edit().putString("password", user.password).apply();
        sharedPreferences.edit().putString("accessToken", user.accessToken).apply();
        sharedPreferences.edit().putString("refreshToken", user.refreshToken).apply();
    }
}
