package com.example.lab18_karaoke_project;

public class Item {
    private String maso;
    private String tieude;
    private int thich; // 0 hoặc 1 (YEUTHICH)

    public Item(String maso, String tieude, int thich) {
        this.maso = maso;
        this.tieude = tieude;
        this.thich = thich;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public int getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }

    @Override
    public String toString() {
        return "Item{" +
                "maso='" + maso + '\'' +
                ", tieude='" + tieude + '\'' +
                ", thich=" + thich +
                '}';
    }
}
