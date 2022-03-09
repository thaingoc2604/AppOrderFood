package com.example.monan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnActivity extends AppCompatActivity {
    GridView gvDanhSachLoaiMonAn;
    ArrayList<LoaiMonAn> loaiMonAnArrayList;
    LoaiMonAnAdapter loaiMonAnAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private List<LoaiMon> loaiMonList;



    //String url = "http://food-menu-vhnhan.herokuapp.com/json/loaimon/getdata.php";
    //String url = "http://192.168.1.3/food-menu-vhnhan/json/loaimon/getdata.php";
    String url = "http://192.168.1.4/food-menu-vhnhan/json/loaimon/getdata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_mon_an);

        // custom listview
        gvDanhSachLoaiMonAn  = (GridView) findViewById(R.id.gridViewLoaiMonAn);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setTitle("Thực đơn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });

        loaiMonAnArrayList = new ArrayList<>();
        loaiMonAnAdapter = new LoaiMonAnAdapter(LoaiMonAnActivity.this,R.layout.dong_loai_mon_an,loaiMonAnArrayList);
        gvDanhSachLoaiMonAn.setAdapter(loaiMonAnAdapter);
        // kết thức custom listview

        GetDataLoaiMon(url);


    }

    private void GetDataLoaiMon(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>(){
                @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object =  response.getJSONObject(i);
                                loaiMonAnArrayList.add(new LoaiMonAn(
                                        // phải giống với bên sinvien
                                       object.getInt("maloai"),
                                       object.getString("tenloai"),
                                        object.getString("hinhanh")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loaiMonAnAdapter.notifyDataSetChanged();
                    }

                    },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoaiMonAnActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                );
                requestQueue.add(jsonArrayRequest);
        }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnTrangChu:
                    Intent intentTrangChu = new Intent(LoaiMonAnActivity.this, MainActivity.class);
                    startActivity(intentTrangChu);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThucDon:
                    Intent intentThucDon = new Intent(LoaiMonAnActivity.this, LoaiMonAnActivity.class);
                    startActivity(intentThucDon);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnDanhSach:
                    Intent intentDanhSach = new Intent(LoaiMonAnActivity.this, XemGioHang.class);
                    startActivity(intentDanhSach);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnQuanLy:
                    if (DangNhapActivity.account.getLoaiquyen() == 0) {
                        Intent intentQuanLy = new Intent(LoaiMonAnActivity.this, QuanLyMonAnActivity.class);
                        startActivity(intentQuanLy);
                        drawerLayout.closeDrawers();
                    } else
                        Toast.makeText(LoaiMonAnActivity.this, "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.btnThongTin:
                    Intent intentThongTin = new Intent(LoaiMonAnActivity.this, thongtinungdung.class);
                    startActivity(intentThongTin);
                    drawerLayout.closeDrawers();
                    return true;
            }
            return false;
        }
    };
}