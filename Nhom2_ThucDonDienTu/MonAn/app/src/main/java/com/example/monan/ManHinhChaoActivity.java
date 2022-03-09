package com.example.monan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (Exception e){

                }finally {
                    Intent iTrangChu = new Intent(ManHinhChaoActivity.this, DangNhapActivity.class);
                    startActivity(iTrangChu);
                }
            }
        });

        thread.start();

    }
}