package com.example.monan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThongTinCaNhanActivity extends AppCompatActivity {

    TextView txtHoTen, txtTenDangNhap;

    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        AnhXa();

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtHoTen.setText(DangNhapActivity.account.getHoten());
        txtTenDangNhap.setText(DangNhapActivity.account.getTendangnhap());


    }

    private void AnhXa(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarThemMon);
        txtHoTen = (TextView) findViewById(R.id.textviewHoTen);
        txtTenDangNhap = (TextView) findViewById(R.id.textviewTenDangNhap);

    }
}