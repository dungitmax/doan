package com.example.tandung_pc.monngonduongpho.Model;

import java.io.Serializable;

/**
 * Created by TANDUNG-PC on 2/24/2018.
 */

public class Food_1 implements Serializable {
    private int foodId;
    private String nameFood;
    private byte[] image;
    private String address;
    private String mota;
    private String price;


    public Food_1(int foodId, String nameFood, byte[] image) {
        this.foodId = foodId;
        this.nameFood = nameFood;
        this.image = image;
    }

    public Food_1(int foodId, String nameFood, String price, String address, String mota, byte[] image) {
        this.foodId = foodId;
        this.nameFood = nameFood;
        this.image = image;
        this.address = address;
        this.mota = mota;
        this.price = price;
    }

    public Food_1() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
