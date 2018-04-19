package com.example.tandung_pc.monngonduongpho.View;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.tandung_pc.monngonduongpho.until.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoang on 1/26/2018.
 */

public class CommentRequest extends StringRequest {
    private static final String url = Server.DuongdanSendComment;
    private Map<String, String> params;

    public CommentRequest(String user_id, String food_id, String username, String text_comment, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("user_id", String.valueOf(user_id));
        params.put("food_id", String.valueOf(food_id));
        params.put("username", username);
        params.put("text_comment", text_comment);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
