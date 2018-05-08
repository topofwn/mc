package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




public class PromotionActivity extends Activity {
    public final String CODE = "CD51H";
    public final String EXPIRED = "10/02/2020";
    public final String SUMMARY = "This code is just an example";
    private LinearLayout SuccessLayout, ErrorLayout;
    private ImageButton btnBack;
    private Button btnApply;
    private EditText edtPromotion;
    private TextView code, expired, summary, error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_1);
        SuccessLayout = (LinearLayout) findViewById(R.id.ApplySuccess);
        ErrorLayout = (LinearLayout) findViewById(R.id.ErrorLayout);
        code = (TextView) findViewById(R.id.SelectPromotionB);
        expired = (TextView) findViewById(R.id.ExpiredDateB);
        summary = (TextView) findViewById(R.id.txtSummary);
        error = (TextView) findViewById(R.id.txtError);
        error.setText("The promotion you entered have been incorrect, please re-enter promotion");
        edtPromotion = (EditText) findViewById(R.id.EdtPromotion);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        try{
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(PromotionActivity.this, ActivityTemp.class);
            //    startActivity(i);
            }
        });
        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_keyboard();
                String Inputtxt = edtPromotion.getText().toString();
                edtPromotion.getText().clear();
                if (Inputtxt.equals("")==true)
                {
                    Toast.makeText(PromotionActivity.this,"Please enter the promotion",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Inputtxt.equals(CODE)==true)//Kiem tra coupon user nhap voi danh sach coupon tu server
                      {                             //Neu trung khop voi bat ki coupon nao thi se hien thi thong tin
                        showSuccess(Inputtxt);      //cua coupon do nhu expired date va summary
                      }
                    else {
                        showError();                 //Neu khong trung khop thi thong bao sai va de user nhap lai
                      }
                    }
            }
        });
           } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void showSuccess(String txt)  {
        code.setText(txt);
        expired.setText(EXPIRED);
        summary.setText(SUMMARY);
        edtPromotion.getEditableText();
        expired.getEditableText();
        ErrorLayout.setVisibility(View.INVISIBLE);
        SuccessLayout.setVisibility(View.VISIBLE);
//      Intent intent = new Intent(PromotionActivity.this,ActivityOrder.class);
//       startActivity(intent);
    }

      void showError() {
        SuccessLayout.setVisibility(View.GONE);
        ErrorLayout.setVisibility(View.VISIBLE);

    }

    private void hide_keyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtPromotion.getWindowToken(), 0);

    }



}
