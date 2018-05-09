package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnImgBackCP);
        Button btnOK = (Button) findViewById(R.id.btnOk);
        EditText edtCurPass = (EditText) findViewById(R.id.edtCurrentPasswordCP);
        EditText edtNewPass = (EditText) findViewById(R.id.edtNewPasswordCP);
        EditText edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPasswordCP);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this, MyProfileActivity.class));
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2,s3 = null;
                s1 = edtCurPass.getText().toString();
                s2 = edtNewPass.getText().toString();
                s3 = edtConfirmPass.getText().toString();
                if ((s2 == s3) && (s1 != s2)&& (s1 != s3))
                {
                    Intent intent = new Intent(ChangePasswordActivity.this,EditMyProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("change Pass",s3);
                    startActivity(intent);
                }
                    else{
                    Toast.makeText(getApplicationContext()," Error when change password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
