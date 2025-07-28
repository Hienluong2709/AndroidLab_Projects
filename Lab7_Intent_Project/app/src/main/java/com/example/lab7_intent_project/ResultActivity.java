package com.example.lab7_intent_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    TextView textView1, textView2;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        btnBack = findViewById(R.id.btnBack);
        Intent intent2 = getIntent();
        Bundle bundle2 = intent2.getBundleExtra("mybackage");
        int a = bundle2.getInt("soa");
        int b = bundle2.getInt("sob");
        String result = "";
        if (a==0 && b==0) {
            result = "Vô số nghiệm";
        } else if (a==0 && b!=0) {
            result = "Vô nghệm";
        } else {
            DecimalFormat dcf = new DecimalFormat("0.##");
            result = dcf.format(-b*1.0/a);
        }
        textView2.setText(result);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}