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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by TANDUNG-PC on 2/10/2018.
 */

public class FoodAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Food> listFood;
    ArrayList<Food> listTeam = new ArrayList<>();

    public FoodAdapter(Context context, int layout, List<Food> listFood) {
        this.context = context;
        this.layout = layout;
        this.listTeam.addAll(listFood);
        this.listFood = listFood;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.imgFood = view.findViewById(R.id.imgFood);
            viewHolder.txtNameFood = view.findViewById(R.id.txtNameFood);
            viewHolder.txtAdress = view.findViewById(R.id.txtAddress);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Food food = listFood.get(position);
        viewHolder.txtNameFood.setText(food.getNameFood());
        Picasso.with(context).load(food.getImage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(viewHolder.imgFood);
        viewHolder.txtAdress.setMaxLines(2);
        viewHolder.txtAdress.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtAdress.setText(food.getAddress());
        return view;
    }

    public void Filter(String text) {
        listFood.clear();
        for (Food food : listTeam) {
            if (removeAccent(food.getNameFood().toLowerCase()).contains(removeAccent(text.trim().toLowerCase()))) {
                listFood.add(food);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView txtNameFood, txtAdress;
        ImageView imgFood;
    }
}
