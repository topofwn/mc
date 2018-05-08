package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class activity_registration extends Activity {
    private static final String TAG = "My Cafe";
    boolean flagName, flagPhone, flagEmail, flagPass, flagConfirm = false;
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private EditText edtPhone, edtEmail, edtName, edtPass, edtConfirmPass;
    private Button btnRegister;
    private TextView txterror;
    private ImageButton btnBack;
    private LinearLayout Errorfield, Regis;
    public boolean sFlag = false;
    RegisterAsyncTask registerAsyncTask;
    public static final String SERVER_URL = "http://mycafe-dev.miloglobal.xyz";
    public static final String SECRET_KEY = "TDUPcG4xrsApRKH@4zVKTBTBEcq9WW";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_registration);
        edtName = (EditText) findViewById(R.id.edtFullnameR);
        edtPhone = (EditText) findViewById(R.id.edtPhonenumberR);
        edtEmail = (EditText) findViewById(R.id.edtEmailR);
        edtPass = (EditText) findViewById(R.id.edtPasswordR);
        txterror = (TextView) findViewById(R.id.txtError);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmpasswordR);
        btnRegister = (Button) findViewById(R.id.btnRegisterR);
        Errorfield = (LinearLayout) findViewById(R.id.ErrorField);
        btnBack = (ImageButton) findViewById(R.id.btnImgBackR);
        Regis = (LinearLayout) findViewById(R.id.RegistrationLayout);
            try {
                Regis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hide_keyboard();
                    }
                });
                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                             doLogin();
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(activity_registration.this, activity_choose.class));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    private void doLogin(){
        Errorfield.setVisibility(View.GONE);
        if (edtName.getText().toString().equals("") == true) {
            flagName = false;
        } else {
            flagName = true;
        }
        if (edtPhone.getText().toString().equals("") == true) {
            flagPhone = false;
        } else {
            flagPhone = true;
        }
        if (edtEmail.getText().toString().equals("") == true) {
            flagEmail = false;
        } else {
            flagEmail = true;
        }
        if (edtPass.getText().toString().equals("") == true) {
            flagPass = false;
        } else {
            flagPass = true;
        }
        if (edtConfirmPass.getText().toString().equals("") == true) {
            flagConfirm = false;
        } else {
            flagConfirm = true;
        }

        if ((flagName == true) && (flagPhone == true) && (flagEmail == true) && (flagPass == true)) {
            flagEmail = false;
            flagPhone = false;
            flagName = false;
            flagPass = false;
            flagConfirm = false;
            User user = new User(edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString(), edtPass.getText().toString(), edtConfirmPass.getText().toString());
            registerAsyncTask = new RegisterAsyncTask(activity_registration.this);
            registerAsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AndroidNetworking.post(SERVER_URL + "/api/mcf/v1/drinker")
                            .addApplicationJsonBody(user)
                            .setPriority(Priority.MEDIUM)
                            .addHeaders(new APIHeader(SecurityUtils.convertSha256(SECRET_KEY + user.full_name + user.phone_number + user.email + user.password + user.confirm_password)))
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("response", response.toString());
                                    try {
                                        if (response.getInt("status") == 200) {
                                            startActivity(new Intent(activity_registration.this, activity_login.class));
                                            Toast.makeText(getApplicationContext(), response.getString("message").toString(), Toast.LENGTH_LONG).show();
                                        } else if (response.getInt("status") == 400) {
                                            Errorfield.setVisibility(View.VISIBLE);
                                            txterror.setText("Error code " + response.get("status").toString() + ": " + response.getString("message").toString());
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
                }
            });
        }
    }
    private void hide_keyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}



