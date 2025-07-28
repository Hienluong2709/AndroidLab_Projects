package com.example.lab5_calendar_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4;
    Button button1;
    EditText editText1, editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        button1 = findViewById(R.id.button1);
        editText1 = findViewById(R.id.editText1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String input1 = editText1.getText().toString();
                if (!input1.isEmpty()) {
                    int namDuong = Integer.parseInt(input1);
                    String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
                    String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

                    String namAm = can[namDuong % 10] + " " + chi[namDuong % 12];
                    textView4.setText(namAm);
                } else {
                    textView4.setText("Vui lòng nhập năm!");
                }
            }
        });

    }
}