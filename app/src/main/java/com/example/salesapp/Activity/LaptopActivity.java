package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.example.salesapp.Adapter.LaptopAdapter;
import com.example.salesapp.Model.Sanpham;
import com.example.salesapp.Model.SanphamModel;
import com.example.salesapp.R;
import com.example.salesapp.Retrofit.ApiBanHang;
import com.example.salesapp.Retrofit.RetrofitClient;
import com.example.salesapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    int page = 1;
    int IDsanpham;
    LaptopAdapter laptopAdapter;
    List<Sanpham> sanphamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        IDsanpham = getIntent().getIntExtra("IDsanpham",1);
        Anhxa();
        ActionToolBar();
        getSanPhamphone(page);
        addEventLoading();
    }

    private void addEventLoading() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanphamList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                sanphamList.add(null);
                laptopAdapter.notifyItemInserted(sanphamList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanphamList.remove(sanphamList.size()-1);
                laptopAdapter.notifyItemRemoved(sanphamList.size());
                page = page+1;
                getSanPhamphone(page);
                laptopAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getSanPhamphone(int page) {
        apiBanHang.getSPphone(page, IDsanpham)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SanphamModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SanphamModel sanphamModel) {
                        if(sanphamModel.isSuccess()){
                            if (laptopAdapter == null){
                                sanphamList = sanphamModel.getResult();
                                laptopAdapter = new LaptopAdapter(getApplicationContext(),sanphamList);
                                recyclerView.setAdapter(laptopAdapter);
                            }
                            else {
                                int vitri = sanphamList.size()-1;
                                int soluongadd = sanphamModel.getResult().size();
                                for (int i = 0; i < soluongadd; i++){
                                    sanphamList.add(sanphamModel.getResult().get(i));
                                }
                                laptopAdapter.notifyItemRangeInserted(vitri, soluongadd);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarlt);
        recyclerView = findViewById(R.id.recyclerview_laptop);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanphamList = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),sanphamList);
        recyclerView.setAdapter(laptopAdapter);
    }
}
