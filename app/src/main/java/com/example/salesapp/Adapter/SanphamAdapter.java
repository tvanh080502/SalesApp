package com.example.salesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salesapp.Model.Sanpham;
import com.example.salesapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.MyViewHolder> {
    Context context;
    List<Sanpham> array;

    public SanphamAdapter(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        holder.txtten.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        holder.txtgia.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanpham.getGiaSP())) + "đ");
        Glide.with(context).load(sanpham.getHinhanhSP()).into(holder.imghinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtgia, txtten;
        ImageView imghinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imghinhanh = itemView.findViewById(R.id.itemsp_image);
        }
    }
}

