package com.example.tandung_pc.monngonduongpho.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDangKi extends Fragment implements View.OnFocusChangeListener {
    EditText edtHoten, edtUsername, edtPassword, edtPasswordNew, edtAddress;
    Button btnDangKi;
    TextInputLayout input_editHoten;
    TextInputLayout input_editUsername;
    TextInputLayout input_Password;
    TextInputLayout input_edtNewMK;
    TextInputLayout input_edtAddress;
    boolean kiemtra = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangki, container, false);
        edtHoten = view.findViewById(R.id.edtHoten);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtPasswordNew = view.findViewById(R.id.edtPasswordNew);
        edtAddress = view.findViewById(R.id.edtAddress);
        input_editHoten = view.findViewById(R.id.input_edtHoten);
        input_editUsername = view.findViewById(R.id.input_edtEmail);
        input_Password = view.findViewById(R.id.input_edtMK);
        input_edtNewMK = view.findViewById(R.id.input_edtNewMK);
        input_edtAddress = view.findViewById(R.id.input_edtAddress);
        btnDangKi = view.findViewById(R.id.btnDangKi);
        edtHoten.setOnFocusChangeListener(this);
        edtUsername.setOnFocusChangeListener(this);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edtHoten.getText().toString();
                final String userName = edtUsername.getText().toString();
                final String passWord = edtPassword.getText().toString();
                final String passwordNew = edtPasswordNew.getText().toString();
                final String address = edtAddress.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanRegister, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        if (name.equals("") || userName.equals("") || passWord.equals("")) {
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                        } else if (passWord.length() < 6) {
                            Toast.makeText(getContext(), "Mật khẩu phải ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches() && !Patterns.PHONE.matcher(userName).matches() || userName.length() < 10) {
                            Toast.makeText(getContext(), "Tài khoản không hợp lệ !", Toast.LENGTH_SHORT).show();
                        } else if (!passwordNew.equals(passWord)) {
                            Toast.makeText(getContext(), "Nhập lại sai mật khẩu!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                object = new JSONObject(response);
                                boolean success = object.getBoolean("success");
                                if (success == true) {
                                    Toast.makeText(getActivity(), "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("name_User", name);
                        hashMap.put("username", userName);
                        hashMap.put("password_User", passWord);
                        hashMap.put("address", address);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id) {
            case R.id.edtHoten:
                if (!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    if (chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_editHoten.setErrorEnabled(false);
                        input_editHoten.setError("Bạn chưa nhập họ tên!");
                        kiemtra = false;

                    } else {
                        input_editHoten.setErrorEnabled(false);
                        input_editHoten.setError("");
                        kiemtra = true;
                    }
                }
                break;
            case R.id.edtUsername:
                if (!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    if (chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_editUsername.setErrorEnabled(true);
                        input_editUsername.setError("Bạn chưa nhập tài khoản !");
                        kiemtra = false;

                    } else if (!Patterns.EMAIL_ADDRESS.matcher(chuoi).matches() && !Patterns.PHONE.matcher(chuoi).matches() || chuoi.length() < 10) {
                        input_editUsername.setErrorEnabled(true);
                        input_editUsername.setError(" Tài khoản không hợp lệ !");
                        kiemtra = false;
                    } else {
                        input_editUsername.setErrorEnabled(false);
                        input_editUsername.setError("");
                        kiemtra = true;
                    }
                }
                break;
            case R.id.edtPassword:
                if (!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    if (chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_Password.setErrorEnabled(true);
                        input_Password.setError("Bạn chưa nhập mật khẩu !");
                        kiemtra = false;

                    } else if (chuoi.length() < 6) {
                        input_Password.setErrorEnabled(true);
                        input_Password.setError("Mật khẩu không hợp lệ !");
                        kiemtra = false;
                    } else {
                        input_Password.setErrorEnabled(false);
                        input_Password.setError("");
                        kiemtra = true;
                    }
                }
                break;
            case R.id.edtAddress:
                if (!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    if (chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_edtAddress.setErrorEnabled(false);
                        input_edtAddress.setError("Bạn chưa nhập địa chỉ!");
                        kiemtra = false;

                    } else {
                        input_edtAddress.setErrorEnabled(false);
                        input_edtAddress.setError("");
                        kiemtra = true;
                    }
                }
                break;

        }
    }
}
