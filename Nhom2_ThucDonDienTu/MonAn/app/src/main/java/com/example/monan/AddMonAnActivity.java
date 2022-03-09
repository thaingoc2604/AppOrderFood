package com.example.monan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMonAnActivity extends AppCompatActivity {

    public static int REQUEST_CODE_CAMERA = 456;
    public static int REQUEST_CODE_FOLDER = 789;

    EditText edtTenMonAn, edtGia;
    ImageView imageHinh;
    Button btnThem, btnThoat, btnChonHinh, btnChupHinh;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Spinner spLoaiMon;
    LoaiMon loaiMon;
    int vitri = 1;

    String encodeImageString;

    ArrayList<LoaiMon> arrayLoaiMon;
    ArrayList<String> names = new ArrayList<String>();



   /* String urlGetData = " http://192.168.1.3/food-menu-vhnhan/json/loaimon/getdata.php";
    String urlInsert = " http://192.168.1.3/food-menu-vhnhan/json/monan/insert.php";*/

    String urlGetData = " http://192.168.1.4/food-menu-vhnhan/json/loaimon/getdata.php";
    String urlInsert = " http://192.168.1.4/food-menu-vhnhan/json/monan/insert.php";


  /*  String urlGetData = "http://food-menu-vhnhan.herokuapp.com/json/loaimon/getdata.php";
    String urlInsert = "http://food-menu-vhnhan.herokuapp.com/json/monan/insert.php";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_an);

        AnhXa();
        GetData(urlGetData);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm món ăn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmonan = edtTenMonAn.getText().toString();
                String gia = edtGia.getText().toString();

                if (tenmonan.equals("") || gia.equals("")){
                    Toast.makeText(AddMonAnActivity.this, "Vui lòng thêm thông tin!", Toast.LENGTH_SHORT).show();
                } else{
                    ThemMonAn(urlInsert);
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Chup hình
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,AddMonAnActivity.REQUEST_CODE_CAMERA);
            }
        });

        //Chọn Hình
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, AddMonAnActivity.REQUEST_CODE_FOLDER);
            }
        });

    }

    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                arrayLoaiMon = new ArrayList<>();

                for (int i = 0; i < response.length(); i++){
                    try {

                        JSONObject object = response.getJSONObject(i);
                        arrayLoaiMon.add(new LoaiMon(
                                object.getInt("maloai"),
                                object.getString("tenloai"),
                                object.getString("hinhanh")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Đổ dữ liệu lên spinner
                for (int i = 0; i < arrayLoaiMon.size(); i++){
                    names.add(arrayLoaiMon.get(i).getTenLoai().toString());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddMonAnActivity.this, android.R.layout.simple_spinner_item, names);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spLoaiMon.setAdapter(spinnerArrayAdapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddMonAnActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void ThemMonAn(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(AddMonAnActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddMonAnActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(AddMonAnActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tenmon", edtTenMonAn.getText().toString().trim());
                params.put("gia", edtGia.getText().toString().trim());
                params.put("hinhanh",encodeImageString);
                params.put("maloai", vitri+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AddMonAnActivity.REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageHinh.setImageBitmap(bitmap);
            encodeBitmapImage(bitmap);
        }
        if (requestCode == AddMonAnActivity.REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageHinh.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void AnhXa(){
        imageHinh = (ImageView) findViewById(R.id.imageviewAvatarThem);
        spLoaiMon = (Spinner) findViewById(R.id.spLoaiMonAn);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarThemMon);
        edtTenMonAn = (EditText) findViewById(R.id.editTextTenMonAn);
        edtGia = (EditText) findViewById(R.id.editTextGiaTien);
        btnThem = (Button) findViewById(R.id.buttonThem);
        btnThoat = (Button) findViewById(R.id.buttonThoat);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinh);
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinh);

    }
}