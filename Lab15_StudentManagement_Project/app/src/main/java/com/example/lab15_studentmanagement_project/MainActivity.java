package com.example.lab15_studentmanagement_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmalop, edttenlop, edtsiso;
    Button btnInsert, btnUpdate, btnDelete, btnQuery;

    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    private void loadData() {
        mylist.clear();
        Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
                mylist.add(data);
            } while (c.moveToNext());
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtmalop = findViewById(R.id.edtmalop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnQuery = findViewById(R.id.btnQuery);
        lv = findViewById(R.id.lv);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        mydatabase = openOrCreateDatabase("qlsv.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tbllop (malop TEXT PRIMARY KEY, tenlop TEXT, siso INTEGER)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btnInsert.setOnClickListener(v -> {
            String malop = edtmalop.getText().toString();
            String tenlop = edttenlop.getText().toString();
            int siso = Integer.parseInt(edtsiso.getText().toString());

            ContentValues myvalue = new ContentValues();
            myvalue.put("malop", malop);
            myvalue.put("tenlop", tenlop);
            myvalue.put("siso", siso);

            String msg;
            if (mydatabase.insert("tbllop", null, myvalue) != -1) {
                msg = "Insert thành công";
                loadData(); // Tự động load lại list
            } else {
                msg = "Insert thất bại";
            }
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v -> {
            String malop = edtmalop.getText().toString();
            int n = mydatabase.delete("tbllop", "malop=?", new String[]{malop});
            String msg;
            if (n == 0) {
                msg = "Delete thất bại";
            } else {
                msg = "Delete thành công";
                loadData(); // Load lại list
            }
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        btnUpdate.setOnClickListener(v -> {
            int siso = Integer.parseInt(edtsiso.getText().toString());
            String malop = edtmalop.getText().toString();

            ContentValues myvalue = new ContentValues();
            myvalue.put("siso", siso);

            int n = mydatabase.update("tbllop", myvalue, "malop=?", new String[]{malop});
            String msg;
            if (n == 0) {
                msg = "Update thất bại";
            } else {
                msg = "Update thành công";
                loadData(); // Load lại list
            }
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        btnQuery.setOnClickListener(v -> loadData());
    }

}