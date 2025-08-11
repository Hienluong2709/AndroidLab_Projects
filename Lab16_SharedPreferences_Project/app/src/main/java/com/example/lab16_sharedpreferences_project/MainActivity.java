package com.example.lab16_sharedpreferences_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtKq;
    Button btnTong, btnClear;
    TextView txtLichsu;
    String lichsu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKq = findViewById(R.id.edtKq);
        btnTong = findViewById(R.id.btnTong);
        btnClear = findViewById(R.id.btnClear);
        txtLichsu = findViewById(R.id.txtLichsu);
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        lichsu = myprefs.getString("lichsu", "");
        txtLichsu.setText(lichsu);
        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                int kq = a + b;
                edtKq.setText(String.valueOf(kq));
                lichsu += a + " + " + b + " = " + kq + "\n";
                txtLichsu.setText(lichsu);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lichsu = "";
                txtLichsu.setText(lichsu);
                SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
                SharedPreferences.Editor editor = myprefs.edit();
                editor.clear(); // Xóa toàn bộ dữ liệu
                editor.apply();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        SharedPreferences.Editor editor = myprefs.edit();
        editor.putString("lichsu", lichsu);
        editor.commit();
    }
}