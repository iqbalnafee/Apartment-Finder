package com.example.apartment_finder2;

import com.google.firebase.database.Exclude;

class User {
    private String Displayname;
    private String Email;
    private long createdAt;
    private String mToken;
    private String mRecipientId;

    public User(){}

    public User(String name, String email, long createdAt,String token ) {
        this.Displayname = name;
        this.Email = email;
        this.createdAt = createdAt;
        this.mToken=token;
    }
    public String getName() {
        return Displayname;
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




}

