package com.example.salesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salesapp.Model.UserModel;
import com.example.salesapp.R;
import com.example.salesapp.Retrofit.ApiBanHang;
import com.example.salesapp.Retrofit.RetrofitClient;
import com.example.salesapp.Utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {
    EditText email, password, confirm_password;
    AppCompatButton button;
    ApiBanHang apiBanHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initCotroll();
    }

    private void initCotroll() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
    }

    private void Signup() {
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_confirm_password = confirm_password.getText().toString().trim();
        if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email",Toast.LENGTH_SHORT ).show();
        }
        else if(TextUtils.isEmpty(str_password)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT ).show();
        }
        else if(TextUtils.isEmpty(str_confirm_password)){
            Toast.makeText(getApplicationContext(), "Bạn chưa xác nhận lại mật khẩu",Toast.LENGTH_SHORT ).show();
        }
        else if(!str_password.equals(str_confirm_password)){
            Toast.makeText(getApplicationContext(), "Mật khẩu và xác nhận mật khẩu không giống nhau",Toast.LENGTH_SHORT ).show();
        }
        else{
            apiBanHang.getSignup(str_email,str_password)
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
                            if(userModel.isSuccess()){
                                Utils.use_curent.setEmail(str_email);
                                Utils.use_curent.setPassword(str_password);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), userModel.getMessage(),Toast.LENGTH_SHORT ).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // Được gọi khi xảy ra lỗi trong quá trình gọi
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT ).show();
                        }

                        @Override
                        public void onComplete() {
                            // Được gọi khi hoàn thành việc lấy dữ liệu từ API
                        }
                    });
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        button = findViewById(R.id.btsignup);
    }
}
