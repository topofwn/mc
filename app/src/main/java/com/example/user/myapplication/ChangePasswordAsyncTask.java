package com.example.user.myapplication;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by dicol on 11/05/2018.
 */

public class ChangePasswordAsyncTask extends AsyncTask<Void, Integer, Void> {
    private final Activity contextPage;

    public ChangePasswordAsyncTask(Activity contextPage) {this.contextPage = contextPage;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
