package com.example.apartment_finder2;

public class favourites {

    private String imageUrl;

    public favourites()
    {

    }
    public favourites(String url)
    {
        imageUrl=url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
