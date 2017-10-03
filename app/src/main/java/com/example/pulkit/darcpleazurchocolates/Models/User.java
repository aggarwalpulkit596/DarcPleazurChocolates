package com.example.pulkit.darcpleazurchocolates.Models;


/**
 * Created by Pulkit on 9/6/2017.
 */

public class User {

    private String name;
    private String email;
    private String phoneno;
    private Address address;


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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }


    public User(String name, String email, String phoneno) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }
    public User(String name, String email, String phoneno,Address address) {
        this.address = address;
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }


}

