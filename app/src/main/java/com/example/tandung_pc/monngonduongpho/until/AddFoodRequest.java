package com.example.tandung_pc.monngonduongpho.until;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddFoodRequest extends StringRequest {
    private static final String url = Server.DuongdanUploadImage;
    private Map<String, String> params;

    public AddFoodRequest(String name, String address, String descrip, String price, String idcuahang, String typefood_id,
                          String user_id, String comment_id, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("name_food", name);
        params.put("address", address);
        params.put("image", "default");
        params.put("description", descrip);
        params.put("price", price);
        params.put("idcuahang", idcuahang);
        params.put("typefood_id", typefood_id);
        params.put("user_id", user_id);
        params.put("comment_id", comment_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
