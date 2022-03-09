package com.example.monan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class QuanLyMonAnActivity extends AppCompatActivity {

//     String urlGetData = " http://192.168.1.3/food-menu-vhnhan/json/monan/getdata.php";
//     String urlDelete = "http://192.168.1.3/food-menu-vhnhan/json/monan/delete.php";

    String urlGetData = " http://192.168.1.4/food-menu-vhnhan/json/monan/getdata.php";
    String urlDelete = "http://192.168.1.4/food-menu-vhnhan/json/monan/delete.php";

    /*String urlGetData = "http://food-menu-vhnhan.herokuapp.com/json/monan/getdata.php";
    String urlDelete = "http://food-menu-vhnhan.herokuapp.com/json/monan/delete.php";*/
    ListView lvMonAn;
    ArrayList<MonAn> arrayMonAn;
    QuanLyMonAnAdapter adapterMonAn;
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_mon_an);


        lvMonAn = (ListView) findViewById(R.id.listviewMonAn);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);




       toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.menu_add_mon_an:
                       Intent intentQuanLy = new Intent(QuanLyMonAnActivity.this, AddMonAnActivity.class);
                       startActivity(intentQuanLy);

               }
               return false;
           }
       });

        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setTitle("Quản lý");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });

        arrayMonAn = new ArrayList<>();
        adapterMonAn = new QuanLyMonAnAdapter(QuanLyMonAnActivity.this, R.layout.dong_mon_an, arrayMonAn);
        lvMonAn.setAdapter(adapterMonAn);

        GetData(urlGetData);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_mon_an, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayMonAn.clear();

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrayMonAn.add(new MonAn(
                                object.getInt("mamon"),
                                object.getString("tenmon"),
                                object.getInt("gia"),
                                object.getString("hinhanh")

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
                    Toast.makeText(QuanLyMonAnActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        );
        requestQueue.add(jsonArrayRequest);
    }


    public void DeleteMonAn(final int maMon){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(QuanLyMonAnActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    GetData(urlGetData);
                } else{
                    Toast.makeText(QuanLyMonAnActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QuanLyMonAnActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mamon", String.valueOf(maMon));
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
                    Intent intentTrangChu = new Intent(QuanLyMonAnActivity.this, MainActivity.class);
                    startActivity(intentTrangChu);
                    intentTrangChu.putExtra("login", DangNhapActivity.account);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThucDon:
                    Intent intentThucDon = new Intent(QuanLyMonAnActivity.this, LoaiMonAnActivity.class);
                    startActivity(intentThucDon);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnDanhSach:
                    Intent intentDanhSach = new Intent(QuanLyMonAnActivity.this, XemGioHang.class);
                    startActivity(intentDanhSach);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnQuanLy:
                    Intent intentQuanLy = new Intent(QuanLyMonAnActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intentQuanLy);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThongTin:
                    Intent intentThongTin = new Intent(QuanLyMonAnActivity.this, thongtinungdung.class);
                    startActivity(intentThongTin);
                    drawerLayout.closeDrawers();
                    return true;
            }
            return false;
        }
    };





}