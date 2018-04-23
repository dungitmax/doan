package com.example.tandung_pc.monngonduongpho.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tandung_pc.monngonduongpho.Adapter.ViewPagerAdapterDangNhap;
import com.example.tandung_pc.monngonduongpho.R;

public class AccountFragment extends Fragment {
    TabLayout tabs;
    ViewPager viewPager;
    ViewPagerAdapterDangNhap viewPagerAdapterDangNhap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabs = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapterDangNhap = new ViewPagerAdapterDangNhap(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDangNhap);
        viewPagerAdapterDangNhap.notifyDataSetChanged();
        tabs.setupWithViewPager(viewPager);

    }
}
