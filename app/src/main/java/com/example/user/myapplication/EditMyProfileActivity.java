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
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.user.myapplication.activity_registration.SECRET_KEY;
import static com.example.user.myapplication.activity_registration.SERVER_URL;

public class EditMyProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =71 ;
    private static final String TAG = "mc" ;
    private Uri filePath =null;
    private ImageButton ImgAvatar;
    public EditText edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle b = new Bundle();

        setContentView(R.layout.activity_edit_my_profile);
        Button btnDone = (Button) findViewById(R.id.btnDone);
        Button btnChange = (Button) findViewById(R.id.btnChange);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        EditText edtName = (EditText) findViewById(R.id.edtNameP);
        EditText edtPhone = (EditText) findViewById(R.id.edtPhoneP);
        EditText edtMail = (EditText) findViewById(R.id.edtEmailP);
        edtPass = (EditText) findViewById(R.id.edtPasswordP);
        Account profile = (Account) i.getSerializableExtra("inform");
        String s = (String) b.getCharSequence("inform_pass");
        edtName.setText(profile.full_name);
        edtPhone.setText(profile.phone_number);
        edtMail.setText(profile.email);
        edtPass.setText(s);
         ImgAvatar = (ImageButton)findViewById(R.id.ImgAvatar);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account edit = profile;
                edit.full_name = edtName.getText().toString();
                edit.phone_number = edtPhone.getText().toString();
                EditProfileAsyncTask editprofileAsynctask = new EditProfileAsyncTask(EditMyProfileActivity.this);
                editprofileAsynctask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Rx2AndroidNetworking.put(SERVER_URL + "/api/mcf/v1/drinker/"+String.valueOf(profile.id))
                                .addApplicationJsonBody(edit)
                               .addHeaders(new APIHeader(SecurityUtils.convertSha256(SECRET_KEY + profile.full_name +s )))
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Toast.makeText(getApplicationContext(),response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                            if (response.getInt("status") == 400){
                                                Log.d(TAG,response.getString("message").toString());
                                            }else if (response.getInt("status") == 200){
                                                JSONObject json = new JSONObject();
                                                json = response.getJSONObject("data");
                                                Account result =profile;
                                                result.full_name = json.optString("full_name");
                                                result.phone_number = json.optString("phone_number");
                                                result.avatar = json.optString("avatar");
                                                Intent returnIntent = new Intent();
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("result",result);
                                                bundle.putCharSequence("result_pass",edtPass.getText().toString());
                                                i.putExtras(bundle);
                                                setResult(Activity.RESULT_OK,returnIntent);
                                                finish();
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
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditMyProfileActivity.this, ChangePasswordActivity.class);
                startActivityForResult(i,2);
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
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select picture"),PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&data.getData()!=null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                ImgAvatar.setImageBitmap(bitmap);
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ((requestCode == 2)&&(resultCode == Activity.RESULT_OK)){
            String newPass = data.getStringExtra("changed_pass");
            edtPass.setText(newPass);
        }
    }
}
