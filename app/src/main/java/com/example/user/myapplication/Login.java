package com.example.user.myapplication;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends JSONObject {
    @SerializedName("username")
    protected String username;
    @SerializedName("password")
    protected String password;
    @SerializedName("tune_token")
    protected String tune_token;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTune_token() {
        return tune_token;
    }

    public JSONObject getJSONObject(String name, String pw, String token) throws JSONException {
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("username", name);
            obj.accumulate("password", pw);
            obj.accumulate("tune_token", token);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
      return null;
    }
}
