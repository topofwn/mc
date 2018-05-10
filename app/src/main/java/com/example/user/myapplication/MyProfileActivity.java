package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MyProfileActivity extends AppCompatActivity {
   public TextView account_name,account_phone,account_password;
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
         account_name = (TextView) findViewById(R.id.txtAccountNamePE);
        TextView account_type = (TextView) findViewById(R.id.txtTypePE);
         account_phone = (TextView) findViewById(R.id.txtPhonePE);
        TextView account_mail = (TextView) findViewById(R.id.txtEmailPE);
         account_password = (TextView) findViewById(R.id.txtPasswordPE);
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
                startActivityForResult(i,1);
            }
        });
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MyProfileActivity.this, activity_choose.class));
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                Account newProfile = new Account();
                newProfile = (Account) data.getSerializableExtra("result");
                account_name.setText(newProfile.full_name);
                account_phone.setText(newProfile.phone_number);
                  account_password.setText(data.getCharSequenceExtra("result_pass"));
            }
        }
        if (resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),"nothing change",Toast.LENGTH_SHORT).show();
        }
    }
}
