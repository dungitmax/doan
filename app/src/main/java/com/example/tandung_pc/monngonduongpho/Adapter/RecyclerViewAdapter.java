//package com.example.tandung_pc.monngonduongpho.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.tandung_pc.monngonduongpho.Model.ItemFood;
//import com.example.tandung_pc.monngonduongpho.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by TANDUNG-PC on 2/9/2018.
// */
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
//    private Context context;
//    private List<ItemFood> data = new ArrayList<>();
//
//    public RecyclerViewAdapter(List<ItemFood> data) {
//        this.data = data;
//    }
//
//    @Override
//    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.custom_listfood, parent, false);
//        return new RecyclerViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
//        ItemFood itemFood = data.get(position);
//        holder.txtNameFood.setText(itemFood.getNameFood());
//        Picasso.with(context).load(itemFood.getImage()).placeholder(R.drawable.noimage)
//                .error(R.drawable.error).into(holder.imgFood);
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//
//    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
//        TextView txtNameFood;
//        ImageView imgFood;
//
//        public RecyclerViewHolder(View itemView) {
//            super(itemView);
//            txtNameFood = itemView.findViewById(R.id.txtNameFood);
//            imgFood = itemView.findViewById(R.id.imgFood);
//        }
//    }
//}
