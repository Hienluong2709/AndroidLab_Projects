package com.example.lab7_intent_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2;
    TextView textView1, textView2, textView3;
    Button btnKq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        btnKq = findViewById(R.id.btnKq);
        btnKq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, ResultActivity.class);
                int a = Integer.parseInt(editText1.getText().toString());
                int b = Integer.parseInt(editText2.getText().toString());
                Bundle bundle1 = new Bundle();
                bundle1.putInt("soa", a);
                bundle1.putInt("sob", b);
                intent1.putExtra("mybackage", bundle1);
                startActivity(intent1);
            }
        });

    }
}