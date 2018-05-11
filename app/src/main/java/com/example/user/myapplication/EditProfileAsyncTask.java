package com.example.user.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by dicol on 09/05/2018.
 */

class EditProfileAsyncTask extends AsyncTask<Void, Integer, Void> {
      public ProgressDialog dialog;
    private final Activity contextPage;

    public EditProfileAsyncTask(Activity contextPage) {this.contextPage = contextPage;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }


}
