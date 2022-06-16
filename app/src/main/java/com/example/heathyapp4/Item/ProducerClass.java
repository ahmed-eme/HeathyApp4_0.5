package com.example.heathyapp4.Item;

public class ProducerClass {
    // variables for storing our image and name.
    private String name;

    public ProducerClass(String name, String imgUrl, int price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    private String imgUrl;
    private int price;

    public ProducerClass() {
        // empty constructor required for firebase.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

