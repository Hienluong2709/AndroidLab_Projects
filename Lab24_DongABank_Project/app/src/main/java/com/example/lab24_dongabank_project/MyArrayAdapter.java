package com.example.lab24_dongabank_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<TyGia> {

    private int resource;

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<TyGia> data) {
        super(context, resource, data);
        this.resource = resource;
    }

    private static class ViewHolder {
        ImageView img;
        TextView txtType;
        TextView txtMuaTM;
        TextView txtMuaCK;
        TextView txtBanTM;
        TextView txtBanCK;
        TextView txtBANK;

        ViewHolder(View v) {
            img = v.findViewById(R.id.imgminh);
            txtType = v.findViewById(R.id.txtType);
            txtMuaTM = v.findViewById(R.id.txtMuaTM);
            txtMuaCK = v.findViewById(R.id.txtMuaCK);
            txtBanTM = v.findViewById(R.id.txtBanTM);
            txtBanCK = v.findViewById(R.id.txtBanCK);
            txtBANK = v.findViewById(R.id.txtBANK);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView;
        ViewHolder holder;

        if (convertView == null) {
            rowView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        TyGia item = getItem(position);
        if (item != null) {
            holder.txtType.setText(item.getType());
            holder.txtMuaTM.setText("MuaTM: " + item.getMuatienmat());
            holder.txtMuaCK.setText("MuaCK: " + item.getMuack());
            holder.txtBanTM.setText("BanTM: " + item.getBantienmat());
            holder.txtBanCK.setText("BanCK: " + item.getBanck());
            holder.txtBANK.setText(item.getBank());
            holder.img.setImageBitmap(item.getBitmap());
        }

        return rowView;
    }

    public void replaceAll(List<TyGia> newItems) {
        clear();
        addAll(newItems);
        notifyDataSetChanged();
    }
}
