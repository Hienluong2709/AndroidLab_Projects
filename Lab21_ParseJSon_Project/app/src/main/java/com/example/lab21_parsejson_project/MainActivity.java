package com.example.lab21_parsejson_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);

        btnParse.setOnClickListener(v -> parseJson());
    }

    private void parseJson() {
        items.clear();
        try {
            String json = loadJSONFromAssets("computer.json");

            JSONObject root = new JSONObject(json);
            items.add(root.getString("MaDM"));
            items.add(root.getString("TenDM"));

            JSONArray arr = root.getJSONArray("Sanphams");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                String maSP = obj.getString("MaSP");
                String tenSP = obj.getString("TenSP");
                int soLuong = obj.getInt("SoLuong");
                int donGia = obj.getInt("DonGia");

                long thanhTien;
                if (obj.has("ThanhTien")) {
                    thanhTien = obj.getLong("ThanhTien");
                } else {
                    thanhTien = (long) soLuong * donGia;
                }

                String hinh = obj.getString("Hinh");

                items.add(maSP + " - " + tenSP);
                items.add(soLuong + " * " + donGia + " = " + thanhTien);
                items.add(hinh);
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Parse lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String loadJSONFromAssets(String fileName) throws Exception {
        InputStream input = getAssets().open(fileName);
        byte[] buffer = new byte[input.available()];
        input.read(buffer);
        input.close();
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
