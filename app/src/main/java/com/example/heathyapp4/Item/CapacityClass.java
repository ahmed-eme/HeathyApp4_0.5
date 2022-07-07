package com.example.heathyapp4.Item;


public class CapacityClass {

    private int mg;
    private double Price;
    private double discount;
    private int quantity;
    private String endDeal;

    public CapacityClass()
    {

    }

    public CapacityClass(int mg, double price, double discount, int quantity, String endDeal) {
        this.mg = mg;
        Price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.endDeal = endDeal;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEndDeal() {
        return endDeal;
    }

    public void setEndDeal(String endDeal) {
        this.endDeal = endDeal;
    }
}
