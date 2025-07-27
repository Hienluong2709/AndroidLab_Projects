package com.example.lab4_temperatureconverter_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText txtFar, txtCel;
    Button btnFar, btnCel,btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txtCel = findViewById(R.id.txtCel);
        txtFar = findViewById(R.id.txtFar);
        btnCel = findViewById(R.id.btnCel);
        btnFar = findViewById(R.id.btnFar);
        btnCel.setOnClickListener(v -> {
            double far = Double.parseDouble("0" + txtFar.getText());
            double cel = (far - 32) * 5 / 9;
            txtCel.setText(String.format("%.2f", cel));
        });
        btnFar.setOnClickListener(v -> {
            double cel = Double.parseDouble("0" + txtCel.getText());
            double far = cel * 9 / 5 + 32;
            txtFar.setText(String.format("%.2f", far));
        });
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> {
            txtCel.setText("");
            txtFar.setText("");
        }
        );
    }
}