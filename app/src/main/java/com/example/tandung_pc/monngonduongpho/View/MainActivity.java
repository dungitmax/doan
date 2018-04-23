package com.example.tandung_pc.monngonduongpho.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.CheckConnection;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static NavigationView navigationView;
    public static String getten, getGmail;
    public static TextView txtName, txtExit, txtEmail;
    NavigationView navigationViewManHinhChinh;
    Toolbar toolbar;
    DrawerLayout drawerLayoutManHinhChinh;
    boolean doubleBackToExitPressedOnce = false;
    String url_image;
    ImageView mImageView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new HomeFragment()).commit();
                    toolbar.setTitle("Home");
                    return true;
//                case R.id.navigation_news:
//                    transaction.replace(R.id.content, new FragmentNews()).commit();
//                    toolbar.setTitle("Bảng tin");
//                    return true;
                case R.id.navigation_favorite:
                    transaction.replace(R.id.content, new FavoriteFragment()).commit();
                    toolbar.setTitle("Yêu thích");
                    return true;
                case R.id.navigation_map:
                    transaction.replace(R.id.content, new FragmentMap()).commit();
                    toolbar.setTitle("Bản đồ");
                    return true;
                case R.id.navigation_account:
                    NavigationView navigationView = findViewById(R.id.navigationViewManHinhChinh);
                    View hView = navigationView.getHeaderView(0);
                    TextView mTextView = hView.findViewById(R.id.txtName);
                    String name = mTextView.getText().toString().trim();
                    if (name.equals("")) {
                        transaction.replace(R.id.content, new AccountFragment()).commit();
                        toolbar.setTitle("Tài khoản");
                    } else {
                        transaction.replace(R.id.content, new FragmentThongTinTaiKhoan()).commit();
                        toolbar.setTitle("Thông tin tài khoản");
                    }

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!CheckConnection.haveNetworkConnection(getApplicationContext())) {
            Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
        }
        InitView();
        View hView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        txtName = hView.findViewById(R.id.txtName);
        txtEmail = hView.findViewById(R.id.txtGmail);
        mImageView = hView.findViewById(R.id.imgDisplayImage);
        txtExit = hView.findViewById(R.id.txtExit);
        SetOnheader();
        ActionBar();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //keo bottom
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new HomeFragment()).commit();
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        txtName.setText(preferences.getString("username", ""));
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.icontaikhoan));
        //mImageView.setImageDrawable(getResources().getDrawable(R.drawable.icontaikhoan));
        getten = txtName.getText().toString().trim();
        txtEmail.setText(preferences.getString("email", ""));
        String id = preferences.getString("id", "");
        url_image = "https://graph.facebook.com/" + id + "/picture?width=220&height=220";
        Picasso.with(MainActivity.this).load(url_image.toString()).into(mImageView);
        String gmail = txtName.getText().toString().trim();
        getGmail = txtEmail.getText().toString().trim();
        if (gmail.equals("")) {
            txtExit.setEnabled(false);
        } else {
            txtExit.setText("Đăng xuất");
            txtExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    SharedPreferences settings = getSharedPreferences("dangnhap", MODE_PRIVATE);
                    settings.edit().clear().commit();
                    txtExit.setText("");
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.icontaikhoan));
                    startActivity(intent);
                }
            });
        }
    }

    private void InitView() {
        navigationView = findViewById(R.id.navigationViewManHinhChinh);
        toolbar = findViewById(R.id.toolbarManHinhChinh);
        navigationViewManHinhChinh = findViewById(R.id.navigationViewManHinhChinh);
        drawerLayoutManHinhChinh = findViewById(R.id.drawerLayoutManHinhChinh);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutManHinhChinh.openDrawer(GravityCompat.START);
            }
        });
    }

    private void SetOnheader() {
        NavigationView navigationView = findViewById(R.id.navigationViewManHinhChinh);
        View hView = navigationView.getHeaderView(0);
        mImageView = hView.findViewById(R.id.imgDisplayImage);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.icontaikhoan));
        /**xu li su kien trong nav_header o day**/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        if (id == R.id.thongtinlienhe) {
            Intent intent = new Intent(MainActivity.this, Thongtinlienhe.class);
            startActivity(intent);
        } else if (id == R.id.exit) {
            System.exit(1);
        } else if (id == R.id.dangmonan) {
            NavigationView navigationView = findViewById(R.id.navigationViewManHinhChinh);
            View hView = navigationView.getHeaderView(0);
            TextView mTextView = hView.findViewById(R.id.txtName);
            String name = mTextView.getText().toString().trim();
            if (name.equals("")) {
                Toast.makeText(this, "Bạn phải đăng nhập trước khi đăng tin!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, Activity_Add_Next.class);
                startActivity(intent);
                finish();
            }

        }
        DrawerLayout drawer = findViewById(R.id.drawerLayoutManHinhChinh);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //double click for exit app.
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast toast = Toast.makeText(this, "Nhấn BACK thêm 1 lần nữa để thoát", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
