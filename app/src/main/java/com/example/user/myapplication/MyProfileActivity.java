package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Button btnEdit = (Button) findViewById(R.id.btnEditPE);
        ImageView imgAvatar = (ImageView) findViewById(R.id.ImgAvatarPE);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnImgBackPE);
        Intent intent = getIntent();
        Account acc = (Account) intent.getSerializableExtra("account");
        String pass = (String) intent.getCharSequenceExtra("pass");
        TextView account_name = (TextView) findViewById(R.id.txtAccountNamePE);
        TextView account_type = (TextView) findViewById(R.id.txtTypePE);
        TextView account_phone = (TextView) findViewById(R.id.txtPhonePE);
        TextView account_mail = (TextView) findViewById(R.id.txtEmailPE);
        TextView account_password = (TextView) findViewById(R.id.txtPasswordPE);
        account_name.setText(acc.full_name);
        account_mail.setText(acc.email);
        account_type.setText(acc.user_type);
        account_phone.setText(acc.phone_number);
        account_password.setText(pass);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyProfileActivity.this, EditMyProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("inform",acc);
                bundle.putCharSequence("inform_pass",pass);
                startActivity(i);
            }
        });
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MyProfileActivity.this, activity_choose.class));
           }
       });
    }
}
