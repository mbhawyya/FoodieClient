package com.bhawyyamittal.foodieclient;

/**
 * Created by BHAWYYA MITTAL on 25-11-2017.
 */

public class FoodItem {
    private String name,price,description,image;
    FoodItem(){

    }
    public FoodItem(String name, String price, String description, String image){
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
