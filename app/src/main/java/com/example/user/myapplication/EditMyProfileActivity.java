package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static com.example.user.myapplication.activity_registration.SECRET_KEY;
import static com.example.user.myapplication.activity_registration.SERVER_URL;

public class EditMyProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =71 ;
    private static final String TAG = "mc" ;
    public Uri filePath =null;
    private ImageButton ImgAvatar;
    public   Account profile;
    public EditText edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        setContentView(R.layout.activity_edit_my_profile);
        Button btnDone = (Button) findViewById(R.id.btnDone);
        Button btnChange = (Button) findViewById(R.id.btnChange);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        EditText edtName = (EditText) findViewById(R.id.edtNameP);
        EditText edtPhone = (EditText) findViewById(R.id.edtPhoneP);
        EditText edtMail = (EditText) findViewById(R.id.edtEmailP);
        edtPass = (EditText) findViewById(R.id.edtPasswordP);
        edtMail.setEnabled(false);
        edtPass.setEnabled(false);
       profile = (Account) b.getSerializable("inform");
        String s = (String) b.getCharSequence("inform_pass");
        edtName.setText(profile.full_name);
        edtPhone.setText(profile.phone_number);
        edtMail.setText(profile.email);
        edtPass.setText(s);
         ImgAvatar = (ImageButton)findViewById(R.id.ImgAvatar);
         if (!profile.avatar.equals("")){
//             try {
//                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(profile.avatar));
//                 ImgAvatar.setImageBitmap(bitmap);
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
             Glide.with(this).load(Uri.parse(profile.avatar)).apply(RequestOptions.circleCropTransform()).into(ImgAvatar);
         }
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Account edit = profile;
                edit.full_name = edtName.getText().toString();
                edit.phone_number = edtPhone.getText().toString();
                if (filePath != null){
                    String temp = filePath.toString();

                    try {
                        edit.avatar = android.util.Base64.encodeToString(temp.getBytes("UTF-8"), android.util.Base64.DEFAULT);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                EditProfileAsyncTask editprofileAsynctask = new EditProfileAsyncTask(EditMyProfileActivity.this);
                editprofileAsynctask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Rx2AndroidNetworking.put(SERVER_URL + "/api/mcf/v1/drinker/"+String.valueOf(edit.id))
                                .addApplicationJsonBody(edit)
                               .addHeaders(new APIHeader(edit.token))
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Toast.makeText(getApplicationContext(),response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                            if (response.getInt("status") == 400){
                                                Log.d(TAG,response.getString("message").toString());
                                            }else if (response.getInt("status") == 200){
                                            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();

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

                intent.putExtra("result",edit);
                intent.putExtra("result_pass",edtPass.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMyProfileActivity.this, ChangePasswordActivity.class);
                startActivityForResult(intent,2);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditMyProfileActivity.this, activity_choose.class));
            }
        });
        ImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
    }

    private void ChooseImage() {
        Intent inte = new Intent();
        inte.setType("image/*");
        inte.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(inte,"Select picture"),PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&data.getData()!=null){
            filePath = data.getData();
//            try{
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
//                ImgAvatar.setImageBitmap(bitmap);
//            }  catch (IOException e) {
//                e.printStackTrace();
//            }
            Glide.with(this).load(filePath).apply(RequestOptions.circleCropTransform()).into(ImgAvatar);
        }
        if ((requestCode == 2)&&(resultCode == Activity.RESULT_OK)){
            String curPass = data.getStringExtra("changed_pass1");
            String newPass = data.getStringExtra("changed_pass2");
            String confPass = data.getStringExtra("changed_pass3");
            edtPass.setText(newPass);
            UpdatePassword upPass = new UpdatePassword();
            upPass.user_type = profile.user_type;
            upPass.current_password = curPass;
            upPass.new_password = newPass;
            upPass.confirm_password = confPass;
            upPass.type = "CHANGE";
            EditProfileAsyncTask editprofileAsynctask = new EditProfileAsyncTask(EditMyProfileActivity.this);
            editprofileAsynctask.execute(new Runnable() {
                @Override
                public void run() {
                    Rx2AndroidNetworking.put(SERVER_URL + "/api/mcf/v1/drinker/"+String.valueOf(profile.id))
                                        .addApplicationJsonBody(upPass)
                                        .addHeaders(new APIHeader(profile.token))
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(getApplicationContext(),"Change password success",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                            }
                                        });
                }
            });


        }
    }
}
