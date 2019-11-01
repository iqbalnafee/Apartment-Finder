package com.example.apartment_finder2;

import com.google.firebase.database.Exclude;

class User {
    private String Name;
    private String Email;
    private long createdAt;
    private String mToken;
    private String mMobileNo;
    private String mAddress;


    public User(){}

    public User(String name, String email, long createdAt,String token,String MobileNo,String Address) {
        Name = name;
        Email = email;
        createdAt = createdAt;
        mToken=token;
        mMobileNo=MobileNo;
        mAddress=Address;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getmMobileNo() {
        return mMobileNo;
    }

    public void setmMobileNo(String mMobileNo) {
        this.mMobileNo = mMobileNo;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

