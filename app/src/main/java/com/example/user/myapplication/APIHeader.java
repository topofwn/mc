package com.example.user.myapplication;

import com.google.gson.annotations.SerializedName;

public class APIHeader {
    @SerializedName("X-CSRF-Token")
    private String apiKey;
    @SerializedName("platform")
    private String platform ;
    public  APIHeader(String key){
        this.apiKey = key;
        this.platform = "Android";

    }

    public String getApiKey(){
        return apiKey;
    }
}
