package com.example.tandung_pc.monngonduongpho.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.Model.Item_CuaHang;
import com.example.tandung_pc.monngonduongpho.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterRecylerViewAllStore extends RecyclerView.Adapter<AdapterRecylerViewAllStore.ViewHolder> {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    List<Item_CuaHang> ds = null;
    Context context;
    int position;
    private ArrayList<Item_CuaHang> arrayList = new ArrayList<>();
    private ClickListener clickListener;

    public AdapterRecylerViewAllStore(ArrayList<Item_CuaHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public AdapterRecylerViewAllStore(List<Item_CuaHang> ds, Context context, ClickListener clickListener) {
        this.ds = ds;
        this.arrayList.addAll(ds);
        this.context = context;
        this.clickListener = clickListener;

    }

    @Override
    public void onBindViewHolder(AdapterRecylerViewAllStore.ViewHolder holder, int position) {

        Item_CuaHang food = ds.get(position);
        holder.txtTenCuaHang.setText(food.getTencuahang());
        holder.txtDiaChi.setText(food.getDiadiem());
        holder.txtSDT.setText(food.getSdt());
        Picasso.with(context)
                .load(food.getLinkimg())
                .into(holder.imgCuaHang);
    }

    @Override
    public AdapterRecylerViewAllStore.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_all_store, null);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return ds.size();
    }

    public Object getItem(int position) {

        return ds.get(position);
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askpmis() {
        int hasWriteContactsPermission = context.checkSelfPermission(android.Manifest.permission.CALL_PHONE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ds.clear();
        if (charText.length() == 0) {
            ds.addAll(arrayList);
        } else {
            for (Item_CuaHang wp : arrayList) {
                if (wp.getDiadiem().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ds.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenCuaHang, txtDiaChi, txtSDT;
        ImageView imgCuaHang;
        LinearLayout lnComment;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            txtTenCuaHang = v.findViewById(R.id.txtTenCuaHangHome);
            txtDiaChi = v.findViewById(R.id.txtDiaChiHome);
            txtSDT = v.findViewById(R.id.txtSDTHome);
            imgCuaHang = v.findViewById(R.id.imgHome);
            lnComment = v.findViewById(R.id.lnComment);
        }

        @Override
        public void onClick(View view) {
            Log.d("position", String.valueOf(getAdapterPosition()));
            clickListener.onItemClick(getAdapterPosition(), view);

        }
    }


}

