package com.example.apartment_finder2;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mPrice;
    private String mNumber_Of_Bedrooms;
    private String mLocation;
    public Upload() {
        //empty constructor needed
    }

    public Upload(String name,String price,String no_bedrooms,String Loc,String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mPrice=price;
        mNumber_Of_Bedrooms=no_bedrooms;
        mLocation=Loc;
        mImageUrl=imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmNumber_Of_Bedrooms() {
        return mNumber_Of_Bedrooms;
    }

    public void setmNumber_Of_Bedrooms(String mNumber_Of_Bedrooms) {
        this.mNumber_Of_Bedrooms = mNumber_Of_Bedrooms;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String ImageUrl) {
        this.mImageUrl = ImageUrl;
    }
}
