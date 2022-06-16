package com.example.heathyapp4.Item;

public class ItemClass {
    private int itemId;
    private String itemName;
    private String imageUrl;
    private String type1;
    private String type2;
    private  String madInfo;
    private  String scanLeafelt;
    private  String owner;

    public ItemClass()
    {

    }

    public ItemClass(int itemId, String itemName, String imageUrl, String type1, String type2, String madInfo, String scanLeafelt , String owner ) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.type1 = type1;
        this.type2 = type2;
        this.madInfo = madInfo;
        this.scanLeafelt = scanLeafelt;
        this.owner = owner;
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


}

