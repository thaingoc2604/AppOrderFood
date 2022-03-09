package com.example.monan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateMonAnActivity extends AppCompatActivity {

    EditText edtTenMonAnCapNhat, edtGiaCapNhat;
    Button btnCapNhat, btnHuy, btnChupHinh, btnChonHinh;
    ImageView imageHinh;
    Spinner spLoaiMon;
    int mamon = 0;
    int vitri = 1;
    ArrayList<LoaiMon> arrayLoaiMon;
    ArrayList<String> names = new ArrayList<String>();

    String encodeImageString;
    Bitmap bitmap;


   /* String urlGetData = " http://192.168.1.3/food-menu-vhnhan/json/loaimon/getdata.php";
    String urlUpdate = " http://192.168.1.3/food-menu-vhnhan/json/monan/update.php";*/

     String urlGetData = " http://192.168.1.4/food-menu-vhnhan/json/loaimon/getdata.php";
     String urlUpdate = " http://192.168.1.4/food-menu-vhnhan/json/monan/update.php";

/*    String urlGetData = "http://food-menu-vhnhan.herokuapp.com/json/loaimon/getdata.php";
    String urlUpdate = "http://food-menu-vhnhan.herokuapp.com/json/monan/update.php";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mon_an);

        AnhXa();
        GetData(urlGetData);

        Intent intent = getIntent();
        MonAn monAn = (MonAn) intent.getSerializableExtra("dataMonAn");

        mamon = monAn.getMaMon();
        edtTenMonAnCapNhat.setText(monAn.getTenMon());
        edtTenMonAnCapNhat.setSelection(edtTenMonAnCapNhat.getText().length());

        edtGiaCapNhat.setText(monAn.getGia()+"");
        Picasso.get().load(monAn.getHinhAnh()).into(imageHinh);



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

        spLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmon = edtTenMonAnCapNhat.getText().toString();
                String gia = edtGiaCapNhat.getText().toString();


                if (tenmon.equals("") || gia.equals("")){
                    Toast.makeText(UpdateMonAnActivity.this, "Vui lòng thêm thông tin!", Toast.LENGTH_SHORT).show();
                } else{
                    CapNhatMonAn(urlUpdate);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(UpdateMonAnActivity.this, android.R.layout.simple_spinner_item, names);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spLoaiMon.setAdapter(spinnerArrayAdapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateMonAnActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UpdateMonAnActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateMonAnActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(UpdateMonAnActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateMonAnActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mamon", String.valueOf(mamon));
                params.put("tenmon", edtTenMonAnCapNhat.getText().toString().trim());
                params.put("gia", edtGiaCapNhat.getText().toString().trim());
                params.put("hinhanh",encodeImageString);
                params.put("maloai_id", vitri+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
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

    private void AnhXa(){
        imageHinh = (ImageView) findViewById(R.id.imageviewAvatarCapNhat);
        edtTenMonAnCapNhat = (EditText) findViewById(R.id.editTextTenMonAn);
        edtGiaCapNhat = (EditText) findViewById(R.id.editTextGiaTien);
        btnCapNhat = (Button) findViewById(R.id.buttonCapNhatMonAn);
        btnHuy = (Button) findViewById(R.id.buttonThoat);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinh);
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinh);
        spLoaiMon = (Spinner) findViewById(R.id.spLoaiMonAn);

    }

}