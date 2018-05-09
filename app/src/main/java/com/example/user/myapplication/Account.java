package com.example.user.myapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * Created by dicol on 09/05/2018.
 */

public class Account implements  Serializable {
    @SerializedName("id")
    protected int id;
    @SerializedName("user_type")
    protected String user_type;
    @SerializedName("full_name")
    protected String full_name;
    @SerializedName("phone_number")
    protected String phone_number;
    @SerializedName("email")
    protected String email;
    @SerializedName("avatar")
    protected String avatar;
    @SerializedName("token")
    protected String token;
    @SerializedName("total_order")
    protected int total_order;

    public Account(){}
    public Account(String Fullname,String Mail,String Mobilenumber,String token,String avatar, String user_type,int id, int total_order){
        this.full_name = Fullname;
        this.phone_number = Mobilenumber;
        this.email = Mail;
        this.avatar = avatar;
        this.token = token;
        this.total_order = total_order;
        this.id = id;
        this.user_type = user_type;

    }
    public String getFull_name(){
        return full_name;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public String getEmail(){
        return email;
    }

    public int getId() {
        return id;
    }

    public int getTotal_order() {
        return total_order;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getToken() {
        return token;
    }

    public String getUser_type() {
        return user_type;
    }

}
