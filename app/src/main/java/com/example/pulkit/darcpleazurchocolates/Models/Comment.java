package com.example.pulkit.darcpleazurchocolates.Models;

/**
 * Created by Pulkit on 9/11/2017.
 */

public class Comment {

    public String uid;
    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}
