package com.example.monan;

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

public class MonAnActivity extends AppCompatActivity {


    ListView lvMonAn;
    ArrayList<MonAn> arrayMonAn;
    MonAnAdapter adapterMonAn;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    int maloai = 0;
    LoaiMonAn loaiMonAn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an);


        //Nhận intent Loại món
        Intent intent = getIntent();
        loaiMonAn = (LoaiMonAn) intent.getSerializableExtra("data");

        maloai = loaiMonAn.getMaloai();

        //Anh Xa
        lvMonAn = (ListView) findViewById(R.id.listviewMonAn);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Hiển thị toolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thực đơn");
        //Click trở về trang trước
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Adapter Hiển thị listview Món ăn
        arrayMonAn = new ArrayList<>();
        adapterMonAn = new MonAnAdapter(MonAnActivity.this, R.layout.dong_mon_an_hien_thi, arrayMonAn);
        lvMonAn.setAdapter(adapterMonAn);

        //Get dữ liệu từ Server
        GetData(maloai);


    }

    //Get dữ liệu món ăn theo loại món
    private void GetData(int maloai){
//        String urlGetData =  "http://192.168.1.3/food-menu-vhnhan/json/monan/getdata_id.php?maloai="+ maloai;
        String urlGetData =  "http://192.168.1.4/food-menu-vhnhan/json/monan/getdata_id.php?maloai="+ maloai;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetData, null, new Response.Listener<JSONArray>() {
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
                    Toast.makeText(MonAnActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Nullable
            @Override
            //Gửi dữ liệu mã loại lên Server
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maloai",maloai+"");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }


    //Navigation View
    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnTrangChu:
                    Intent intentTrangChu = new Intent(MonAnActivity.this, MainActivity.class);
                    startActivity(intentTrangChu);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThucDon:
                    Intent intentThucDon = new Intent(MonAnActivity.this, LoaiMonAnActivity.class);
                    startActivity(intentThucDon);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnDanhSach:
                    Intent intentDanhSach = new Intent(MonAnActivity.this, XemGioHang.class);
                    startActivity(intentDanhSach);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnQuanLy:
                    Intent intentQuanLy = new Intent(MonAnActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intentQuanLy);
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.btnThongTin:
                    Intent intentThongTin = new Intent(MonAnActivity.this, thongtinungdung.class);
                    startActivity(intentThongTin);
                    drawerLayout.closeDrawers();
                    return true;

            }
            return false;
        }
    };


}