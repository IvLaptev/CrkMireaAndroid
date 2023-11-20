package com.ivlaptev.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserinfoResponse {
    public UUID uid;
    public String middlename;
    @SerializedName("name")
    public String firstname;
    public String lastname;
    public String username;
    public String email;
}
