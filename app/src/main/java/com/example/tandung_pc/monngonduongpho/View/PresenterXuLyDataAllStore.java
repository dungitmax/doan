package com.example.tandung_pc.monngonduongpho.View;

import android.content.Context;

import com.example.tandung_pc.monngonduongpho.Model.Item_CuaHang;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PresenterXuLyDataAllStore implements ImpPresenterXuLyDataAllStore {

    ViewAllStore viewAllStore;

    public PresenterXuLyDataAllStore(ViewAllStore viewAllStore) {
        this.viewAllStore = viewAllStore;
    }

    @Override
    public void layDanhSachStore(Context context) {
        ArrayList<Item_CuaHang> list_Gas;
        String dataJSON="";
        String duongDan = "http://localhost/get_all_cuahang.php";
        DownJson downJson = new DownJson(duongDan,context);
        downJson.execute();

        try {
            dataJSON = downJson.get();
            XuLyJsonAllStoreModel xuLyJsonAllStoreModel = new XuLyJsonAllStoreModel();
            list_Gas = xuLyJsonAllStoreModel.ParserJSONAllStore(dataJSON);
            viewAllStore.hienThiDanhSachStore(list_Gas);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
