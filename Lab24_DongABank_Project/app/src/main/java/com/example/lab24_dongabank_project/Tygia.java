package com.example.lab24_dongabank_project;

import android.graphics.Bitmap;

public class TyGia {

    private String type;
    private String imageurl;
    private Bitmap bitmap;
    private String muatienmat;
    private String muack;
    private String bantienmat;
    private String banck;
    private String bank;

    // Constructor mặc định
    public TyGia() {
        this.type = "";
        this.imageurl = "";
        this.bitmap = null;
        this.muatienmat = "";
        this.muack = "";
        this.bantienmat = "";
        this.banck = "";
        this.bank = "";
    }

    // Constructor đầy đủ
    public TyGia(String type, String imageurl, Bitmap bitmap, String muatienmat,
                 String muack, String bantienmat, String banck, String bank) {
        this.type = type;
        this.imageurl = imageurl;
        this.bitmap = bitmap;
        this.muatienmat = muatienmat;
        this.muack = muack;
        this.bantienmat = bantienmat;
        this.banck = banck;
        this.bank = bank;
    }

    // Getter & Setter
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImageurl() { return imageurl; }
    public void setImageurl(String imageurl) { this.imageurl = imageurl; }

    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public String getMuatienmat() { return muatienmat; }
    public void setMuatienmat(String muatienmat) { this.muatienmat = muatienmat; }

    public String getMuack() { return muack; }
    public void setMuack(String muack) { this.muack = muack; }

    public String getBantienmat() { return bantienmat; }
    public void setBantienmat(String bantienmat) { this.bantienmat = bantienmat; }

    public String getBanck() { return banck; }
    public void setBanck(String banck) { this.banck = banck; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }
}
