package com.example.tandung_pc.monngonduongpho.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandung_pc.monngonduongpho.Adapter.CommentAdapter;
import com.example.tandung_pc.monngonduongpho.Model.Food;
import com.example.tandung_pc.monngonduongpho.Model.ModelComment;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.SQLite.MyDatabaseHelper;
import com.example.tandung_pc.monngonduongpho.until.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detail_Screen_Food extends AppCompatActivity {

    public static MyDatabaseHelper helper;
    public static Food food;
    Toolbar toolbarChitietFood;
    ImageView img_food, btn_sendcoment;
    TextView txtTenfood, txtGiafood, txtDiadiem, txtMotafood;
    ListView lvComment;
    String Tenchitiet = "";
    String Giachitiet = "";
    String Hinhanhchitiet = "";
    String Diadiem = "";
    String Motachitiet = "";
    SharedPreferences preferences;
    LinearLayout layoutListView, layoutComment;
    int IdTypeFood = 0;
    int maFood;
    int id;
    boolean checkSame = false;
    String name = "";
    String namee = "";
    ArrayList<ModelComment> arrayComment;
    ArrayList<String> listNameFood;
    CommentAdapter commentAdapter;
    EditText edtNoidung;
    private TextView txtThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__screen__food);
        InitView();
        Actiontoolbar();
        GetInfomation();
        Getdata();
        sendComment();
        helper = new MyDatabaseHelper(this, "Foodd.db", null, 1);
        helper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY, name VARCHAR,address TEXT,image TEXT,description TEXT,price TEXT,typefoodId INTEGER)");
    }


    private void sendComment() {
        btn_sendcoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoidung.getText().toString().trim();
                if (noidung.equals("")) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập nội dung đánh giá !", Toast.LENGTH_SHORT).show();
                } else if (MainActivity.getten.equals("")) {
                    Toast.makeText(Detail_Screen_Food.this, "Vui lòng đăng nhập !", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            edtNoidung.setText("");
                            arrayComment = new ArrayList<>();
                            commentAdapter = new CommentAdapter(arrayComment, getApplicationContext());
                            lvComment.setAdapter(commentAdapter);
                            Getdata();
                            GetUser();
                            Toast.makeText(getApplicationContext(), "Bình luận thành công !", Toast.LENGTH_SHORT).show();
                        }
                    };
                    preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
                    int idUser = preferences.getInt("iduser", 100);
                    Log.d("idd", String.valueOf(idUser));
                    CommentRequest request = new CommentRequest(idUser, maFood, MainActivity.getten, noidung, listener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    String duongdan = Server.DuongdanSendComment;
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.printStackTrace();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            HashMap<String, String> param = new HashMap<String, String>();
//                            param.put("comment_id", "1");
//                            param.put("user_id",String.valueOf(maFood));
//                            param.put("username", String.valueOf(maFood));
//                            param.put("text_comment", String.valueOf(maFood));
//                            return param;
//                        }
//                    };
//                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    private void GetUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.DuongdanGetUser, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("user_id");
                        String username = jsonObject.getString("username");
                        name = jsonObject.getString("name_User");
                        String diachi = jsonObject.getString("address");


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

    }

    private void GetInfomation() {
        Bundle bundle = getIntent().getBundleExtra("data");
        food = (Food) bundle.getSerializable("list");
        //
        maFood = food.getFoodId();
        Tenchitiet = food.getNameFood();
        Giachitiet = food.getPrice();
        Diadiem = food.getAddress();
        Motachitiet = food.getDescription();
        Hinhanhchitiet = food.getImage();
        IdTypeFood = food.getTypefoodId();
        txtTenfood.setText(Tenchitiet);
        txtMotafood.setText(Motachitiet);
        txtDiadiem.setText(Diadiem);
        txtGiafood.setText(Giachitiet);
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(img_food);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuFavorite) {
            String name = txtTenfood.getText().toString();
            Log.d("sizee", name);
            String address = txtDiadiem.getText().toString();
            String description = txtMotafood.getText().toString();
            String price = txtGiafood.getText().toString();
            //
            int size = FavoriteFragment.listAll.size();
            Log.d("sizee", String.valueOf(size));
            for (int i = 0; i < size; i++) {
                namee = FavoriteFragment.listAll.get(i);
                Log.d("sizee", namee);
                if (name.equals(namee)) {
                    checkSame = true;
                    Toast.makeText(this, "Món ăn đã được yêu thích!", Toast.LENGTH_SHORT).show();
                }
            }
            if (checkSame == false) {
                if (name != null && item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.favorite).getConstantState())) {
                    helper.insertData(food.getFoodId(), name, address, food.getImage(), description, price, food.getTypefoodId());
                    item.setIcon(R.drawable.favorite_1);
                    Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    helper.deleteData(food.getFoodId());
                    item.setIcon(R.drawable.favorite);
                    Toast.makeText(getApplicationContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbarChitietFood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitietFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void InitView() {
        toolbarChitietFood = findViewById(R.id.toolbarID);
        img_food = findViewById(R.id.img_food);
        txtTenfood = findViewById(R.id.txtTenmonan);
        txtGiafood = findViewById(R.id.txtGiamonan);
        txtDiadiem = findViewById(R.id.txtDiadiem);
        txtMotafood = findViewById(R.id.txtMotachitiet);
        lvComment = findViewById(R.id.lvComment);
        edtNoidung = findViewById(R.id.edt_noidung1);
        layoutListView = findViewById(R.id.layoutListView);
        layoutComment = findViewById(R.id.layoutComment);
        btn_sendcoment = findViewById(R.id.btn_sendcoment);
        txtThongBao = findViewById(R.id.txtThongBao);
        arrayComment = new ArrayList<>();
        commentAdapter = new CommentAdapter(arrayComment, getApplicationContext());
        lvComment.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    private void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdangetcoment;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String username1;
                String noidung1;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            username1 = jsonObject.getString("username");
                            noidung1 = jsonObject.getString("text_comment");
                            arrayComment.add(new ModelComment(username1, noidung1));
                            ThongBao();
                            commentAdapter.notifyDataSetChanged();
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("food_id", String.valueOf(maFood));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ThongBao() {
        if (arrayComment.size() <= 0) {
            txtThongBao.setVisibility(View.VISIBLE);
            lvComment.setVisibility(View.INVISIBLE);
            commentAdapter.notifyDataSetChanged();
        } else {
            txtThongBao.setVisibility(View.INVISIBLE);
            lvComment.setVisibility(View.VISIBLE);
            commentAdapter.notifyDataSetChanged();
        }
    }
}
