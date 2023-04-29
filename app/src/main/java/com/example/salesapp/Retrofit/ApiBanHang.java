package com.example.salesapp.Retrofit;

import com.example.salesapp.Model.LoaiSpModel;
import com.example.salesapp.Model.SanphamModel;
import com.example.salesapp.Model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {

    @GET("Getloaisanpham.php")
    Observable<LoaiSpModel> getLoaisp();

    @GET("Getsanpham.php")
    Observable<SanphamModel> getSanpham();
    @POST("PhanLoai.php")
    @FormUrlEncoded
    Observable<SanphamModel> getSPphone(
            @Field("page") int page,
            @Field("IDsanpham") int IDsanpham
    );
    @POST("Signup.php")
    @FormUrlEncoded
    Observable<UserModel> getSignup(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("Login.php")
    @FormUrlEncoded
    Observable<UserModel> getLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
