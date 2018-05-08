package com.example.user.myapplication;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class User {
    @SerializedName("full_name")
    protected String full_name;
    @SerializedName("phone_number")
    protected String phone_number;
    @SerializedName("email")
    protected String email;
    @SerializedName("password")
    protected String password;
    @SerializedName("confirm_password")
    protected String confirm_password;

    public User(){}
   public User(String Fullname,String Mail,String Mobilenumber,String password,String confrim_password){
        this.full_name = Fullname;
        this.phone_number = Mobilenumber;
        this.email = Mail;
        this.password = password;
        this.confirm_password = confrim_password;

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
public String getPassword(){
       return password;
}
public String getConfirm_password(){
        return confirm_password;
}
}
