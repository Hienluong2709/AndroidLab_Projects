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
import androidx.core.app.ActivityCompat;


public class CallActivity extends AppCompatActivity {
    EditText edtPhoneNumber;
    ImageButton btnCall;
    Button btnBack1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnCall = (ImageButton) findViewById(R.id.btnCall);
        btnBack1 = findViewById(R.id.btnBack1);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + edtPhoneNumber.getText().toString()));
                if (ActivityCompat.checkSelfPermission(CallActivity.this, android.Manifest.permission.CALL_PHONE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                } else {
                    startActivity(callIntent);
                }
            }
        });

        btnBack1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}