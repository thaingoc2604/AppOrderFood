package com.example.monan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class thongtinungdung extends AppCompatActivity {
    ListView lvdanhsachchucnang;
    ArrayAdapter<String> adapter = null;

    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinungdung);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarThemMon);
        lvdanhsachchucnang = (ListView) findViewById(R.id.lvDanhSachChucNang);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin Ứng dụng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongtinungdung.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final String arr[] = {"1. Đăng Nhập","2. Gọi Món","3. Xem danh sách món ăn","4. Xem danh sách loại món ăn","5. Thêm món ăn",
        "6. Xóa món ăn","7. Sửa món ăn","8. Xem danh sách món ăn được gọi","9. Thanh Toán"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arr);
        lvdanhsachchucnang.setAdapter(adapter);
    }
}