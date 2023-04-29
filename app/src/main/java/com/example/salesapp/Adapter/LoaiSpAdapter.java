package com.example.salesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.salesapp.Model.LoaiSp;
import com.example.salesapp.R;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView texttext;
        ImageView imghinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisanpham, null);
            viewHolder.texttext = view.findViewById(R.id.item_text);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.texttext.setText(array.get(i).getTenloaisanpham());
        Glide.with(context).load(array.get(i).getHinhanhsanpham()).into(viewHolder.imghinhanh);

        return view;
    }
}
