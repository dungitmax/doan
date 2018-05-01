package com.example.tandung_pc.monngonduongpho.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.R;

/**
 * Created by TANDUNG-PC on 3/18/2018.
 */

public class FragmentThongTinTaiKhoan extends android.support.v4.app.Fragment {
    public static String iduser = "";
    TextView txtTen, txtUsername, txtDiachi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thongtintaikhoan, container, false);
        txtTen = view.findViewById(R.id.txtTen);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtDiachi = view.findViewById(R.id.txtDiachi);
        //
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.DuongdanGetUser, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        String id = jsonObject.getString("user_id");
//                        String username = jsonObject.getString("username");
//                        String name = jsonObject.getString("name_User");
//                        String diachi = jsonObject.getString("address");
//                        if (MainActivity.getten.equals(username) && FragmentDangNhap.clickFace == false) {
//                            txtTen.setText(name);
//                            txtUsername.setText(username);
//                            txtDiachi.setText(diachi);
//                            MainActivity.txtEmail.setText(username);
//                            MainActivity.txtName.setText(name);
//                            iduser = id;
//                        } else if (FragmentDangNhap.clickFace == true) {
//                            txtTen.setText(MainActivity.getten);
//                            txtUsername.setText(MainActivity.getGmail);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
        SharedPreferences preferences = getActivity().getSharedPreferences("dangnhap", getActivity().MODE_PRIVATE);
        txtTen.setText(preferences.getString("name", ""));
        txtUsername.setText(preferences.getString("username", ""));
        txtDiachi.setText(preferences.getString("address", ""));
        SharedPreferences preferences1 = getActivity().getSharedPreferences("dangnhapface", getActivity().MODE_PRIVATE);
        boolean loginface = preferences1.getBoolean("loginface", false);
        if (loginface) {
            txtTen.setText(preferences1.getString("username", ""));
            txtUsername.setText(preferences1.getString("email", ""));
            txtDiachi.setText("N/A");
        }

        return view;
    }
}
