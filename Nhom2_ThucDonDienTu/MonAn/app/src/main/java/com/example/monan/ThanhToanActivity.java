package com.example.monan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {

    TextView txtSoHD, txtNgayLapHD, txtTenMonAn, txtSoLuong, txtGia, txtTongTien, txtSoTienTra, txtTienTraLaiKhach, txtSoBan;
    EditText edtSoTienDua;
    Button btnXong, btnCapNhat;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    String sotiendua = "";
    int tiendua;
    int tientra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        AnhXa();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarThanhToan);

        //Hiển thị toolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thanh toán");
        //Click trở về trang trước
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        MonAnGioHang monAnGioHang = (MonAnGioHang) intent.getSerializableExtra("data");


        txtSoHD.setText(" 000"+monAnGioHang.getId()+"");
        txtSoBan.setText(" "+monAnGioHang.getMaban()+"");

        txtTenMonAn.setText(monAnGioHang.getTenmon());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy "+ " h:mm a");
        txtNgayLapHD.append(dinhDangNgay.format(calendar.getTime()));

        txtSoLuong.setText(monAnGioHang.getSoluong()+"");
        // tạo 1 NumberFormat để định dạng số theo tiêu chuẩn của nước Anh
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        // đối với số có kiểu long được định dạng theo chuẩn của nước Anh
        // thì phần ngàn của số được phân cách bằng dấu phẩy

        txtGia.setText(en.format(monAnGioHang.getDongia())+"");
        txtTongTien.setText(en.format(monAnGioHang.getThanhtien())+"");
        txtSoTienTra.setText(en.format(monAnGioHang.getThanhtien())+"");

        edtSoTienDua.setText(monAnGioHang.getThanhtien()+"");

        edtSoTienDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoTienDua.setText("");
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sotiendua = edtSoTienDua.getText().toString();

                if ( sotiendua.equals("")){
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                }
                else {
                     tiendua = Integer.parseInt(sotiendua);
                     tientra = tiendua - monAnGioHang.getThanhtien();
                    txtTienTraLaiKhach.setText(en.format(tientra));
                }
            }
        });

        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tientra < 0){
                    Toast.makeText(ThanhToanActivity.this,"Số tiền không hợp lệ! Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ThanhToanActivity.this, "Đã thanh toán thành công!", Toast.LENGTH_SHORT).show();
                    finish();

                }

            }
        });

    }


    private void AnhXa(){
        txtSoHD = (TextView) findViewById(R.id.txtSoHoaDon_ThanhToan);
        txtSoBan = (TextView) findViewById(R.id.txtSoBan);
        txtNgayLapHD = (TextView) findViewById(R.id.txtNgayLapHD);
        txtTenMonAn = (TextView) findViewById(R.id.txtTenMonAn_ThanhToan);
        txtSoLuong = (TextView) findViewById(R.id.txtSoLuong_ThanhToan);
        txtGia = (TextView) findViewById(R.id.txtGia_ThanhToan);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien_ThanhToan);
        txtSoTienTra = (TextView) findViewById(R.id.txtSoTienTra);
        txtTienTraLaiKhach = (TextView) findViewById(R.id.txtTienTraLaiKhach);
        edtSoTienDua = (EditText) findViewById(R.id.txtSoTienDua);
        btnXong = (Button) findViewById(R.id.buttonXong);
        btnCapNhat = (Button) findViewById(R.id.buttonCapNhapHoaDon);
    }
}