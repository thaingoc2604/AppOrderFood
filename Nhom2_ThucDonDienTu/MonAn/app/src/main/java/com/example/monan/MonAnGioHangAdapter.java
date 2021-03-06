package com.example.monan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MonAnGioHangAdapter extends BaseAdapter {
    private XemGioHang context;
    private int layout;
    private List<MonAnGioHang> chonMonList;


    public MonAnGioHangAdapter(XemGioHang context, int layout, List<MonAnGioHang> chonMonList) {
        this.context = context;
        this.layout = layout;
        this.chonMonList = chonMonList;
    }

    @Override
    public int getCount() {
        return chonMonList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private class ViewHolder{
        TextView txtTenMonAn_giohang, txtTenBan,txtThanhTien, txtId;

        ImageView imgSua_giohang, imgDelete_giohang;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        MonAnGioHangAdapter.ViewHolder holder;
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenMonAn_giohang = (TextView) view.findViewById(R.id.textviewTenMonAnCustom_giohang);
            holder.txtId = (TextView) view.findViewById(R.id.textviewId_giohang);
            holder.txtTenBan = (TextView) view.findViewById(R.id.textviewTenBanCustom_giohang);
            holder.txtThanhTien = (TextView) view.findViewById(R.id.textviewThanhTien_giohang);
            holder.imgSua_giohang = (ImageView) view.findViewById(R.id.imageviewSua_giohang);
            holder.imgDelete_giohang = (ImageView) view.findViewById(R.id.imageviewDelete_giohang);

            view.setTag(holder);
        } else{
            holder = (MonAnGioHangAdapter.ViewHolder) view.getTag();
        }

        MonAnGioHang chonMon = chonMonList.get(i);


        holder.txtTenMonAn_giohang.setText("T??n m??n: " + chonMon.getTenmon());
        holder.txtId.setText("S??? H??a ????n: " + chonMon.getId());
        holder.txtTenBan.setText("B??n: " +chonMon.getMaban() +"");
        // t???o 1 NumberFormat ????? ?????nh d???ng s??? theo ti??u chu???n c???a n?????c Anh
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        // ?????i v???i s??? c?? ki???u long ???????c ?????nh d???ng theo chu???n c???a n?????c Anh
        // th?? ph???n ng??n c???a s??? ???????c ph??n c??ch b???ng d???u ph???y
        holder.txtThanhTien.setText( "Th??nh Ti???n: "+ en.format(chonMon.getThanhtien()) +"??");

        holder.imgSua_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuaChonMonActivity.class);
                intent.putExtra("dataMonAn", chonMon);
                context.startActivity(intent);
            }
        });

        holder.imgDelete_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(chonMon.getId());
            }
        });

        XemGioHang.lvdanhsachmongiohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MonAnGioHang chonMon = chonMonList.get(i);
                Intent intent = new Intent(context, ThanhToanActivity.class);
                intent.putExtra("data", chonMon);
                context.startActivity(intent);
            }
        });

        return view;
    }
    public void XacNhanXoa( int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("B???n c?? mu???n x??a ????n s??? " +id + " kh??ng!");
        dialogXoa.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                context.DeleteChonMon(id);
            }
        });
        dialogXoa.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }


}
