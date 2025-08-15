package com.example.lab18_karaoke_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtTim;
    private ListView lvSearch;
    private ListView lvAll;
    private ListView lvFav;
    private ImageButton btnXoa;
    private TabHost tabHost;

    private DatabaseAssetHelper helper;
    private SQLiteDatabase db;

    private List<Item> listSearch = new ArrayList<>();
    private List<Item> listAll = new ArrayList<>();
    private List<Item> listFav = new ArrayList<>();

    private MyArrayAdapter adapterSearch;
    private MyArrayAdapter adapterAll;
    private MyArrayAdapter adapterFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout TabHost bạn đã gửi

        helper = new DatabaseAssetHelper(this);
        db = helper.openDatabase();

        adapterSearch = new MyArrayAdapter(this, R.layout.listitem, listSearch, () -> db);
        adapterAll = new MyArrayAdapter(this, R.layout.listitem, listAll, () -> db);
        adapterFav = new MyArrayAdapter(this, R.layout.listitem, listFav, () -> db);

        bindViews();
        setupTabs();
        setupEvents();

        // Nạp dữ liệu ban đầu
        loadAll();
        loadFavorites();
    }

    private void bindViews() {
        btnXoa = findViewById(R.id.btnxoa);
        tabHost = findViewById(R.id.tabhost);
        edtTim = findViewById(R.id.edttim);
        lvSearch = findViewById(R.id.lv1);
        lvAll = findViewById(R.id.lv2);
        lvFav = findViewById(R.id.lv3);

        lvSearch.setAdapter(adapterSearch);
        lvAll.setAdapter(adapterAll);
        lvFav.setAdapter(adapterFav);
    }

    private void setupTabs() {
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Tìm");
        tab1.setContent(R.id.tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("DS");
        tab2.setContent(R.id.tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Yêu thích");
        tab3.setContent(R.id.tab3);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setOnTabChangedListener(tabId -> {
            switch (tabId) {
                case "t2":
                    loadAll();
                    break;
                case "t3":
                    loadFavorites();
                    break;
            }
        });
    }

    private void setupEvents() {
        btnXoa.setOnClickListener(v -> edtTim.setText(""));

        edtTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s != null ? s.toString() : "");
            }
        });
    }

    private void performSearch(String keyword) {
        listSearch.clear();
        if (keyword.trim().isEmpty()) {
            adapterSearch.notifyDataSetChanged();
            return;
        }

        String like = "%" + keyword + "%";
        String sql = "SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList " +
                "WHERE TENBH LIKE ? OR MABH LIKE ?";
        Cursor c = db.rawQuery(sql, new String[]{like, like});
        try {
            while (c.moveToNext()) {
                listSearch.add(new Item(
                        c.getString(0),
                        c.getString(1),
                        c.getInt(2)
                ));
            }
        } finally {
            c.close();
        }
        adapterSearch.notifyDataSetChanged();
    }

    private void loadAll() {
        listAll.clear();
        Cursor c = db.rawQuery("SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList", null);
        try {
            while (c.moveToNext()) {
                listAll.add(new Item(
                        c.getString(0),
                        c.getString(1),
                        c.getInt(2)
                ));
            }
        } finally {
            c.close();
        }
        adapterAll.notifyDataSetChanged();
    }

    private void loadFavorites() {
        listFav.clear();
        Cursor c = db.rawQuery(
                "SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList WHERE YEUTHICH=1",
                null
        );
        try {
            while (c.moveToNext()) {
                listFav.add(new Item(
                        c.getString(0),
                        c.getString(1),
                        c.getInt(2)
                ));
            }
        } finally {
            c.close();
        }
        adapterFav.notifyDataSetChanged();
    }
}
