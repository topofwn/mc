package com.example.user.myapplication;

import android.app.Activity;
import android.os.AsyncTask;

public class RegisterAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextPage;

    public RegisterAsyncTask(Activity contextPage){
        this.contextPage = contextPage;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }


}
