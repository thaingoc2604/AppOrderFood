package com.example.monan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SuaChonMonActivity extends AppCompatActivity {


    ImageView imgHinh;
    TextView txtTenMonAn, txtGia, txtSoLuong, txtTongTien;
    Button btnGiamSL, btnTangSL, btnChonMon;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Spinner spinner_banAn;
    int soluong = 1;
    int tongtien, dongia = 0;
    int id = 0;
    int mamon = 0;
    int vitri = 1;

    ArrayList<BanAn> arrayBanAn;
    ArrayList<String> names = new ArrayList<String>();

    String urlUpdate = " http://192.168.1.4/food-menu-vhnhan/json/datmon/update.php";
    String urlgetData_BanAn = "http://192.168.1.4/food-menu-vhnhan/json/banan/getdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_chon_mon);

        spinner_banAn = (Spinner) findViewById(R.id.spinner);
        AnhXa();

        Intent intent = getIntent();
        MonAnGioHang monAnGioHang = (MonAnGioHang) intent.getSerializableExtra("dataMonAn");


        id = monAnGioHang.getId();
        mamon = monAnGioHang.getMamon();


        txtTenMonAn.setText(monAnGioHang.getTenmon());
        // tạo 1 NumberFormat để định dạng số theo tiêu chuẩn của nước Anh
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        txtSoLuong.setText(monAnGioHang.getSoluong()+"");

        String soluong1 = txtSoLuong.getText().toString();
        soluong = Integer.valueOf(soluong1);
        // đối với số có kiểu long được định dạng theo chuẩn của nước Anh
        // thì phần ngàn của số được phân cách bằng dấu phẩy
        txtGia.setText( en.format(monAnGioHang.getDongia())+" đ");
       // Picasso.get().load(ChonMonActivity.monAn.getHinhAnh()).into(imgHinh);
        txtTongTien.setText(en.format(monAnGioHang.getThanhtien())+" đ");

        tongtien = monAnGioHang.getThanhtien();
        dongia = monAnGioHang.getDongia();

        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thực đơn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soluong == 1){
                    btnGiamSL.setBackgroundColor(Color.parseColor("#CD8383"));
                    btnGiamSL.setClickable(false);
                }
                else if (soluong > 1){
                    soluong -= 1;
                    tongtien = tongtien - dongia;
                    txtSoLuong.setText(soluong+"");
                    txtTongTien.setText(en.format(tongtien)+" đ");
                }
            }
        });

        btnTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong += 1;
                tongtien = tongtien + dongia;
                txtSoLuong.setText(soluong+"");
                txtTongTien.setText(en.format(tongtien)+" đ");

                if (soluong > 1){
                    btnGiamSL.setBackgroundColor(Color.parseColor("#C61111"));
                    btnGiamSL.setClickable(true);

                }
            }
        });


        btnChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapNhatMonAn(urlUpdate);
            }

        });
        spinner_banAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vitri = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData(urlgetData_BanAn);
    }

    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayBanAn = new ArrayList<>();

                for (int i = 0; i < response.length(); i++){
                    try {

                        JSONObject object = response.getJSONObject(i);
                        arrayBanAn.add(new BanAn(
                                object.getInt("maban"),
                                object.getString("tenban")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Đổ dữ liệu lên spinner
                for (int i = 0; i < arrayBanAn.size(); i++){
                    names.add(arrayBanAn.get(i).getTenban().toString());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SuaChonMonActivity.this, R.layout.spinner, names);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice); // The drop down view
                spinner_banAn.setAdapter(spinnerArrayAdapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SuaChonMonActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void CapNhatMonAn(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(SuaChonMonActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SuaChonMonActivity.this, XemGioHang.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(SuaChonMonActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SuaChonMonActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id+"");
                params.put("monan_id",mamon+"");
                params.put("dongia", dongia+"");
                params.put("soluong",txtSoLuong.getText().toString().trim());
                params.put("thanhtien", tongtien+"");
                params.put("maban", vitri+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa(){
        imgHinh = (ImageView) findViewById(R.id.imageviewHinhMon);
        txtTenMonAn = (TextView) findViewById(R.id.textviewTenMonAn);
        txtGia = (TextView) findViewById(R.id.textviewGia);
        txtSoLuong = (TextView) findViewById(R.id.textviewSoLuong);
        txtTongTien = (TextView) findViewById(R.id.textviewTongTien);
        btnGiamSL = (Button) findViewById(R.id.buttonGiamSoLuong);
        btnTangSL = (Button) findViewById(R.id.buttonTangSoLuong);
        btnChonMon = (Button) findViewById(R.id.buttonChonMonAn);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarChonMon);


    }
}