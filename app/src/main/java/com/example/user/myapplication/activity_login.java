package com.example.user.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.schedulers.Schedulers;

import static com.example.user.myapplication.activity_registration.SERVER_URL;
import static com.example.user.myapplication.activity_registration.SECRET_KEY;

public class activity_login extends AppCompatActivity {
    private EditText edtMail, edtPassword;
    private static final String TAG="My_Cafe";
    private Button btnLogin;
    private ImageButton btnBackL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtMail = (EditText) findViewById(R.id.edtEmailL);
        edtPassword = (EditText) findViewById(R.id.edtPasswordL);
        btnLogin = (Button) findViewById(R.id.btnLoginL);
        btnBackL = (ImageButton) findViewById(R.id.btnImgbackL);
        btnBackL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(activity_login.this, activity_choose.class));
                }
            });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Login login = new Login();
                    login.username = edtMail.getText().toString();
                    login.password = edtPassword.getText().toString();
                    login.tune_token = null;
                    LoginAsyncTask loginAsynctask = new LoginAsyncTask(activity_login.this);
                    loginAsynctask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Rx2AndroidNetworking.post(SERVER_URL + "/api/mcf/v1/login")
                                        .addJSONObjectBody(login.getJSONObject(login.username,login.password,login.tune_token))
                                        .addHeaders(new APIHeader(SecurityUtils.convertSha256(SECRET_KEY + login.username + login.password)))
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    Toast.makeText(getApplicationContext(),response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                                    if (response.getInt("status") == 400){
                                                        Log.d(TAG,response.getString("message").toString());
                                                    }else {
                                                        startActivity(new Intent(activity_login.this, activity_choose.class));
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            @Override
                                            public void onError(ANError error) {
                                                if (error.getErrorCode() != 0) {
                                                    Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                                                    Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                                                    Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                                                } else {
                                                    Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                                                }
                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
            }
        });
    }

}
