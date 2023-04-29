package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.salesapp.Adapter.LoaiSpAdapter;
import com.example.salesapp.Adapter.SanphamAdapter;
import com.example.salesapp.Model.LoaiSp;
import com.example.salesapp.Model.LoaiSpModel;
import com.example.salesapp.Model.Sanpham;
import com.example.salesapp.Model.SanphamModel;
import com.example.salesapp.R;
import com.example.salesapp.Retrofit.ApiBanHang;
import com.example.salesapp.Retrofit.RetrofitClient;
import com.example.salesapp.Utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManhinhchinh;
    NavigationView navigationView;
    ListView listViewManhinhchinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    ApiBanHang apiBanHang;
    List<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();

        if(isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSanpham();
            getEClick();
        }
        else{
            Toast.makeText(getApplicationContext(),"Không có kết nối Internet, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void getEClick() {
        listViewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent phone = new Intent(getApplicationContext(), PhoneActivity.class);
                        phone.putExtra("IDsanpham",1);
                        startActivity(phone);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(), LaptopActivity.class);
                        laptop.putExtra("IDsanpham",2);
                        startActivity(laptop);
                        break;
                    case 3:
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                        break;
                    case 4:
                        Intent signup = new Intent(getApplicationContext(), SignupActivity.class);
                        startActivity(signup);
                        break;
                }
            }
        });
    }

    private void getSanpham() {
        apiBanHang.getSanpham()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SanphamModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // Được gọi khi đăng ký Observer thành công
                    }

                    @Override
                    public void onNext(@NonNull SanphamModel sanphamModel) {
                        // Được gọi khi nhận được dữ liệu từ API thành công
                        if(sanphamModel.isSuccess()) {
                            mangsanpham = sanphamModel.getResult();
                            sanphamAdapter = new SanphamAdapter(getApplicationContext(), mangsanpham);
                            recyclerViewManhinhchinh.setAdapter(sanphamAdapter);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Được gọi khi xảy ra lỗi trong quá trình gọi
                    }

                    @Override
                    public void onComplete() {
                        // Được gọi khi hoàn thành việc lấy dữ liệu từ API
                    }
                });
    }
    private void getLoaiSanPham() {
        apiBanHang.getLoaisp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoaiSpModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // Được gọi khi đăng ký Observer thành công
                    }

                    @Override
                    public void onNext(@NonNull LoaiSpModel loaiSpModel) {
                        // Được gọi khi nhận được dữ liệu từ API thành công
                        if(loaiSpModel.isSuccess()) {
                            mangloaisp = loaiSpModel.getResult();
                            loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
                            listViewManhinhchinh.setAdapter(loaiSpAdapter);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Được gọi khi xảy ra lỗi trong quá trình gọi
                    }

                    @Override
                    public void onComplete() {
                        // Được gọi khi hoàn thành việc lấy dữ liệu từ API
                    }
                });
    }
    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2023/03/banner/s23-800-200-800x200-3.png");
        mangquangcao.add("https://cdn.tgdd.vn/2023/04/banner/ip11-800-200-800x200.png");
        mangquangcao.add("https://cdn.tgdd.vn/2023/04/banner/mac-800-200-800x200.png");
        mangquangcao.add("https://cdn.tgdd.vn/2023/03/banner/Laptop-Gaming-800-200-800x200.png");

        for(int i = 0; i < mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void Anhxa() {
        toolbar = findViewById(R.id.toobarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewLipper);
        recyclerViewManhinhchinh = findViewById(R.id.recyclerviewmanhinhchinh);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManhinhchinh.setLayoutManager(layoutManager);
        recyclerViewManhinhchinh.setHasFixedSize(true);
        listViewManhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        //khoi tao list
        mangloaisp = new ArrayList<>();
        mangsanpham = new ArrayList<>();
    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
            // Đã kết nối internet
            return true;
        } else {
            // Không có kết nối internet
            return false;
        }
    }
}