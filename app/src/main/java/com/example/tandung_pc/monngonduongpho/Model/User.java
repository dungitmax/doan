package com.example.tandung_pc.monngonduongpho.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int iduser;
    private String nameuser;
    private String username;
    private String address;

    public User() {
    }

    public User(int iduser, String nameuser, String username, String address) {
        this.iduser = iduser;
        this.nameuser = nameuser;
        this.username = username;
        this.address = address;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
