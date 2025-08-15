package com.example.lab23_vnexpress_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Article> {

    private Activity act;
    private int layoutId;
    private List<Article> data;

    public MyArrayAdapter(@NonNull Activity act, int layoutId, @NonNull List<Article> data) {
        super(act, layoutId, data);
        this.act = act;
        this.layoutId = layoutId;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutId, parent, false);
        }

        ImageView img = view.findViewById(R.id.imgThumb);
        TextView title = view.findViewById(R.id.txtTitle);
        TextView sum = view.findViewById(R.id.txtSummary);

        final Article item = data.get(position);
        title.setText(item.getTitle());
        sum.setText(item.getSummary());
        img.setImageResource(R.mipmap.ic_launcher);

        view.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
            act.startActivity(intent);
        });

        return view;
    }
}
