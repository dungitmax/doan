package com.example.tandung_pc.monngonduongpho.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.Adapter.CustomGrid;
import com.example.tandung_pc.monngonduongpho.R;



public class HomeFragment extends Fragment {
    GridView grid;
    String[] title = {
            "Bún,Phở",
            "Món chè",
            "Nước uống",
            "Đồ chiên,nướng",
            "Bánh mì",
            "Các món khác"
    };
    int[] imageId = {
            R.drawable.bunpho,
            R.drawable.che,
            R.drawable.nuocuong,
            R.drawable.dochiennuong,
            R.drawable.banhmi,
            R.drawable.cacmonkhac
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        grid = view.findViewById(R.id.gridView);
        CustomGrid adapter = new CustomGrid(getActivity().getApplicationContext(), title, imageId);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity(), BunPhoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), MonCheActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), NuocUongActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), ChienNuongActivity.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(getActivity(), BanhMiActivity.class);
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(getActivity(), OtherActivity.class);
                        startActivity(intent6);
                        break;
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
