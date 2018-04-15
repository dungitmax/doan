package com.example.tandung_pc.monngonduongpho.Model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.tandung_pc.monngonduongpho.until.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TANDUNG-PC on 2/25/2018.
 */

public class LoginRequest extends StringRequest {
    private final static String url = Server.DuongdanLogin;
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password_User", password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}