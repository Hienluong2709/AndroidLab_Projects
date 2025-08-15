package com.example.lab20_arsyntask_project;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvPercent;
    private ProgressBar progressBar;
    private Button btnStart;

    private Handler handler = new Handler(Looper.getMainLooper());
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPercent = findViewById(R.id.tvPercent);
        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> startProgress());
    }

    private void startProgress() {
        progressStatus = 0;
        progressBar.setProgress(0);
        tvPercent.setText("0%");

        Toast.makeText(MainActivity.this, "onPreExecute()", Toast.LENGTH_SHORT).show();

        // Chạy trong thread riêng để mô phỏng công việc
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(100); // giả lập công việc
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int progress = i;
                handler.post(() -> {
                    progressBar.setProgress(progress);
                    tvPercent.setText(progress + "%");
                });
            }

            handler.post(() ->
                    Toast.makeText(MainActivity.this, "Update xong rồi!", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }
}
