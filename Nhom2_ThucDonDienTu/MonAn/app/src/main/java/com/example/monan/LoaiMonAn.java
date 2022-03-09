package com.example.monan;

import java.io.Serializable;

public class LoaiMonAn implements Serializable {
    private int maloai;
    private String tenloai;
    private String hinhanh;

    public LoaiMonAn(int maloai, String tenloai, String hinhanh) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
