package com.example.tandung_pc.monngonduongpho.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tandung_pc.monngonduongpho.Model.LoginRequest;
import com.example.tandung_pc.monngonduongpho.Model.User;
import com.example.tandung_pc.monngonduongpho.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentDangNhap extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean clickFace = false;
    CallbackManager callbackManager;
    Button btnFacebook, btnDangNhap;
    EditText edtUsername, edtMK;
    String tennguoidung, email, id;
    ArrayList<User> mangUsers;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangnhap, container, false);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
                Log.d("kiemtra", "thanhcong");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                try {
                    FragmentDangNhap.this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }


            }

            @Override
            public void onCancel() {
                Log.d("kiemtra", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("kiemtra", "onError");
            }
        });


        btnDangNhap = view.findViewById(R.id.btnDangNhap);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtMK = view.findViewById(R.id.edtMK);
        btnFacebook = view.findViewById(R.id.btnFacebook);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFace = false;
                final String username = edtUsername.getText().toString();
                final String password = edtMK.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                boolean success = object.getBoolean("success");
                                if (success) {
                                    //dang nhap thanh cong!
                                    Toast.makeText(getActivity(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//                                    intent.putExtra("dangnhap", "dangnhap");
//                                    intent.putExtra("image", R.drawable.helloo);

                                    //luu sesson dang nhap
                                    SharedPreferences preferences = getContext().getSharedPreferences("dangnhap", getContext().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    int user_id = object.getInt("user_id");
                                    String name = object.getString("name_User");
                                    String username = object.getString("username");
                                    String address = object.getString("address");
                                    editor.putInt("iduser", user_id);
                                    editor.putString("name", name);
                                    editor.putString("username", username);
                                    editor.putString("address", address);
                                    editor.putString("exit", "Đăng xuất");
                                    editor.commit();
                                    startActivity(intent);
                                    getActivity().finish();
//                                    NavigationView nav = (NavigationView) view.findViewById(R.id.navigationViewManHinhChinh);
//                                    if (nav != null) {
//                                        LinearLayout mParent = (LinearLayout) nav.getHeaderView(0);
//                                        if (mParent != null) {
//                                            SharedPreferences prefs = getActivity().getSharedPreferences("login", MODE_PRIVATE);
//                                            String username = prefs.getString("username", null);
//                                            String password = prefs.getString("password", "User");
//                                        }
//                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Tài khoản hoặc Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest request = new LoginRequest(username, password, listener);
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);
                    ////////////////////////////////////////
                }
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFace = true;
                LoginManager.getInstance().logInWithReadPermissions(FragmentDangNhap.this, Arrays.asList("public_profile", "email", "user_birthday"));
            }
        });


        return view;
    }

//    private void GetData(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//                        int user_id = object.getInt("user_id");
//                        String name_User = object.getString("name_User");
//                        String username = object.getString("username");
//                        String address = object.getString("address");
//                        mangUsers.add(new User(user_id, name_User, username, address));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }


    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("aaaaa", response.getJSONObject().toString());
                try {
                    tennguoidung = object.getString("name");
                    email = object.getString("email");
                    id = object.getString("id");
                    Log.d("facee", tennguoidung + email + id);

                    Intent intent = new Intent(getActivity(), MainActivity.class);

                    SharedPreferences preferences = getContext().getSharedPreferences("dangnhapface", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    clickFace = true;
                    editor.putBoolean("loginface", clickFace);
                    editor.putInt("1", R.mipmap.ic_launcher);
                    editor.putString("username", tennguoidung);
                    editor.putString("email", email);
                    editor.putString("id", id);
                    editor.putString("exit", "Đăng xuất");
                    editor.commit();
                    startActivity(intent);
                    Log.d("tennguoidung", tennguoidung);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

