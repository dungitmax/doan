package com.example.tandung_pc.monngonduongpho.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.Model.Food;
import com.example.tandung_pc.monngonduongpho.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TANDUNG-PC on 2/24/2018.
 */

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Food> foodsList;

    public FoodListAdapter(Context context, int layout, ArrayList<Food> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtNameFood = row.findViewById(R.id.txtNameFood);
            holder.txtAdress = row.findViewById(R.id.txtAddress);
            holder.imgFood = row.findViewById(R.id.imgFood);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Food food = foodsList.get(position);

        holder.txtNameFood.setText(food.getNameFood());

        Picasso.with(context).load(food.getImage()).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(holder.imgFood);
        holder.txtAdress.setMaxLines(2);
        holder.txtAdress.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtAdress.setText(food.getAddress());
        return row;
    }

    public class ViewHolder {
        TextView txtNameFood, txtAdress;
        ImageView imgFood;
    }
}
