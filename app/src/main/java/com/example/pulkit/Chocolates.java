package com.example.pulkit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 9/8/2017.
 */

public class Chocolates implements Serializable {
    private String name;
    private String price;
    private List<String> images = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;



    public Chocolates(){
        //Needed for firebase
    }

    public Chocolates(String name, String price, ArrayList<String> images, String description) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.description = description;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
