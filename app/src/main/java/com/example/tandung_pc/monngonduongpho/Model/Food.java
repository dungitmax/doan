package com.example.tandung_pc.monngonduongpho.Model;

import java.io.Serializable;

/**
 * Created by TANDUNG-PC on 2/10/2018.
 */

public class Food implements Serializable {
    private int foodId;

    private String nameFood;

    private String address;

    private String image;

    private String description;

    private String price;
    private int user_id;
    private int typefoodId;

    public Food() {
    }

    public Food(int foodId, String nameFood, String address, String image, String description, String price, int typefoodId) {
        this.foodId = foodId;
        this.nameFood = nameFood;
        this.address = address;
        this.image = image;
        this.description = description;
        this.price = price;
        this.typefoodId = typefoodId;
    }

    public Food(int foodId, String nameFood, String image) {
        this.foodId = foodId;
        this.nameFood = nameFood;
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTypefoodId() {
        return typefoodId;
    }

    public void setTypefoodId(int typefoodId) {
        this.typefoodId = typefoodId;
    }
}
