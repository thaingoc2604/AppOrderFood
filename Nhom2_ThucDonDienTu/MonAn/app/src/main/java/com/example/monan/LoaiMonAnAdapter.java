package com.example.monan;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaiMonAnAdapter extends BaseAdapter {
    private LoaiMonAnActivity context;
    private int layout;
    private List<LoaiMonAn> loaiMonAnList;

    public LoaiMonAnAdapter(LoaiMonAnActivity context, int layout, List<LoaiMonAn> loaiMonAnList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonAnList = loaiMonAnList;
    }

    @Override
    public int getCount() {
        return loaiMonAnList.size();
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
        ImageView imgLoaiMonAn;
        TextView editText_TenLoaiMonAn;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.imgLoaiMonAn = (ImageView) view.findViewById(R.id.imageView_loaimonan);
            holder.editText_TenLoaiMonAn = (TextView) view.findViewById(R.id.editText_tenloaimonan);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        LoaiMonAn loaiMonAn = loaiMonAnList.get(i);
        holder.editText_TenLoaiMonAn.setText(loaiMonAn.getTenloai());
        Picasso.get().load(loaiMonAn.getHinhanh()).into(holder.imgLoaiMonAn);

        //bat su kien xóa và sửa
        holder.imgLoaiMonAn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MonAnActivity.class);
                intent.putExtra("data", loaiMonAn);

                context.startActivity(intent);
            }
        });

        return view;


    }
}
