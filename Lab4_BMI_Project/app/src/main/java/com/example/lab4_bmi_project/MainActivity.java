package com.example.lab4_bmi_project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;


import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnTinh;
    EditText edtTen, edtChieucao, edtCannang, edtBMI, edtChuandoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtTen = findViewById(R.id.edtTen);
        edtChieucao = findViewById(R.id.edtChieucao);
        edtCannang = findViewById(R.id.edtCannang);
        edtBMI = findViewById(R.id.edtBMI);
        edtChuandoan = findViewById(R.id.edtChuandoan);
        btnTinh = findViewById(R.id.btnTinh);
        btnTinh.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View view){
                double H = Double.parseDouble(edtChieucao.getText() + "");
                double W = Double.parseDouble(edtCannang.getText() + "");
                double BMI = W / (H * H);
                String chuandoan = "";
                if (BMI < 18.5) {
                    chuandoan = "Ban gay";
                } else if (BMI < 24.9) {
                    chuandoan = "Ban binh thuong";
                } else if (BMI < 29.9) {
                    chuandoan = "Ban beo phi do 1";
                } else if (BMI < 34.9) {
                    chuandoan = "Ban beo phi do 2";
                } else {
                    chuandoan = "Ban beo phi";
                }
                DecimalFormat dcf = new DecimalFormat("#.0");
                edtBMI.setText(dcf.format(BMI));
                edtChuandoan.setText(chuandoan);
            }
        });
    }
}