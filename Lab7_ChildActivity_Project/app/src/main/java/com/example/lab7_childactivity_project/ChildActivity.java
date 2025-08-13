package com.example.lab7_childactivity_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        Button btnBackMain = findViewById(R.id.btnBackMain);

        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Để không quay lại ChildActivity khi nhấn back
            }
        });
    }
}
