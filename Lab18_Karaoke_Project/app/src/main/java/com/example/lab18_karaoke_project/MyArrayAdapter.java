package com.example.lab18_karaoke_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {

    public interface DbProvider {
        SQLiteDatabase getDb();
    }

    private Context ctx;
    private int layoutId;
    private List<Item> arr;
    private DbProvider dbProvider;

    public MyArrayAdapter(Context ctx, int layoutId, List<Item> arr, DbProvider dbProvider) {
        super(ctx, layoutId, arr);
        this.ctx = ctx;
        this.layoutId = layoutId;
        this.arr = arr;
        this.dbProvider = dbProvider;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View row = (convertView != null) ? convertView : inflater.inflate(layoutId, parent, false);

        Item item = arr.get(position);

        TextView tvTieuDe = row.findViewById(R.id.txttieude);
        TextView tvMaSo = row.findViewById(R.id.txtmaso);
        ImageView btnLike = row.findViewById(R.id.btnlike);
        ImageView btnUnlike = row.findViewById(R.id.btnunlike);

        tvTieuDe.setText(item.getTieude());
        tvMaSo.setText(item.getMaso());

        // Hàm hiển thị icon theo trạng thái
        Runnable renderLikeState = () -> {
            if (item.getThich() == 1) {
                btnLike.setVisibility(View.VISIBLE);
                btnUnlike.setVisibility(View.INVISIBLE);
            } else {
                btnLike.setVisibility(View.INVISIBLE);
                btnUnlike.setVisibility(View.VISIBLE);
            }
        };
        renderLikeState.run();

        // Hàm cập nhật trạng thái yêu thích
        View.OnClickListener toggleFavorite = v -> {
            int newVal = (v.getId() == R.id.btnlike) ? 0 : 1;
            item.setThich(newVal);
            ContentValues values = new ContentValues();
            values.put("YEUTHICH", newVal);
            dbProvider.getDb().update(
                    "ArirangSongList",
                    values,
                    "MABH=?",
                    new String[]{item.getMaso()}
            );
            renderLikeState.run();
        };

        btnLike.setOnClickListener(toggleFavorite);
        btnUnlike.setOnClickListener(toggleFavorite);

        // Mở SubActivity khi click vào dòng
        row.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, SubActivity.class);
            intent.putExtra("maso", item.getMaso());
            ctx.startActivity(intent);
        });

        return row;
    }
}
