package com.example.monan;

import java.io.Serializable;
import java.util.Date;

public class NguoiDung implements Serializable {

    private int id;
    private String tendangnhap;
    private String matkhau;
    private String hoten;
    private Boolean gioitinh;
    private Date ngaysinh;
    private int loaiquyen;


    public NguoiDung(int id, String tendangnhap, String matkhau, String hoten) {
        this.id = id;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.hoten = hoten;
    }

    public NguoiDung(int id, String tendangnhap, String matkhau, String hoten, Boolean gioitinh, Date ngaysinh, int loaiquyen) {
        this.id = id;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.loaiquyen = loaiquyen;
    }


    public NguoiDung(String tendangnhap, String matkhau) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public NguoiDung() {

    }

    public Boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(Boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public int getLoaiquyen() {
        return loaiquyen;
    }

    public void setLoaiquyen(int loaiquyen) {
        this.loaiquyen = loaiquyen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
