package com.example.tandung_pc.monngonduongpho.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.Adapter.CommentAdapter;
import com.example.tandung_pc.monngonduongpho.Model.Food;
import com.example.tandung_pc.monngonduongpho.Model.ModelComment;
import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.SQLite.MyDatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityFoodLove extends AppCompatActivity {

    public static MyDatabaseHelper helper;
    public static Food food;
    Toolbar toolbarChitietFood;
    ImageView img_food;
    TextView txtTenfood, txtGiafood, txtDiadiem, txtMotafood;
    ListView lvComment;
    String Tenchitiet = "";
    String Giachitiet = "";
    String Hinhanhchitiet = "";
    String Diadiem = "";
    String Motachitiet = "";
    int IdTypeFood = 0;
    int maFood;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_love);
        InitView();
        Actiontoolbar();
        GetInfomation();
        helper = new MyDatabaseHelper(this, "Foodd.db", null, 1);
        helper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY, name VARCHAR,address TEXT,image TEXT,description TEXT,price TEXT,typefoodId INTEGER)");
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
    }
}
