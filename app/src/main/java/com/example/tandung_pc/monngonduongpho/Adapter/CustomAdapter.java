package com.example.tandung_pc.monngonduongpho.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.R;

/**
 * Created by TANDUNG-PC on 2/10/2018.
 */

public class CustomAdapter extends BaseAdapter {
    String[] result;
    Context context;
    int[] imageId;

    public CustomAdapter(Context context, String[] result, int[] imageId) {
        this.context = context;
        this.result = result;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_listfood, parent, false);
        TextView tvNoiDung =  rowView.findViewById(R.id.txtNameFood);
        ImageView imgAvatar =  rowView.findViewById(R.id.imgFood);
        tvNoiDung.setText(result[position]);
        imgAvatar.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
