package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import static java.lang.Integer.*;

public class activity_choose extends AppCompatActivity {
 public static final String SERVER_URL ="http://mycafe.miloglobal.xyz/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button btnRegis = (Button) findViewById(R.id.btnRegistrationchoose);
        Button btnLogin = (Button) findViewById(R.id.btnLoginchoose);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_choose.this, activity_registration.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_choose.this, activity_login.class));
            }
        });
    }
}
