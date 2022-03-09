package com.example.monan;

import java.io.Serializable;

public class MonAnGioHang implements Serializable {

    public String tenmon;
    public int dongia;
    public int soluong;
    public int thanhtien;
    public int id;
    public int maban;
    public int mamon;


    public MonAnGioHang() {
    }

    public MonAnGioHang(String tenmon, int dongia, int soluong, int thanhtien, int id, int maban) {
        this.tenmon = tenmon;
        this.dongia = dongia;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
        this.id = id;
        this.maban = maban;
    }

    public MonAnGioHang(String tenmon, int dongia, int soluong, int thanhtien, int id, int maban, int mamon) {
        this.tenmon = tenmon;
        this.dongia = dongia;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
        this.id = id;
        this.maban = maban;
        this.mamon = mamon;
    }

    public int getMamon() {
        return mamon;
    }

    public void setMamon(int mamon) {
        this.mamon = mamon;
    }

    public int getMaban() {
        return maban;
    }

    public void setMaban(int maban) {
        this.maban = maban;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
