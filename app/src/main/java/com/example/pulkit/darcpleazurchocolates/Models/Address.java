package com.example.pulkit.darcpleazurchocolates.Models;

/**
 * Created by Pulkit on 10/3/2017.
 */
public class Address {


    private String AddressLine1;
    private String City;
    private String FullName;
    private String Landmark;
    private String Mobile;
    private String State;
    private String Pincode;

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public Address(){

    }

    public Address(String addressLine1, String city, String fullName, String landmark, String mobile, String state, String pincode) {
        AddressLine1 = addressLine1;
        City = city;
        FullName = fullName;
        Landmark = landmark;
        Mobile = mobile;
        State = state;
        this.Pincode = pincode;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        this.Pincode = pincode;
    }
}
