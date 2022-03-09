package com.example.monan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {

   /* String urlGetData =  "http://192.168.1.3/food-menu-vhnhan/json/nguoidung/dangnhap.php";*/

    String urlGetData =  "http://192.168.1.4/food-menu-vhnhan/json/nguoidung/dangnhap.php";

    Button btnDongYDN;
    EditText edit_TenDangNhapDN,edit_MatKhauDN;
    String tendangnhap = "";
    String matkhau = "";
    ArrayList<NguoiDung> arrayNguoiDung;
    ArrayAdapter adapterNguoiDung;
    public static NguoiDung account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnDongYDN = (Button) findViewById(R.id.btnDongYDN);
        edit_TenDangNhapDN = (EditText) findViewById(R.id.edit_TenDangNhapDN);
        edit_MatKhauDN = (EditText) findViewById(R.id.edit_MatKhauDN);

        //AnhXa();

        btnDongYDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 tendangnhap = edit_TenDangNhapDN.getText().toString();
                 matkhau = edit_MatKhauDN.getText().toString();

                if (tendangnhap.equals("") || matkhau.equals("")){
                    Toast.makeText(DangNhapActivity.this, "Vui lòng thêm thông tin!", Toast.LENGTH_SHORT).show();
                } else{
                    GetData(tendangnhap, matkhau);
                }
            }
        });


    }
    private void GetData(String tendangnhap, String matkhau){

            StringRequest requestLogin = new StringRequest(Request.Method.POST, urlGetData,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            String message = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                     account = new NguoiDung();
                                    account.setTendangnhap(jsonObject.getString("tendangnhap"));
                                    account.setHoten((jsonObject.getString("hoten")));
                                    account.setLoaiquyen((jsonObject.getInt("loaiquyen")));




                                    Intent intentTrangChu = new Intent(DangNhapActivity.this, MainActivity.class);

                                    startActivity(intentTrangChu);

                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(DangNhapActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DangNhapActivity.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("tendangnhap", edit_TenDangNhapDN.getText().toString().trim());
                    params.put("matkhau", edit_MatKhauDN.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(requestLogin);
        }




//    private void AnhXa() {
//        btnDongYDN = (Button) findViewById(R.id.btnDongYDN);
//        edit_TenDangNhapDN = (EditText) findViewById(R.id.edit_TenDangNhapDN);
//        edit_MatKhauDN = (EditText) findViewById(R.id.edit_MatKhauDN);
//
//    }
}