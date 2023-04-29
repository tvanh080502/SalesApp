package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.salesapp.Adapter.PhoneAdapter;
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

public class PhoneActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    int page = 1;
    int IDsanpham;
    PhoneAdapter phoneAdapter;
    List<Sanpham> sanphamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
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
                phoneAdapter.notifyItemInserted(sanphamList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanphamList.remove(sanphamList.size()-1);
                phoneAdapter.notifyItemRemoved(sanphamList.size());
                page = page+1;
                getSanPhamphone(page);
                phoneAdapter.notifyDataSetChanged();
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
                            if (phoneAdapter == null){
                                sanphamList = sanphamModel.getResult();
                                phoneAdapter = new PhoneAdapter(getApplicationContext(),sanphamList);
                                recyclerView.setAdapter(phoneAdapter);
                            }
                            else {
                                int vitri = sanphamList.size()-1;
                                int soluongadd = sanphamModel.getResult().size();
                                for (int i = 0; i < soluongadd; i++){
                                    sanphamList.add(sanphamModel.getResult().get(i));
                                }
                                phoneAdapter.notifyItemRangeInserted(vitri, soluongadd);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_phone);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanphamList = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(getApplicationContext(),sanphamList);
        recyclerView.setAdapter(phoneAdapter);
    }
}
