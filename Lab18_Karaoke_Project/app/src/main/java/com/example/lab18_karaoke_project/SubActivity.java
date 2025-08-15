package com.example.lab18_karaoke_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    private TextView txtMaso;
    private TextView txtTieuDe;
    private TextView txtLoiBaiHat;
    private TextView txtTacGia;
    private ImageButton btnThich;
    private ImageButton btnKhongThich;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        txtMaso = findViewById(R.id.txtmaso);
        txtTieuDe = findViewById(R.id.txttieude);
        txtLoiBaiHat = findViewById(R.id.txtloibaihat);
        txtTacGia = findViewById(R.id.txttacgia);
        btnThich = findViewById(R.id.btnthich);
        btnKhongThich = findViewById(R.id.btnkhongthich);

        db = new DatabaseAssetHelper(this).openDatabase();

        String mabaihat = getIntent().getStringExtra("maso");
        if (mabaihat == null) return;

        loadDetail(mabaihat);

        btnThich.setOnClickListener(v -> setFavorite(mabaihat, 0));
        btnKhongThich.setOnClickListener(v -> setFavorite(mabaihat, 1));
    }

    private void loadDetail(String maso) {
        Cursor c = db.rawQuery(
                "SELECT * FROM ArirangSongList WHERE MABH = ?",
                new String[]{maso}
        );

        try {
            if (c.moveToFirst()) {
                txtMaso.setText("#" + maso);
                txtTieuDe.setText(c.getString(c.getColumnIndexOrThrow("TENBH")));
                txtLoiBaiHat.setText(c.getString(c.getColumnIndexOrThrow("LoiBH")));
                txtTacGia.setText(c.getString(c.getColumnIndexOrThrow("TacGia")));

                int yeuThich = c.getInt(c.getColumnIndexOrThrow("YEUTHICH"));
                if (yeuThich == 1) {
                    btnThich.setVisibility(View.INVISIBLE);
                    btnKhongThich.setVisibility(View.VISIBLE);
                } else {
                    btnThich.setVisibility(View.VISIBLE);
                    btnKhongThich.setVisibility(View.INVISIBLE);
                }
            }
        } finally {
            c.close();
        }
    }

    private void setFavorite(String maso, int value) {
        ContentValues values = new ContentValues();
        values.put("YEUTHICH", value);
        db.update("ArirangSongList", values, "MABH=?", new String[]{maso});
        loadDetail(maso);
    }
}
