package com.example.tandung_pc.monngonduongpho.View;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandung_pc.monngonduongpho.Adapter.FoodAdapter;
import com.example.tandung_pc.monngonduongpho.Model.Food;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MonCheActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView lv;
    ArrayList<Food> mangfood;
    FoodAdapter adapter;
    String urlGetdata = Server.DuongdanMonChe;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_che);
        Anhxa();
        Actiontoolbar();
        GetData(urlGetdata);
        // LoadMoreData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Detail_Screen_Food.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", mangfood.get(position));
                intent.putExtra("data", bundle);

                startActivity(intent);
            }
        });
        swipe.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
        swipe.setDistanceToTriggerSync(300);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setOnRefreshListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchManager searchManager = (SearchManager) MonCheActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchManager != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MonCheActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.Filter(newText);
                    lv.invalidate();
                    return true;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int food_id = object.getInt("food_id");
                        String name_food = object.getString("name_food");
                        String address_food = object.getString("address");
                        String description = object.getString("description");
                        String image_food = object.getString("image");
                        String price = object.getString("price");
                        int typefood_id = object.getInt("typefood_id");
                        mangfood.add(new Food(food_id, name_food, address_food, image_food, description, price, typefood_id));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new FoodAdapter(getApplicationContext(), R.layout.custom_listfood, mangfood);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarID);
        lv = findViewById(R.id.listMonChe);
        mangfood = new ArrayList<>();
        swipe = findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onRefresh() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                swipe.setRefreshing(false);
                adapter = new FoodAdapter(getApplicationContext(), R.layout.custom_listfood, mangfood);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }.start();
    }
}
