package com.example.pulkit.darcpleazurchocolates.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pulkit on 9/8/2017.
 */
@Entity(tableName = "chocolates")
@IgnoreExtraProperties
public class Chocolates implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int price;
    private String stock;
    private String description;
    private boolean message;
    private String sms;
    private ArrayList<String> images = new ArrayList<>();

    @Ignore
    public Chocolates() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Chocolates(String name, int price, String stock, String description, boolean message, ArrayList<String> images) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.message = message;
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}