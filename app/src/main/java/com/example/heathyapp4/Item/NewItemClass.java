package com.example.heathyapp4.Item;

public class NewItemClass {
    int id;
    String type1;
    String type2;
    String imageUrl;
    String itemName;
    double itemPrice;
    double itemDiscount;
    String madInfo;
    String scanLeafelt;

    public NewItemClass()
    {

    }

    public NewItemClass(int id, String type1, String type2, String imageUrl, String itemName, double itemPrice, double itemDiscount, String madInfo, String scanLeafelt) {
        this.id = id;
        this.type1 = type1;
        this.type2 = type2;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
        this.madInfo = madInfo;
        this.scanLeafelt = scanLeafelt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getMadInfo() {
        return madInfo;
    }

    public void setMadInfo(String madInfo) {
        this.madInfo = madInfo;
    }

    public String getScanLeafelt() {
        return scanLeafelt;
    }

    public void setScanLeafelt(String scanLeafelt) {
        this.scanLeafelt = scanLeafelt;
    }
}



