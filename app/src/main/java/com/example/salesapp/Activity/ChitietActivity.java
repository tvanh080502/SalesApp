package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.salesapp.Model.Sanpham;
import com.example.salesapp.R;

import java.text.DecimalFormat;

public class ChitietActivity extends AppCompatActivity {
    TextView Tensanpham, GiaSP, MotaSP;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        initView();
        ActionToolBar();
        initData();
    }

    private void initData() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("chitiet");
        Tensanpham.setText(sanpham.getTensanpham());
        MotaSP.setText(sanpham.getMotaSP());
        Glide.with(getApplicationContext()).load(sanpham.getHinhanhSP()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        GiaSP.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanpham.getGiaSP()))+"đ");

    }

    private void initView() {
        Tensanpham = findViewById(R.id.txttensp);
        GiaSP = findViewById(R.id.txtgiasp);
        MotaSP = findViewById(R.id.txtmota);
        btnthem = findViewById(R.id.btthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar1 = findViewById(R.id.toolbar1);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
