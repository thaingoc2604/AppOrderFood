package com.example.monan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class QuanLyMonAnAdapter extends BaseAdapter {


    private QuanLyMonAnActivity context;
    private int layout;
    private List<MonAn> monAnList;

    public QuanLyMonAnAdapter(QuanLyMonAnActivity context, int layout, List<MonAn> monAnList) {
        this.context = context;
        this.layout = layout;
        this.monAnList = monAnList;
    }

    @Override
    public int getCount() {
        return monAnList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenMonAn, txtGia, txtmaLoai;
        ImageView imgHinh, imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);     holder.txtTenMonAn = (TextView) view.findViewById(R.id.textviewTenMonAnCustom);
            holder.txtGia = (TextView) view.findViewById(R.id.textviewGiaCustom);

            holder.imgHinh = (ImageView) view.findViewById(R.id.imageViewHinh);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            view.setTag(holder);
        } else{
            holder = (ViewHolder) view.getTag();

        }

        MonAn monAn = monAnList.get(i);

        holder.txtTenMonAn.setText(monAn.getTenMon());
        // tạo 1 NumberFormat để định dạng số theo tiêu chuẩn của nước Anh
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        // đối với số có kiểu long được định dạng theo chuẩn của nước Anh
        // thì phần ngàn của số được phân cách bằng dấu phẩy
        holder.txtGia.setText("Giá:" + en.format(monAn.getGia()) +"đ");
        Picasso.get().load(monAn.getHinhAnh()).into(holder.imgHinh);


        //bat su kien xóa và sửa
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMonAnActivity.class);
                intent.putExtra("dataMonAn", monAn);
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(monAn.getTenMon(), monAn.getMaMon());
            }
        });

        return view;
    }
    public void XacNhanXoa(String ten, int mamon){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa món ăn " + ten + " không!");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteMonAn(mamon);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }

}

