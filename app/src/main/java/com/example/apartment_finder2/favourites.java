package com.example.apartment_finder2;

public class favourites {

    private String imageUrl;
    private String uid;

    public favourites()
    {

    }
    public favourites(String url,String U_id)
    {
        imageUrl=url;
        uid=U_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
