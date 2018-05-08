package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.beardedhen.androidbootstrap.BootstrapProgressBarGroup;


public class ActiviyWelcome extends Activity {
   BootstrapProgressBar mProgressBar;

   int i=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        BootstrapProgressBarGroup groupRound =  findViewById(R.id.character1);
        mProgressBar = (BootstrapProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMaxProgress(10);
        mProgressBar.setProgress(0);
        groupRound.setRounded(true);
        mProgressBar.setRounded(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
             for ( i = 0; i<10;i++){
                 if (i==9){startActivity(new Intent(ActiviyWelcome.this, activity_choose.class));}
                 try {
                    Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         mProgressBar.setProgress((i*100)/10);
                     }
                 });
             }

            }
        }).start();
    }
}






