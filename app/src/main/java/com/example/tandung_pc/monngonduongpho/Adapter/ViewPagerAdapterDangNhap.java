package com.example.tandung_pc.monngonduongpho.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tandung_pc.monngonduongpho.View.FragmentDangKi;
import com.example.tandung_pc.monngonduongpho.View.FragmentDangNhap;

public class ViewPagerAdapterDangNhap extends FragmentPagerAdapter {
    public ViewPagerAdapterDangNhap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentDangNhap fragmentDangNhap = new FragmentDangNhap();
                return fragmentDangNhap;
            case 1:
                FragmentDangKi fragmentDangKi = new FragmentDangKi();
                return fragmentDangKi;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ĐĂNG NHẬP";
            case 1:
                return "ĐĂNG KÍ";
            default:
                return null;
        }
    }
}

