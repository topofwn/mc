package com.example.user.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dicol on 10/05/2018.
 */

public class UpdatePassword {
    @SerializedName("user_type")
    protected String user_type;
    @SerializedName("current_password")
    protected String current_password;
    @SerializedName("new_password")
    protected String new_password;
    @SerializedName("confirm_password")
    protected String confirm_password;
    @SerializedName("type")
    protected String type;

    public String getUser_type() {
        return user_type;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public String getType() {
        return type;
    }
}
