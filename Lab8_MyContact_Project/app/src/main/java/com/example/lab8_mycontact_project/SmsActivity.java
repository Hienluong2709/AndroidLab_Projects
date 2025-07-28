package com.example.lab8_mycontact_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SmsActivity extends AppCompatActivity {
    EditText edtSmsNumber;
    ImageButton btnSms;
    Button btnBack2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);
        edtSmsNumber = findViewById(R.id.edtSmsNumber);
        btnSms = (ImageButton) findViewById(R.id.btnSms);
        btnBack2 = findViewById(R.id.btnBack2);
        btnSms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent SmsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + edtSmsNumber.getText().toString()));
                startActivity(SmsIntent);
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}