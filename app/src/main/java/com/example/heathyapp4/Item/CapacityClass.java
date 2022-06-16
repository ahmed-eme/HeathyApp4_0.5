package com.example.heathyapp4.Item;


public class CapacityClass extends ItemClass {

    private int mg;
    private double Price;
    private double discount;
    private int qty;

    public CapacityClass()
    {

    }

    public CapacityClass(int mg, double price, double discount, int qty) {
        this.mg = mg;
        Price = price;
        this.discount = discount;
        this.qty = qty;
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
