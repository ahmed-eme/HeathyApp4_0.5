package com.example.heathyapp4.Item;

public class ItemOfAllObject {
    private int itemId;
    private String itemName;
    private String imageUrl;
    private String type1;
    private String type2;
    private  String madInfo;
    private  String scanLeafelt;
    private  String owner;
    private int mg;
    private double Price;
    private double discount;
    private int qty;

    public ItemOfAllObject()
    {

    }

    public ItemOfAllObject(int itemId, String itemName, String imageUrl, String type1, String type2, String madInfo, String scanLeafelt, String owner, int mg, double price, double discount, int qty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.type1 = type1;
        this.type2 = type2;
        this.madInfo = madInfo;
        this.scanLeafelt = scanLeafelt;
        this.owner = owner;
        this.mg = mg;
        Price = price;
        this.discount = discount;
        this.qty = qty;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
