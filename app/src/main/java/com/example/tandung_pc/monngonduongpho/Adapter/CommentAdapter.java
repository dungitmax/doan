package com.example.tandung_pc.monngonduongpho.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.Model.ModelComment;
import com.example.tandung_pc.monngonduongpho.R;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    ArrayList<ModelComment> arrayListComment;
    Context context;

    public CommentAdapter(ArrayList<ModelComment> arrayListComment, Context context) {
        this.arrayListComment = arrayListComment;
        this.context = context;
    }

    @Override
    public int getCount() {
        //tra ve cac gia tri trong mang
        return arrayListComment.size();
    }

    @Override
    public Object getItem(int i) {
        //get gia tri tung thuoc tinh trong mang
        return arrayListComment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CommentAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new CommentAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_comment, null); //nap vao donglisviewloaisp
            viewHolder.txtvUsetnameComment = view.findViewById(R.id.tv_cmt_name);
            viewHolder.txtvValueComment = view.findViewById(R.id.tv_cmt_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CommentAdapter.ViewHolder) view.getTag();
        }
        ModelComment modelComment = (ModelComment) getItem(i);
        viewHolder.txtvUsetnameComment.setText(modelComment.getUsername());
        viewHolder.txtvValueComment.setText(modelComment.getNoidung());
        return view;
    }

    //Load du lieu lan dau se bat nhung anh xa,nhung lan sau khong phai load lai (ho tro load du lieu)
    public class ViewHolder {
        TextView txtvUsetnameComment, txtvValueComment;
    }
}

