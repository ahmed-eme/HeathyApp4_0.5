package com.example.heathyapp4.Cart;

public class CartClass {
    public CartClass(){}

    private String ImgLink;
    private String type1;
    private String name;
    private double price;
    private double discount;
    private String userId;
    private String itemId;
    private int mg;
    private String endDeal;
    private int quantity;

    public CartClass(String imgLink, String type1, String name, double price, double discount, String userId, String itemId, int mg, String endDeal, int quantity) {
        ImgLink = imgLink;
        this.type1 = type1;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.userId = userId;
        this.itemId = itemId;
        this.mg = mg;
        this.endDeal = endDeal;
        this.quantity = quantity;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public String getEndDeal() {
        return endDeal;
    }

    public void setEndDeal(String endDeal) {
        this.endDeal = endDeal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
