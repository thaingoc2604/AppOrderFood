package com.example.monan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XemGioHang extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static ListView lvdanhsachmongiohang;
    ArrayList<MonAnGioHang> arrayListMonAnGioHang;
    MonAnGioHangAdapter adapterMonAn;


   /* String urlGetData =  "http://192.168.1.3/food-menu-vhnhan/json/datmon/getdata.php";
    String urlDelete = "http://192.168.1.3/food-menu-vhnhan/json/datmon/delete.php";*/

    String urlGetData =  "http://192.168.1.11/food-menu-vhnhan/json/datmon/getdata.php";
    String urlDelete = "http://192.168.1.11/food-menu-vhnhan/json/datmon/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_gio_hang);

        lvdanhsachmongiohang = (ListView) findViewById(R.id.listviewGioHang);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        arrayListMonAnGioHang = new ArrayList<MonAnGioHang>();
        adapterMonAn = new MonAnGioHangAdapter(XemGioHang.this, R.layout.dong_mon_gio_hang, arrayListMonAnGioHang);
        lvdanhsachmongiohang.setAdapter(adapterMonAn);

        GetData(urlGetData);

        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setTitle("Quản lý");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });

    }
    private void GetData(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayListMonAnGioHang.clear();
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrayListMonAnGioHang.add(new MonAnGioHang(
                                object.getString("tenmon"),
                                object.getInt("dongia"),
                                object.getInt("soluong"),
                                object.getInt("thanhtien"),
                                object.getInt("id"),
                                object.getInt("maban"),
                                object.getInt("monan_id")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterMonAn.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XemGioHang.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void DeleteChonMon(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(XemGioHang.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    GetData(urlGetData);
                } else{
                    Toast.makeText(XemGioHang.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XemGioHang.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnTrangChu:
                    Intent intentTrangChu = new Intent(XemGioHang.this, MainActivity.class);
                    startActivity(intentTrangChu);
                    intentTrangChu.putExtra("login", DangNhapActivity.account);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThucDon:
                    Intent intentThucDon = new Intent(XemGioHang.this, LoaiMonAnActivity.class);
                    startActivity(intentThucDon);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnDanhSach:
                    Intent intentDanhSach = new Intent(XemGioHang.this, XemGioHang.class);
                    startActivity(intentDanhSach);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnQuanLy:
                    Intent intentQuanLy = new Intent(XemGioHang.this, QuanLyMonAnActivity.class);
                    startActivity(intentQuanLy);
                    drawerLayout.closeDrawers();
                    return true;

            }
            return false;
        }
    };

}