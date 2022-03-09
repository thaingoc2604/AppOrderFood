package com.example.monan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ViewFlipper viewBanner;
    Animation enter, exit;
    ImageView imgIconInfoApp, imgIconDatMon, imgIconQuanLy, imgIconMonAn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        //Toolbar
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Thongtin:
                        Intent intentThongTin = new Intent(MainActivity.this, ThongTinCaNhanActivity.class);
                        startActivity(intentThongTin);
                        break;
                    case R.id.Thoat:
                        AlertDialog.Builder dialogThoat = new AlertDialog.Builder(MainActivity.this);
                        dialogThoat.setTitle("Bạn muốn thoát khỏi ứng dụng");
                        dialogThoat.setMessage("Bạn có chắc chắn?");
                        dialogThoat.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent startMain = new Intent(Intent.ACTION_MAIN);
                                startMain.addCategory(Intent.CATEGORY_HOME);
                                startActivity(startMain);
                                finish();
                            }
                        });
                        dialogThoat.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialogThoat.show();
                        break;
                    case R.id.DangXuat:
                        String thongbao = "Đã đăng xuất";
                        Intent intentDangXuat = new Intent(MainActivity.this, DangNhapActivity.class);
                        intentDangXuat.putExtra("dangxuat", thongbao);
                        startActivity(intentDangXuat);

                        break;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Animation chuyen hinh banner
        enter = AnimationUtils.loadAnimation(this, R.anim.anim_enter);
        exit = AnimationUtils.loadAnimation(this, R.anim.anim_exit);

        //Hien thi banner
        viewBanner.setInAnimation(enter);
        viewBanner.setOutAnimation(exit);
        viewBanner.setFlipInterval(3000);
        viewBanner.setAutoStart(true);



        //Su kien click cac imgIcon
        imgIconInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTrangChu = new Intent(MainActivity.this, thongtinungdung.class);
                startActivity(intentTrangChu);
                overridePendingTransition(0,0);
            }
        });

        //Su kien click cac imgIcon
        imgIconDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, XemGioHang.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
            }
        });
        imgIconMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMonAn = new Intent(MainActivity.this, LoaiMonAnActivity.class);
                startActivity(intentMonAn);

                Intent intentTrangChu = new Intent(MainActivity.this, LoaiMonAnActivity.class);
                intentTrangChu.putExtra("login", DangNhapActivity.account);
                startActivity(intentTrangChu);
                overridePendingTransition(0,0);
            }
        });

        imgIconQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhapActivity.account.getLoaiquyen() == 0){
                    Intent intentQuanLy = new Intent(MainActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intentQuanLy);
                    overridePendingTransition(0,0);
                } else
                    Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
            }
        });

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.thong_tin_nguoi_dung, menu);

        return super.onCreateOptionsMenu(menu);
    }



    public void AnhXa(){
        viewBanner = (ViewFlipper) findViewById(R.id.viewBanner);
        imgIconInfoApp = (ImageView) findViewById(R.id.imageviewInfoApp);
        imgIconMonAn = (ImageView) findViewById(R.id.imageviewIconMonAn);
        imgIconDatMon = (ImageView) findViewById(R.id.imageviewDatMon);
        imgIconQuanLy = (ImageView) findViewById(R.id.imageviewIconQuanLy);
    }


}