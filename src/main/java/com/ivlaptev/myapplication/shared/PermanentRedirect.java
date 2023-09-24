package com.ivlaptev.myapplication.shared;

import android.content.Context;
import android.content.Intent;

import com.ivlaptev.myapplication.activities.LoginActivity;
import com.ivlaptev.myapplication.activities.MainActivity;

public class PermanentRedirect {
    public static void toLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
