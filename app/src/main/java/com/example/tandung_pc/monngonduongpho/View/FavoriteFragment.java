package com.example.tandung_pc.monngonduongpho.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.Adapter.FoodListAdapter;
import com.example.tandung_pc.monngonduongpho.Model.Food;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.SQLite.MyDatabaseHelper;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView lvYeuThich;
    FoodListAdapter adapter;
    ArrayList<Food> list = new ArrayList<>();
    SwipeRefreshLayout swipe;
    Integer id;
    private MyDatabaseHelper helper;
    private TextView txtThongBao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        lvYeuThich = view.findViewById(R.id.lvYeuThich);
        txtThongBao = view.findViewById(R.id.txtThongBao);
        swipe = view.findViewById(R.id.swipeRefreshLayout);
        helper = new MyDatabaseHelper(getActivity().getApplicationContext(), "Foodd.db", null, 1);
        adapter = new FoodListAdapter(getActivity(), R.layout.custom_listfood, list);
        Cursor cursor = helper.getData("SELECT * FROM FOOD");
        lvYeuThich.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String image = cursor.getString(3);
            String description = cursor.getString(4);
            String price = cursor.getString(5);
            Integer typefoodId = cursor.getInt(6);
            list.add(new Food(id, name, address, image, description, price, typefoodId));
        }
        Log.d("listt", String.valueOf(list.size()));
        adapter.notifyDataSetChanged();
        ThongBao();
        lvYeuThich.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ActivityFoodLove.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", list.get(i));
                intent.putExtra("data", bundle);
                getActivity().startActivity(intent);
            }
        });
        lvYeuThich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                helper = new MyDatabaseHelper(getActivity(), "Foodd.db", null, 1);
                                helper.deleteData(id);
                                lvYeuThich.invalidateViews();
                                adapter.notifyDataSetChanged();
                                Log.d("listt", String.valueOf(list.size()));
                                Toast.makeText(getActivity(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Log.d("listt", String.valueOf(list.size()));
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có chắc chắn muốn xóa?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            }
        });
        // registerForContextMenu(lvYeuThich);
        swipe.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
        swipe.setDistanceToTriggerSync(300);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setOnRefreshListener(this);
        return view;
    }

    private void ThongBao() {
        if (list.size() <= 0) {
            txtThongBao.setVisibility(View.VISIBLE);
            lvYeuThich.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            txtThongBao.setVisibility(View.INVISIBLE);
            lvYeuThich.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                swipe.setRefreshing(false);
                adapter = new FoodListAdapter(getActivity(), R.layout.custom_listfood, list);
                lvYeuThich.setAdapter(adapter);

            }
        }.start();
    }
}

