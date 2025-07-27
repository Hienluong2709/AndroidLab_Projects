package com.example.lab3_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2, editText3;
    Button buttonTong, buttonHieu, buttonNhan, buttonChia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        buttonTong = findViewById(R.id.buttonTong);
        buttonHieu = findViewById(R.id.buttonHieu);
        buttonNhan = findViewById(R.id.buttonNhan);
        buttonChia = findViewById(R.id.buttonChia);

        buttonTong.setOnClickListener(v -> {
            double a = Double.parseDouble("0" + editText1.getText());
            double b = Double.parseDouble("0" + editText2.getText());
            editText3.setText("a + b = " + (a+b));
        });
        buttonHieu.setOnClickListener(v -> {
            double a = Double.parseDouble("0" + editText1.getText());
            double b = Double.parseDouble("0" + editText2.getText());
            editText3.setText("a - b = " + (a-b));
        });
        buttonNhan.setOnClickListener(v -> {
            double a = Double.parseDouble("0" + editText1.getText());
            double b = Double.parseDouble("0" + editText2.getText());
            editText3.setText("a * b = " + (a*b));
        });
        buttonChia.setOnClickListener(v -> {
            double a = Double.parseDouble("0" + editText1.getText());
            double b = Double.parseDouble("0" + editText2.getText());
            if (b != 0) {
                editText3.setText("a / b = " + (a/b));
            } else {
                editText3.setText("B phai khac 0");
            }
        });
    }
}