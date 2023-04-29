package com.example.salesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salesapp.Model.Sanpham;
import com.example.salesapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class LaptopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Sanpham> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public LaptopAdapter(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Sanpham sanpham = array.get(position);
            myViewHolder.Tensanpham.setText(sanpham.getTensanpham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.GiaSP.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanpham.getGiaSP()))+"đ");
            myViewHolder.MotaSP.setText(sanpham.getMotaSP());
            myViewHolder.IDsanpham.setText(sanpham.getID() + "");
            Glide.with(context).load(sanpham.getHinhanhSP()).into(myViewHolder.hinhanh);
        }
        else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Tensanpham, GiaSP, MotaSP, IDsanpham;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tensanpham = itemView.findViewById(R.id.itemlt_ten);
            GiaSP = itemView.findViewById(R.id.itemlt_gia);
            MotaSP = itemView.findViewById(R.id.itemlt_mota);
            hinhanh = itemView.findViewById(R.id.itemlt_image);
            IDsanpham = itemView.findViewById(R.id.itemlt_id);
        }
    }

}
