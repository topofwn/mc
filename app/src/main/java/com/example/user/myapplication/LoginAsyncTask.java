package com.example.user.myapplication;

import android.app.Activity;
import android.os.AsyncTask;

class LoginAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextPage;

    public LoginAsyncTask(Activity contextPage) {this.contextPage = contextPage;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
