package com.example.heathyapp4.Home;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class ItemViewClass {
    public ItemViewClass(){}

    private String ImgLink;
    private String type1;
    private String name;
    private double price;
    private String id;
    private int isFav;

    public ItemViewClass(String imgLink, String type1, String name, double price , String id ,int isFav) {
        ImgLink = imgLink;
        this.type1 = type1;
        this.name = name;
        this.price = price;
        this.id = id;
        this.isFav = isFav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgLink() {
        return ImgLink;
    }

    public void setImgLink(String imgLink) {
        ImgLink = imgLink;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int isFav() {
        return isFav;
    }

    public void setFav(int fav) {
        isFav = fav;
    }
}
