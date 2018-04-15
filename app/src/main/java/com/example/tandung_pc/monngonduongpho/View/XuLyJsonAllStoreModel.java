package com.example.tandung_pc.monngonduongpho.View;

import com.example.tandung_pc.monngonduongpho.Model.Item_CuaHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class XuLyJsonAllStoreModel {
    public ArrayList<Item_CuaHang> ParserJSONAllStore(String dulieuJson) {
        ArrayList<Item_CuaHang> listLoaiSanPhams = new ArrayList<>();
        try {
            JSONObject mangto = new JSONObject(dulieuJson);
            JSONArray jsonArray = mangto.getJSONArray("cuahang");
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int ch_id = jsonObject.getInt("idcuahang");
                String tencuahang = jsonObject.getString("tencuahang");
                String sdt = jsonObject.getString("sdt");
                String diachi = jsonObject.getString("diadiem");
                String latlng = jsonObject.getString("latlng");
                String linkimg = jsonObject.getString("linkimg");
                int user_id = jsonObject.getInt("user_id");
                listLoaiSanPhams.add(new Item_CuaHang(ch_id, tencuahang,
                        sdt, diachi, latlng, linkimg, user_id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listLoaiSanPhams;
    }
}
