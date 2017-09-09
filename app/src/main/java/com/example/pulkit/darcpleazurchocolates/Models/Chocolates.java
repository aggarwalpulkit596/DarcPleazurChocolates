package com.example.pulkit.darcpleazurchocolates.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pulkit on 9/8/2017.
 */

@IgnoreExtraProperties
public class Chocolates implements Serializable {
    private String name;
    private String price;
    private List<String> images = new ArrayList<>();
    public Map<String, Boolean> stars = new HashMap<>();


    public Chocolates() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
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

    public Chocolates(String name, String price, List<String> images) {
        this.name = name;
        this.price = price;
        this.images = images;
    }
}