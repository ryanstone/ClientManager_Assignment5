package com.stoneryan.android.clientmanager;

import database.ClientDbSchema;

/**
 * Created by Ryan on 4/21/2016.
 */
public class Customer {
    private Long mId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mEmail;
    private String mPicture_Address;
    private String mCard_Num;
    private String mCard_Exp;
    private String mCard_CVV;
    private String mCard_Zip;

    @Override
    public String toString() {
        return mName + ", " + mId;
    }

    public Customer() {
    }

    public Customer(Long _id) {
        setId(_id);
    }

    public String printCustomer() {
        String mCustomer;
        mCustomer = "Name: " + mName + "\n" +
                    "Address: " + mAddress + "\n" +
                    "Phone: " + mPhone + "\n" +
                    "Email: " + mEmail + "\n" +
                    "Card: " + mCard_Num + "\n";
        return mCustomer;
    }


    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }
    public String getPhone() {
        return mPhone;
    }
    public String getEmail() {
        return mEmail;
    }
    public String getPicture_Address() {
        String id = String.valueOf(getId());
        return "IMG_" + id + ".jpg";
    }

    // TODO: Implement masking when displaying card_Num
    public String getCard_Num() {
        return mCard_Num;
    }

    public String getCard_Exp() {
        return mCard_Exp;
    }

    public String getCard_CVV() {
        return mCard_CVV;
    }
    public String getCard_Zip() {
        return mCard_Zip;
    }

    public void setId(Long id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPicture_Address(String picture_Address) {
        mPicture_Address = picture_Address;
    }

    public void setCard_Num(String card_Num) {
        mCard_Num = card_Num;
    }

    public void setCard_Exp(String card_Exp) {
        mCard_Exp = card_Exp;
    }

    public void setCard_CVV(String card_CVV) {
        mCard_CVV = card_CVV;
    }

    public void setCard_Zip(String card_Zip) {
        mCard_Zip = card_Zip;
    }
}
