package com.example.tandung_pc.monngonduongpho.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.R;

/**
 * Created by TANDUNG-PC on 2/5/2018.
 */

public class CustomGrid extends BaseAdapter {
    private final String[] title;
    private final int[] Imageid;
    private Context mContext;

    public CustomGrid(Context mContext, String[] title, int[] imageid) {
        this.mContext = mContext;
        this.title = title;
        Imageid = imageid;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            grid = inflater.inflate(R.layout.custom_gridview, null);
            TextView textView = grid.findViewById(R.id.grid_text);
            ImageView imageView = grid.findViewById(R.id.grid_image);
            textView.setText(title[i]);
            imageView.setImageResource(Imageid[i]);
        } else {
            grid = view;
        }
        return grid;
    }
}
