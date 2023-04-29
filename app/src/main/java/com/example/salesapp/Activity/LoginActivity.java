package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesapp.Model.UserModel;
import com.example.salesapp.R;
import com.example.salesapp.Retrofit.ApiBanHang;
import com.example.salesapp.Retrofit.RetrofitClient;
import com.example.salesapp.Utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView txtlogin;
    EditText email, password;
    AppCompatButton btlogin;
    ApiBanHang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initControll();
    }
    private void initControll() {
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                String str_password = password.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email",Toast.LENGTH_SHORT ).show();
                }
                else if(TextUtils.isEmpty(str_password)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT ).show();
                }
                else {
                    //
                    Paper.book().write("email", str_email);
                    Paper.book().write("password", str_password);

                    apiBanHang.getLogin(str_email,str_password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UserModel>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    // Được gọi khi đăng ký Observer thành công
                                }

                                @Override
                                public void onNext(@NonNull UserModel userModel) {
                                    // Được gọi khi nhận được dữ liệu từ API thành công
                                    if (userModel.isSuccess()){
                                        Utils.use_curent = userModel.getResult().get(0);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
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
            }
        });
    }
    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        txtlogin = findViewById(R.id.txtlogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btlogin = findViewById(R.id.btlogin);
        //Đọc dữ liệu user
        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
            email.setText(Paper.book().read("email"));
            password.setText(Paper.book().read("password"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.use_curent.getEmail() != null && Utils.use_curent.getPassword() != null){
            email.setText(Utils.use_curent.getEmail());
            password.setText(Utils.use_curent.getPassword());
        }
    }
}
