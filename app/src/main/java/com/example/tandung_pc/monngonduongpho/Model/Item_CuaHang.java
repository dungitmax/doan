package com.example.tandung_pc.monngonduongpho.Model;

import java.io.Serializable;

public class Item_CuaHang implements Serializable {
    private int idcuahang;
    private String tencuahang;
    private String sdt;
    private String diadiem;
    private String latlng;
    private String linkimg;
    private int user_id;

    public Item_CuaHang() {
    }

    public Item_CuaHang(int idcuahang, String tencuahang, String sdt, String diadiem, String latlng, String linkimg, int user_id) {
        this.idcuahang = idcuahang;
        this.tencuahang = tencuahang;
        this.sdt = sdt;
        this.diadiem = diadiem;
        this.latlng = latlng;
        this.linkimg = linkimg;
        this.user_id = user_id;
    }

    public int getIdcuahang() {
        return idcuahang;
    }

    public void setIdcuahang(int idcuahang) {
        this.idcuahang = idcuahang;
    }

    public String getTencuahang() {
        return tencuahang;
    }

    public void setTencuahang(String tencuahang) {
        this.tencuahang = tencuahang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getLinkimg() {
        return linkimg;
    }

    public void setLinkimg(String linkimg) {
        this.linkimg = linkimg;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

