package com.example.pulkit.darcpleazurchocolates.Models;

/**
 * Created by Pulkit on 9/6/2017.
 */

public class User {

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    private String phoneno;


    public User(String name, String email, String phoneno) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }
}
