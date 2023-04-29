package com.example.salesapp.Model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    int ID;
    String Tensanpham;
    String GiaSP;
    String HinhanhSP;
    String MotaSP;
    int IDsanpham;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public String getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(String giaSP) {
        GiaSP = giaSP;
    }

    public String getHinhanhSP() {
        return HinhanhSP;
    }

    public void setHinhanhSP(String hinhanhSP) {
        HinhanhSP = hinhanhSP;
    }

    public String getMotaSP() {
        return MotaSP;
    }

    public void setMotaSP(String motaSP) {
        MotaSP = motaSP;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }
}

