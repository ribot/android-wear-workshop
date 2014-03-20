package uk.co.ribot.androidwear.model;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login") public String username;
    public int id;
    @SerializedName("avatar_url") public String avatarUrl;
}
