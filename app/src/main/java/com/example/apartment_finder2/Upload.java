package com.example.apartment_finder2;

public class Upload {
    private String mName;
    private String mImageUrl1;
    private String mImageUrl2;
    private String mImageUrl3;
    private String mImageUrl4;
    private String mPrice;
    private String mNumber_Of_Bedrooms;
    private String mLocation;
    private String mLoggedEmail;
    private String mSchool;
    private String mHospital;
    private String mBus;
    private String mGas;
    private String mLift;
    private String mSecurity;
    private String mRentOrSell;
    private String mEs;
    public Upload() {
        //empty constructor needed
    }

    public Upload(String loggedemail ,String price,String no_bedrooms,String Loc,String imageUrl1,String imageUrl2,String imageUrl3,String imageUrl4,
                  String school,String hospital,String bus,String gas,String lift,String security,String rentOsell,String LEmail) {

        mLoggedEmail=loggedemail;
        mPrice=price;
        mNumber_Of_Bedrooms=no_bedrooms;
        mLocation=Loc;
        mImageUrl1=imageUrl1;
        mImageUrl2=imageUrl2;
        mImageUrl3=imageUrl3;
        mImageUrl4=imageUrl4;
        mSchool=school;
        mHospital=hospital;
        mBus=bus;
        mGas=gas;
        mLift=lift;
        mSecurity=security;
        mRentOrSell=rentOsell;
        mEs=LEmail;


    }

    public String getmLoggedEmail() {
        return mLoggedEmail;
    }

    public void setmLoggedEmail(String mLoggedEmail) {
        this.mLoggedEmail = mLoggedEmail;
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
        return mImageUrl1;
    }
    public String getmImageUrl2() {
        return mImageUrl2;
    }
    public String getmImageUrl3() {
        return mImageUrl3;
    }
    public String getmImageUrl4() {
        return mImageUrl4;
    }

    public void setmImageUrl(String ImageUrl) {
        this.mImageUrl1 = ImageUrl;
    }
    public void setmImageUrl2(String ImageUrl) {
        this.mImageUrl2 = ImageUrl;
    }
    public void setmImageUrl3(String ImageUrl) {
        this.mImageUrl3 = ImageUrl;
    }
    public void setmImageUrl4(String ImageUrl) {
        this.mImageUrl4 = ImageUrl;
    }

    public String getmSchool() {
        return mSchool;
    }

    public void setmSchool(String mSchool) {
        this.mSchool = mSchool;
    }

    public String getmHospital() {
        return mHospital;
    }

    public void setmHospital(String mHospital) {
        this.mHospital = mHospital;
    }

    public String getmBus() {
        return mBus;
    }

    public void setmBus(String mBus) {
        this.mBus = mBus;
    }

    public String getmGas() {
        return mGas;
    }

    public void setmGas(String mGas) {
        this.mGas = mGas;
    }

    public String getmLift() {
        return mLift;
    }

    public void setmLift(String mLift) {
        this.mLift = mLift;
    }

    public String getmSecurity() {
        return mSecurity;
    }

    public void setmSecurity(String mSecurity) {
        this.mSecurity = mSecurity;
    }

    public String getmRentOrSell() {
        return mRentOrSell;
    }

    public void setmRentOrSell(String mRentOrSell) {
        this.mRentOrSell = mRentOrSell;
    }

    public String getmEs() {
        return mEs;
    }

    public void setmEs(String mEs) {
        this.mEs = mEs;
    }
}
