package com.example.tandung_pc.monngonduongpho.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.DuongdanGetUser, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("user_id");
                        String username = jsonObject.getString("username");
                        String name = jsonObject.getString("name_User");
                        String diachi = jsonObject.getString("address");
                        if (MainActivity.getten.equals(username)) {
                            txtTen.setText(name);
                            txtUsername.setText(username);
                            txtDiachi.setText(diachi);
                            MainActivity.txtEmail.setText(username);
                            MainActivity.txtName.setText(name);
                            iduser = id;
                        }
                        if (MainActivity.getGmail.equals("ltandungit@gmail.com")) {
                            txtTen.setText("Lê Tấn Dũng");
                            txtUsername.setText("ltandungit@gmail.com");
                            txtDiachi.setText("Nam Phú-Tiền Hải-Thái Bình");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        requestQueue.add(jsonArrayRequest);
        return view;
    }
}
